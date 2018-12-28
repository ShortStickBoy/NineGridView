package com.sunzn.nine.sample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sunzn.nine.library.NineGridView;

import java.util.ArrayList;
import java.util.Random;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private RequestOptions mOptions;
    private DrawableTransitionOptions mTransition;
    private ArrayList<String> mBeans;
    private ArrayList<ArrayList<String>> mData;

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

        mData = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int n = random.nextInt(5);
            ArrayList<String> subs = new ArrayList<>();
            for (int j = 0; j < (n == 0 ? 1 : n); j++) {
                switch (j) {
                    case 1:
                        subs.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181227153138275_small_.jpg");
                        break;
                    case 2:
                        subs.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181226085339816_small_.jpg");
                        break;
                    case 3:
                        subs.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181225161535468_small_.jpg");
                        break;
                    case 4:
                        subs.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181225161535468_small_.jpg");
                        break;
                    case 5:
                        subs.add("http://e.bianke.cnki.net/Home/GetCorpusPic/20181219182009178_small_.jpg");
                        break;
                }
            }
            mData.add(subs);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getNineGridView().getAdapter().setData(mData.get(i));
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
            nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
                @Override
                public void onImageClick(int position, View view) {
                    Toast.makeText(itemView.getContext(), nineGridView.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public NineGridView<NineAdapter> getNineGridView() {
            return nineGridView;
        }
    }

}
