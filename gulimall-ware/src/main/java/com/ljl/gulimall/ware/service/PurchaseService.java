package com.ljl.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljl.common.utils.PageUtils;
import com.ljl.gulimall.ware.entity.PurchaseEntity;
import com.ljl.gulimall.ware.vo.MergeVo;
import com.ljl.gulimall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author ljl
 * @email ljl@gmail.com
 * @date 2022-08-18 16:24:21
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnReceivePurchase(Map<String, Object> params);

    void mergePurchase(MergeVo vo);

    void received(List<Long> ids);


    void done(PurchaseDoneVo vo);
}

