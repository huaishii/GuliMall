package com.ljl.gulimall.product.feign;

import com.ljl.common.to.SkuReductionTo;
import com.ljl.common.to.SpuBoundTo;
import com.ljl.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @PostMapping("/coupon/spubounds/save")
    R saveSkuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/seckillskurelation/saveinfo")
    R saveSkuRedution(SkuReductionTo skuReductionTo);
}
