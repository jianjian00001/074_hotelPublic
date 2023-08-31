package com.example.hotel.controller.socket;


import com.alibaba.fastjson.JSONObject;

import com.example.hotel.entity.SocketInfo;
import com.example.hotel.service.SocketInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiao
 * @since 2020-12-25
 */
@RestController
@CrossOrigin
@RequestMapping("/Hotux/socket-info")
public class SocketInfoController {

    @Autowired
    private SocketInfoService infoService;


    @PostMapping("/updateBean")
    public void saveBean(@RequestBody String str) {
        SocketInfo socketInfo = JSONObject.parseObject(str, SocketInfo.class);
        infoService.saveOrUpdate(socketInfo);

    }


}

