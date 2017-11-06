package com.qin.dao;

import com.qin.entity.Seckill;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Created by DELL on 2017/10/28.
 */
@MapperScan
public interface SeckillDao {

    public Seckill findById(Long id);

    public int save(Seckill seckill);

    public int updateCount(@Param("id") Long id,@Param("count") int count);

    public int deleteById(Long id);
}
