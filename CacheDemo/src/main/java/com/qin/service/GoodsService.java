package com.qin.service;

import com.qin.entity.Seckill;

/**
 * Created by DELL on 2017/10/28.
 */
public interface GoodsService {

    public void addGoods(Seckill seckill);

    public void delGoods(Long id);

    public int getGoodsRest(Long id);

    public boolean updateGoodsAmount(Long id,int buys);
}
