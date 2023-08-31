package com.example.hotel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.hotel.entity.SocketInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author JIAO

 * @date 2020-12-17 16:45:37
 */
@Mapper
public interface InfoDao extends BaseMapper<SocketInfo> {
	
}
