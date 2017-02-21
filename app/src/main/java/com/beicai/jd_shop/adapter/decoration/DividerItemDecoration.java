package com.beicai.jd_shop.adapter.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 王东 on 2017/2/15.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration{
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = 5;
        outRect.bottom = 5;
        outRect.left = 5;
        outRect.right= 5;
    }
}
