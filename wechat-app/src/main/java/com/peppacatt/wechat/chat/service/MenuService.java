package com.peppacatt.wechat.chat.service;

import com.alibaba.fastjson2.JSONObject;

/**
 * 自定义菜单
 */
public interface MenuService {
    /**
     * 手动调用该接口来创建菜单
     *
     * @return JSONObject
     */
    JSONObject creatMenu();
}
