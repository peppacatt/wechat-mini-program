package com.peppacatt.wechat.chat.controller;

import com.alibaba.fastjson2.JSONObject;
import com.peppacatt.wechat.chat.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义菜单
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 手动调用该接口来创建菜单
     *
     * @return JSONObject
     */
    @GetMapping("/create")
    private JSONObject creatMenu() {
        return menuService.creatMenu();
    }
}
