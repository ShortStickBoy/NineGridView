package com.sunzn.nine.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class NineGridView<A extends NineGridView.NineGridAdapter> extends ViewGroup implements ViewGroup.OnHierarchyChangeListener {

    private int mRows;
    private int mSpace;
    private int mColumns;
    private int mChildWidth;
    private int mChildHeight;

    private A mAdapter;
    private boolean mXClick;
    private int mWRatio, mHRatio, mUSpace;
    private OnImageClickListener mListener;
    private WeakReferencePool<View> IMAGE_POOL;

    public interface NineGridAdapter<I> {
        int getCount();

        I getItem(int position);

        View getView(NineGridView parent, int position, View itemView);
    }

    public NineGridView(Context context) {
        this(context, null);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.NineGridView);
        mWRatio = attributes.getInt(R.styleable.NineGridView_nine_grid_view_w_ratio, 1);
        mHRatio = attributes.getInt(R.styleable.NineGridView_nine_grid_view_h_ratio, 1);
        mUSpace = attributes.getInt(R.styleable.NineGridView_nine_grid_view_x_space, 4);
        mXClick = attributes.getBoolean(R.styleable.NineGridView_nine_grid_view_x_click, true);
        attributes.recycle();
        initView();
    }

    private void initView() {
        setOnHierarchyChangeListener(this);
        mSpace = SizeHelper.dp2px(mUSpace);
        IMAGE_POOL = new WeakReferencePool<>(10);
    }

    public void setAdapter(A adapter) {
        mAdapter = adapter;
        notifyDataSetChanged();
    }

    public A getAdapter() {
        return mAdapter;
    }

    public void notifyDataSetChanged() {
        if (mAdapter == null || mAdapter.getCount() <= 0) {
            removeAllViews();
            return;
        }
        if (mImageViews == null) {
            mImageViews = new ArrayList<>();
        } else {
            mImageViews.clear();
        }
        int oldCount = getChildCount();
        int newCount = mAdapter.getCount();
        initMatrix(newCount);
        removeScrapViews(oldCount, newCount);
        addChildrenData(mAdapter);
        requestLayout();
    }

    private void removeScrapViews(int oldCount, int newCount) {
        if (newCount < oldCount) {
            removeViewsInLayout(newCount, oldCount - newCount);
        }
    }

    private void initMatrix(int length) {
        if (length <= 3) {
            mRows = 1;
            mColumns = length;
        } else if (length <= 6) {
            mRows = 2;
            mColumns = 3; // 因为length <=6 所以实际Columns<3也是不会导致计算出问题的
            if (length == 4) {
                mColumns = 2;
            }
        } else {
            mRows = 3;
            mColumns = 3;
        }
    }

    private void addChildrenData(A adapter) {
        int childCount = getChildCount();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            boolean hasChild = i < childCount;
            // 简单的回收机制,主要是为ListView/RecyclerView做优化
            View recycleView = hasChild ? getChildAt(i) : null;
            if (recycleView == null) {
                recycleView = IMAGE_POOL.get();
                View child = adapter.getView(this, i, recycleView);
                addViewInLayout(child, i, child.getLayoutParams(), true);
                mImageViews.add((ImageView) child);
            } else {
                adapter.getView(this, i, recycleView);
                mImageViews.add((ImageView) recycleView);
            }
        }
    }


    private List<ImageView> mImageViews;

    public List<ImageView> getImageViews() {
        return mImageViews;
    }

    @Override
    protected boolean addViewInLayout(View child, int index, LayoutParams params, boolean preventRequestLayout) {
        if (!(child instanceof ImageView)) {
            throw new ClassCastException("addView(View child) NineGridView只能放ImageView");
        }
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int childCount = getChildCount();
        if (childCount <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        if ((mRows == 0 || mColumns == 0) && mAdapter == null) {
            initMatrix(childCount);
        }

        final int minW = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int width = resolveSizeAndState(minW, widthMeasureSpec, 1);
        int availableWidth = width - getPaddingLeft() - getPaddingRight();
        mChildWidth = (availableWidth - mSpace * (mColumns - 1)) / mColumns;
        mChildHeight = mChildWidth * mHRatio / mWRatio;

        int height = mChildHeight * mRows + mSpace * (mRows - 1);
        setMeasuredDimension(width, height + getPaddingTop() + getPaddingBottom());
    }

    public int getChildHeight() {
        return mChildHeight;
    }

    public int getChildWidth() {
        return mChildWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren();
    }

    protected void layoutChildren() {
        if (mRows <= 0 || mColumns <= 0) {
            return;
        }

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView view = (ImageView) getChildAt(i);
            int row = i / mColumns;
            int col = i % mColumns;
            int left = (mChildWidth + mSpace) * col + getPaddingLeft();
            int top = (mChildHeight + mSpace) * row + getPaddingTop();
            int right = left + mChildWidth;
            int bottom = top + mChildHeight;
            view.layout(left, top, right, bottom);
            final int position = i;
            if (mXClick) {
                view.setOnClickListener(v -> {
                    if (mListener != null) {
                        mListener.onImageClick(position, view);
                    }
                });
            }
        }
    }


    public void setOnImageClickListener(OnImageClickListener listener) {
        mListener = listener;
    }

    public void setSpace(int space) {
        mSpace = space;
    }

    public int getSpace() {
        return mSpace;
    }

    @Override
    public void onChildViewAdded(View parent, View child) {

    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        IMAGE_POOL.put(child);
    }

    public interface OnImageClickListener {
        void onImageClick(int position, View view);
    }

}