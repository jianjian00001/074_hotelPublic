package com.example.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hotel.entity.SocketDaily;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiao
 * @since 2020-12-25
 */
public interface SocketDailyService extends IService<SocketDaily> {
    List<SocketDaily> findAllById(Long post);

}
