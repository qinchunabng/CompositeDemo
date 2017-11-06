package com.qin.service;

import com.qin.dao.SeckillDao;
import com.qin.entity.Seckill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 基于数据库的实现
 * 缺点：并发量低，单台mysql并发量300/700（加SSD）
 * 优点：实现简单，最可靠
 * Created by DELL on 2017/10/28.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    SeckillDao seckillDao;

    public void addGoods(Seckill seckill) {
        seckillDao.save(seckill);
    }

    public void delGoods(Long id) {
        seckillDao.deleteById(id);
    }

    public int getGoodsRest(Long id) {
        return seckillDao.findById(id).getNumber();
    }

    public boolean updateGoodsAmount(Long id, int buys) {
        return seckillDao.updateCount(id,buys)>0;
    }
}
