package com.sunzn.nine.sample;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sunzn.nine.library.NineGridView;
import com.sunzn.nine.library.SizeHelper;

import java.util.List;

public class NineAdapter implements NineGridView.NineGridAdapter<String> {

    private List<String> mImageBeans;

    private RequestOptions mOptions;

    private NineGridView mNineGridView;

    private DrawableTransitionOptions mTransitionOptions;

    public NineAdapter(NineGridView parent, RequestOptions options, DrawableTransitionOptions transition) {
        this.mNineGridView = parent;
        this.mTransitionOptions = transition;
        int itemSize = (SizeHelper.getScreenWidth() - 2 * SizeHelper.dp2px(4) - SizeHelper.dp2px(54)) / 3;
        this.mOptions = options.override(itemSize, itemSize);
    }

    public void setData(List<String> data) {
        mImageBeans = data;
        mNineGridView.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mImageBeans == null ? 0 : mImageBeans.size();
    }

    @Override
    public String getItem(int position) {
        return mImageBeans == null ? null : position < mImageBeans.size() ? mImageBeans.get(position) : null;
    }

    @Override
    public ImageView getView(NineGridView parent, int position, View itemView) {
        ImageView imageView;
        if (itemView == null) {
            Log.e("NineGridView", "空");
            imageView = new ImageView(parent.getContext());
            imageView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.base_F2F2F2));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            Log.e("NineGridView", "非");
            imageView = (ImageView) itemView;
        }
        String url = mImageBeans.get(position);
        Glide.with(imageView).load(url).apply(mOptions).transition(mTransitionOptions).into(imageView);
        return imageView;
    }

}
