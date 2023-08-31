package com.example.hotel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.hotel.entity.SocketDaily;
import com.example.hotel.mapper.SocketDailyMapper;
import com.example.hotel.service.SocketDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiao
 * @since 2020-12-25
 */
@Service
public class SocketDailyServiceImpl extends ServiceImpl<SocketDailyMapper, SocketDaily> implements SocketDailyService {

    @Autowired
    private SocketDailyMapper socketDailyMapper;

    @Override
    public List<SocketDaily> findAllById(Long post) {
        return socketDailyMapper.findAllById(post);
    }
}
