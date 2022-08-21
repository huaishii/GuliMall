package com.ljl.gulimall.order.dao;

import com.ljl.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author ljl
 * @email ljl@gmail.com
 * @date 2022-08-18 16:15:18
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
