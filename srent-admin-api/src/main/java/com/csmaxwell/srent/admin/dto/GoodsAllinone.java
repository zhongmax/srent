package com.csmaxwell.srent.admin.dto;

import com.csmaxwell.srent.db.domain.SrentGoods;

/**
 * Created by maxwell on 2019/8/13
 */
public class GoodsAllinone {
    SrentGoods goods;

    public SrentGoods getGoods() {
        return goods;
    }

    public void setGoods(SrentGoods goods) {
        this.goods = goods;
    }
}
