package com.ljl.gulimall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ljl.common.valid.AddGroup;
import com.ljl.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ljl.gulimall.product.entity.BrandEntity;
import com.ljl.gulimall.product.service.BrandService;
import com.ljl.common.utils.PageUtils;
import com.ljl.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author ljl
 * @email ljl@gmail.com
 * @date 2022-08-18 13:15:16
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     * 使用JSR303校验，1、在实体类的字段上加上校验注解 列如@NotBlank，可以自定义返回信息
     * 2、加上@Valid注解开启校验
     * 3、BindResult可以获取校验结果
     * 4、分组校验功能
     *  1)、@NotBlank(message = "品牌名必须提交", groups = {UpdateGroup.class, AddGroup.class})
     *  给校验注解标注什么情况需要校验
     *  2)、@Validated({AddGroup.class})指定需要校验的分组
     *  3)、默认没有指定分组的校验注解，在分组校验下不生效，只会在不分组@Validated下生效
     * 5、自定义校验
     *  1)、编写一个自定义校验注解
     *  2)、编写一个自定义校验器 ConstraintValidator
     *  3)、关联校验注解、校验器
     *      @Documented
     *      @Constraint(validatedBy = { ListValueConstraintValidator.class } 可以指定多个不同的校验器)
     *      @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
     *      @Retention(RUNTIME)
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand/*, BindingResult result*/){
//        HashMap<String, String> map = new HashMap<>();
//        if(result.hasErrors()){
//            //获取校验的错误信息
//            result.getFieldErrors().forEach((item) -> {
//                //错误提示消息
//                String message = item.getDefaultMessage();
//                //获取错误属性名
//                String field = item.getField();
//                map.put(field, message);
//            });
//            return R.error(400, "提交的数据不合法").put("data", map);
//        }
		brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Validated(UpdateGroup.class) @RequestBody BrandEntity brand){
		brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
