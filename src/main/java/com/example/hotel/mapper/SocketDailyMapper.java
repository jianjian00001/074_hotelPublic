package com.example.hotel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hotel.entity.SocketDaily;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jiao
 * @since 2020-12-25
 */
@Mapper
public interface SocketDailyMapper extends BaseMapper<SocketDaily> {

    List<SocketDaily> findAllById(Long post);
}
