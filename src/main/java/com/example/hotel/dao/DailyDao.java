package com.example.hotel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.hotel.entity.SocketDaily;
import org.apache.ibatis.annotations.Mapper;

/**
 * 网站统计日数据
 * 
 * @author JIAO
 * @email
 * @date 2020-12-17 16:45:37
 */
@Mapper
public interface DailyDao extends BaseMapper<SocketDaily> {
	
}
