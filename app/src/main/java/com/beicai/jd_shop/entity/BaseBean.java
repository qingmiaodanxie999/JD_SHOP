package com.beicai.jd_shop.entity;

import java.io.Serializable;

/**
 * Created by 王东 on 2017/2/15.
 */

public class BaseBean implements Serializable {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
