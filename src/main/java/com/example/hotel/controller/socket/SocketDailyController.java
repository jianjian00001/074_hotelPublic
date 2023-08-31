package com.example.hotel.controller.socket;


import com.alibaba.fastjson.JSONObject;

import com.example.hotel.entity.SocketDaily;
import com.example.hotel.service.SocketDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiao
 * @since 2020-12-25
 */
@RestController
@RequestMapping("/Hotux/socket-daily")
public class SocketDailyController {

    @Autowired
    private SocketDailyService socketDailyService;

    @PostMapping("/saveBean")
    public void saveBean(@RequestBody String str) {
        SocketDaily socketDaily = JSONObject.parseObject(str, SocketDaily.class);

        socketDailyService.saveOrUpdate(socketDaily);
    }
}

