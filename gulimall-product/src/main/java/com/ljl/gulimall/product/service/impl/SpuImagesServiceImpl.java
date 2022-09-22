package com.ljl.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljl.common.utils.PageUtils;
import com.ljl.common.utils.Query;

import com.ljl.gulimall.product.dao.SpuImagesDao;
import com.ljl.gulimall.product.entity.SpuImagesEntity;
import com.ljl.gulimall.product.service.SpuImagesService;
import org.springframework.util.CollectionUtils;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long id, List<String> images) {
        if(!CollectionUtils.isEmpty(images)){
            List<SpuImagesEntity> collect = images.stream().map(item -> {
                SpuImagesEntity imagesEntity = new SpuImagesEntity();
                imagesEntity.setId(id);
                imagesEntity.setImgUrl(item);
                return imagesEntity;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }

    }

}