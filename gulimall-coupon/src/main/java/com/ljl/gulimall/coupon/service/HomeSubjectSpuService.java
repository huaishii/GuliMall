package com.ljl.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljl.common.utils.PageUtils;
import com.ljl.gulimall.coupon.entity.HomeSubjectSpuEntity;

import java.util.Map;

/**
 * δΈι’εε
 *
 * @author ljl
 * @email ljl@gmail.com
 * @date 2022-08-18 17:03:29
 */
public interface HomeSubjectSpuService extends IService<HomeSubjectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

