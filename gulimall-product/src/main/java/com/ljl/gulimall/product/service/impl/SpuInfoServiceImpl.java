package com.ljl.gulimall.product.service.impl;

import com.ljl.common.to.SkuReductionTo;
import com.ljl.common.to.SpuBoundTo;
import com.ljl.common.to.es.SkuEsModel;
import com.ljl.common.utils.R;
import com.ljl.gulimall.product.entity.*;
import com.ljl.gulimall.product.feign.CouponFeignService;
import com.ljl.gulimall.product.service.*;
import com.ljl.gulimall.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljl.common.utils.PageUtils;
import com.ljl.common.utils.Query;

import com.ljl.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {


    @Autowired
    private SpuInfoDescService descService;
    @Autowired
    private SpuImagesService imagesService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService valueService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private CouponFeignService couponFeignService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 保存商品信息
     *
     * @param vo
     */
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        //spu基本信息
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfoEntity);
        Date date = new Date();
        spuInfoEntity.setCreateTime(date);
        spuInfoEntity.setUpdateTime(date);
        this.saveBaseSpuInfo(spuInfoEntity);

        //保存spu描述
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuInfoEntity.getId());
        descEntity.setDecript(String.join(",", decript));
        descService.saveSpuInfDesc(descEntity);

        //保存spu的图片集
        List<String> images = vo.getImages();
        imagesService.saveImages(spuInfoEntity.getId(), images);

        //保存spu的规格参数
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());
            AttrEntity byId = attrService.getById(attr.getAttrId());
            valueEntity.setAttrName(byId.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(spuInfoEntity.getId());
            return valueEntity;
        }).collect(Collectors.toList());
        valueService.saveProductAttr(collect);

        //sku的积分信息
        Bounds bounds = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(baseAttrs, spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        R res = couponFeignService.saveSkuBounds(spuBoundTo);
        if (res.getCode() != 0) {
            log.error("远程调用保存积分信息失败！");
        }

        //保存相应的sku信息
        List<Skus> skus = vo.getSkus();
        if (!CollectionUtils.isEmpty(skus)) {
            skus.forEach(item -> {
                StringBuilder buffer = new StringBuilder();
                for (Images image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        buffer.append(image.getImgUrl());
                    }
                }
                //sku基本信息
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(buffer.toString());
                skuInfoService.saveSkuInfo(skuInfoEntity);

                //sku的图片信息
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> imgCollect = item.getImages().stream().map(img -> {
                    SkuImagesEntity imagesEntity = new SkuImagesEntity();
                    imagesEntity.setSkuId(skuId);
                    imagesEntity.setImgUrl(img.getImgUrl());
                    imagesEntity.setDefaultImg(img.getDefaultImg());
                    return imagesEntity;
                }).filter(url -> !StringUtils.isEmpty(url.getImgUrl())).collect(Collectors.toList());
                skuImagesService.saveBatch(imgCollect);

                //sku的销售属性信息
                List<Attr> attrs = item.getAttr();
                List<SkuSaleAttrValueEntity> saleCollect = attrs.stream().map(attr -> {
                    SkuSaleAttrValueEntity attrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr, attrValueEntity);
                    attrValueEntity.setSkuId(skuId);
                    return attrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(saleCollect);

                //sku的优惠、满减信息
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    R redutionRes = couponFeignService.saveSkuRedution(skuReductionTo);
                    if (redutionRes.getCode() != 0) {
                        log.error("远程调用保存优惠、满减信息失败！");
                    }
                }


            });
        }
    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w -> {
                w.eq("id", key).or().like("spu_name", key);
            });
        }
        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("publish_status", status);
        }
        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equals(brandId)) {
            wrapper.eq("brand_id", brandId);
        }
        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId) && !"0".equals(catelogId)) {
            wrapper.eq("catelog_id", catelogId);
        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void spuUp(Long spUid) {

        //查出当前spuid对应的所有sku信息、品牌名族
        List<SkuInfoEntity> skus = skuInfoService.getSkusByspuId(spUid);
        //封装每个sku信息
        skus.stream().map(sku -> {
            //组装要存储在es中的数据
            SkuEsModel esModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, esModel);
            esModel.setSkuPrice(sku.getPrice());
            esModel.setSkuImg(sku.getSkuDefaultImg());
            //远程调用库存系统，查询是否有库存

            //热度评分

            //查询品牌 分类名字信息
            BrandEntity brand = brandService.getById(esModel.getBrandId());
            esModel.setBrandName(brand.getName());
            esModel.setBrandImg(brand.getLogo());

            CategoryEntity category = categoryService.getById(esModel.getCatalogId());
            esModel.setCatalogName(category.getName());

            //查询当前sku所有可被检索规格属性

            return esModel;
        }).collect(Collectors.toList());

        //将数据发送给检索服务保存

    }


}