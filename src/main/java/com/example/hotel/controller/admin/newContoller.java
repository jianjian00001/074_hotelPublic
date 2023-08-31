package com.example.hotel.controller.admin;

import com.example.hotel.entity.Role;
import com.example.hotel.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/new")
public class newContoller {

    @Autowired
    private RoleService roleService;

    /**
     * 添加用户页面
     *
     * @return 模板路径admin/admin_edit
     */
    @GetMapping("/user_new")
    public String addUser(Model model) {

        //角色列表
        List<Role> roles = roleService.findAll();
        System.out.println("进入user_new01");
        model.addAttribute("roles", roles);
        System.out.println("进入user_new");
        return "admin/admin_user_add";
    }

}
