package com.ljl.gulimall.ware.dao;

import com.ljl.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品库存
 *
 * @author ljl
 * @email ljl@gmail.com
 * @date 2022-08-18 16:24:21
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void wareSkuDao(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);
}
