package com.qin.service;

import com.qin.entity.Seckill;
import com.schooner.MemCached.MemcachedItem;
import com.whalin.MemCached.MemCachedClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 基于memcached实现
 * Created by DELL on 2017/10/28.
 */
@Service
public class GoodsCacheServiceImpl implements GoodsService{

    @Resource
    private MemCachedClient client;

    public void addGoods(Seckill seckill) {
        boolean b = client.set(seckill.getSeckillId().toString(),seckill.getNumber());
        System.out.println("b:"+b);
    }

    public void delGoods(Long id) {
        client.delete(id.toString());
    }

    public int getGoodsRest(Long id) {
        return Integer.parseInt(client.get(id.toString()).toString().trim());
    }

    public boolean updateGoodsAmount(Long id, int buys) {
//        long n = client.decr(id.toString(),buys);
//        System.out.println("n:"+n);
//        return n>0;
        MemcachedItem item=client.gets(id.toString());
        Integer amount=Integer.parseInt(item.getValue().toString().trim());
        if(amount<buys){
            return false;
        }else{
            //通过memcached提供cas轻量级锁来减库存
            boolean flag = client.cas(id.toString(),amount-buys,item.getCasUnique());
            if(flag){
                return true;
            }else{//更新失败，重试
                return updateGoodsAmount(id,buys);
            }
        }
    }
}
