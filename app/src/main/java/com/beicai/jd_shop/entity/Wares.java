package com.beicai.jd_shop.entity;

/**
 * Created by 王东 on 2017/2/20.
 */
public class Wares extends BaseBean {
    private String name;
    private String imgUrl;
    private Float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
