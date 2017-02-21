package com.beicai.jd_shop.entity;

import android.widget.TextView;

/**
 * Created by 王东 on 2017/2/15.
 */

public class HomeCategory extends Category {
    public TextView textTitle;
    public int imgBig;
    public int imgSmallTop;
    public int imagSmallBottom;

    public HomeCategory(String name, int imgBig, int imgSmallTop,int imagSmallBottom) {
        super(name);
        this.imgBig = imgBig;
        this.imagSmallBottom = imagSmallBottom;
        this.imgSmallTop = imgSmallTop;
    }

    public int getImgBig() {
        return imgBig;
    }

    public void setImgBig(int imgBig) {
        this.imgBig = imgBig;
    }

    public int getImgSmallTop() {
        return imgSmallTop;
    }

    public void setImgSmallTop(int imgSmallTop) {
        this.imgSmallTop = imgSmallTop;
    }

    public int getImagSmallBottom() {
        return imagSmallBottom;
    }

    public void setImagSmallBottom(int imagSmallBottom) {
        this.imagSmallBottom = imagSmallBottom;
    }
}
