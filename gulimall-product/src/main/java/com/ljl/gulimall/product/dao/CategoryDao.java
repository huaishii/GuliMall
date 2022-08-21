package com.ljl.gulimall.product.dao;

import com.ljl.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author ljl
 * @email ljl@gmail.com
 * @date 2022-08-18 13:15:16
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
