package com.beicai.jd_shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.beicai.jd_shop.entity.Wares;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;

/**
 * Created by 王东 on 2017/2/20.
 */

public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHold>{
    private LayoutInflater mInflater;
    private List<Wares> mDatas;

    public HotWaresAdapter(List<Wares> datas) {
        this.mDatas = datas;
    }

    @Override
    public HotWaresAdapter.ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_hot_wares, null);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(HotWaresAdapter.ViewHold holder, int position) {
        Wares wares = getData(position);
    }

    private Wares getData(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.size();
        }
        return 0;
    }

     class ViewHold extends RecyclerView.ViewHolder{
         SimpleDraweeView draweeView;
         TextView textTitle;
         public ViewHold(View itemView) {
             super(itemView);
             draweeView = (SimpleDraweeView)itemView.findViewById(R.id.hot_wares_img_sdv);
             draweeView = (SimpleDraweeView)itemView.findViewById(R.id.hot_wares_img_sdv);
             draweeView = (SimpleDraweeView)itemView.findViewById(R.id.hot_wares_img_sdv);
         }
     }
}
