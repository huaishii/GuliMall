package com.ljl.gulimall.member.dao;

import com.ljl.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author ljl
 * @email ljl@gmail.com
 * @date 2022-08-18 16:11:16
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
