package com.qin.seckill;

import com.qin.entity.Seckill;
import com.qin.service.GoodsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by DELL on 2017/10/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SeckillTest {

    private static final String GOODS_NAME="iphone X";
    private static final int GOODS_NUM=50;
    private static final int USER_NUM=100;

    private CountDownLatch cdl=new CountDownLatch(USER_NUM);

    @Resource(name = "goodsRedisServiceImpl")
    private GoodsService goodsService;

    private Long id;
    private int successUser=0;
    private int saleGoods=0;

    @Before
    public void init(){
        Seckill seckill=new Seckill();
        seckill.setSeckillId(1006L);
        seckill.setName(GOODS_NAME);
        seckill.setNumber(GOODS_NUM);
        seckill.setStartTime(new Date());
        seckill.setEndTime(new Date());
        seckill.setCreateTime(new Date());
        goodsService.addGoods(seckill);
        id=seckill.getSeckillId();
        System.out.println("ID:"+id);
    }

    @Test
    public void seckill(){
        for(int i=0;i<USER_NUM;i++){
            new Thread(new UserRequest(id,3)).start();
            cdl.countDown();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------购买手机成功的用户数量："+successUser);
        System.out.println("--------------------已经卖掉的手机数量："+saleGoods);
        System.out.println("--------------------库存手机数量："+goodsService.getGoodsRest(id));
    }


    class UserRequest implements Runnable{

        private Long id;
        private int buys;

        public UserRequest(Long id, int buys) {
            this.id = id;
            this.buys = buys;
        }

        public void run() {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(goodsService.updateGoodsAmount(id,buys)){
                synchronized (GOODS_NAME){
                    successUser++;
                    saleGoods+=buys;
                }
            }
        }
    }
}
