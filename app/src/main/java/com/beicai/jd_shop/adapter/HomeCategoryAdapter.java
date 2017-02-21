package com.beicai.jd_shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.beicai.jd_shop.R;
import com.beicai.jd_shop.entity.Campaign;
import com.beicai.jd_shop.entity.HomeCampain;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 王东 on 2017/2/15.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    private static int VIEW_TYPE_L = 0;
    private static int VIEW_TYPE_R = 1;
    private LayoutInflater mInflater;
    private List<HomeCampain> mDatas;
    private Context mContext;
    private OnCampaignClickListener mListener;

    public void setOnCampaignClickListener(HomeCategoryAdapter.OnCampaignClickListener mListener) {
        this.mListener = mListener;
    }
    public HomeCategoryAdapter(List<HomeCampain> Datas, Context context) {
        this.mContext = context;
        this.mDatas = Datas;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_R) {
            return new ViewHolder(mInflater.inflate(R.layout.item_home_cardview2, parent, false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.item_home_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCampain homeCampain = mDatas.get(position);
        holder.textTitle.setText(homeCampain.getTitle());
        Picasso.with(mContext).
                load(homeCampain.getCpOne().getImgUrl())
                .into(holder.imageViewBig);
        Picasso.with(mContext).
                load(homeCampain.getCpTwo().getImgUrl())
                .into(holder.imageViewSmallTop);
        Picasso.with(mContext).
                load(homeCampain.getCpThree().getImgUrl())
                .into(holder.imageViewSmallBottom);
//        holder.imageViewBig.setImageResource(homeCampain.getImgBig());
//        holder.imageViewSmallTop.setImageResource(homeCampain.getImgSmallTop());
//        holder.imageViewSmallBottom.setImageResource(homeCampain.getImagSmallBottom());
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_R;
        } else {
            return VIEW_TYPE_L;
        }
    }

   class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textTitle;
        private ImageView imageViewBig;
        private ImageView imageViewSmallTop;
        private ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.title_tv);
            imageViewBig = (ImageView) itemView.findViewById(R.id.big_iv);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.small_bottom_iv);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.small_top_iv);

            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }

       @Override
       public void onClick(View v) {
           HomeCampain homeCampain = mDatas.get(getLayoutPosition());
           if (mListener != null) {
               switch (v.getId()) {
                   case R.id.big_iv:
                       mListener.onClick(v,homeCampain.getCpOne());
                   case R.id.small_top_iv:
                       mListener.onClick(v,homeCampain.getCpTwo());
                   case R.id.small_bottom_iv:
                       mListener.onClick(v,homeCampain.getCpThree());
               }
           }
       }
   }

    public interface OnCampaignClickListener {
        void onClick(View view, Campaign campaign);
    }
}
