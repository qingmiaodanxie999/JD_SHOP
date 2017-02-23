package com.beicai.jd_shop.adapter;

import android.content.Context;

import com.beicai.jd_shop.R;
import com.beicai.jd_shop.entity.Wares;

import java.util.List;

/**
 * Created by 王东 on 2017/2/22.
 */

public class HWAdapter extends SimpleAdapter<Wares> {
    public HWAdapter(Context context,  List<Wares> datas) {
        super(context, R.layout.item_hot_wares, datas);
    }


    @Override
    protected void convert(BaseViewHolder holder, Wares item) {

    }
}
