package com.qin.service;

import com.qin.entity.Seckill;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基于redis的实现
 * Created by DELL on 2017/10/28.
 */
@Service
public class GoodsRedisServiceImpl implements GoodsService {

    @Resource
    RedisTemplate redisTemplate;

    public void addGoods(Seckill seckill) {
        redisTemplate.opsForValue().set(seckill.getSeckillId().toString(),seckill.getNumber());
    }

    public void delGoods(Long id) {
        redisTemplate.delete(id.toString());
    }

    public int getGoodsRest(Long id) {
        return (Integer)redisTemplate.opsForValue().get(id.toString());
    }

    /**
     * 通过redis提供的事务机制，来减库存
     * @param id
     * @param buys
     * @return
     */
    public boolean updateGoodsAmount(Long id, int buys) {
        redisTemplate.watch(id.toString());
        int amount=getGoodsRest(id);
        if(amount>buys){
            redisTemplate.multi();
            redisTemplate.opsForValue().set(id.toString(),amount-buys);
            List<Object> list = redisTemplate.exec();
            //如果事务失败了exec会返回null
            if(list==null){
                updateGoodsAmount(id,amount);
            }else{
                return true;
            }
        }
        return false;
    }
}
