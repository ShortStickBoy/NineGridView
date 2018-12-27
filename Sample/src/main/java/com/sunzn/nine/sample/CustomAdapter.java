package com.sunzn.nine.sample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sunzn.nine.library.NineGridView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private RequestOptions mOptions;
    private DrawableTransitionOptions mTransition;
    private ArrayList<String> mBeans;
    private ArrayList<String> mDatas;

    public CustomAdapter() {
        mOptions = new RequestOptions().centerCrop();
        mOptions = new RequestOptions();
        mTransition = DrawableTransitionOptions.withCrossFade();
        mBeans = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mBeans.add("http://p.bianke.cnki.net/api/face/FC674C7F2D65955E20D3B0EA67543157.jpg");
            mBeans.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181227153138275_small_.jpg");
            mBeans.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181226085339816_small_.jpg");
            mBeans.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181225161535468_small_.jpg");
            mBeans.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181225161535468_small_.jpg");
            mBeans.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181219182009178_small_.jpg");
        }

        mDatas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    mDatas.add("http://bianke.cnki.net/resources/imagesTemp/platForm/20891.jpg");
                    mDatas.add("http://bianke.cnki.net/resources/imagesTemp/platForm/18524.jpg");
                    break;
                case 1:
                    mDatas.add("http://bianke.cnki.net/resources/imagesTemp/platForm/16978.jpg");
                    mDatas.add("http://bianke.cnki.net/resources/imagesTemp/platForm/15853.jpg");
                    break;
                case 2:
                    mDatas.add("http://bianke.cnki.net/resources/imagesTemp/platForm/19721.jpg");
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getNineGridView().getAdapter().setData(mDatas);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final NineGridView<NineAdapter> nineGridView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nineGridView = itemView.findViewById(R.id.image);
            nineGridView.setAdapter(new NineAdapter(nineGridView, mOptions, mTransition));
        }

        public NineGridView<NineAdapter> getNineGridView() {
            return nineGridView;
        }
    }

}
