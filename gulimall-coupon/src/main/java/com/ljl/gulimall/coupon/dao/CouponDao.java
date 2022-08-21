package com.ljl.gulimall.coupon.dao;

import com.ljl.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author ljl
 * @email ljl@gmail.com
 * @date 2022-08-18 17:03:29
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
