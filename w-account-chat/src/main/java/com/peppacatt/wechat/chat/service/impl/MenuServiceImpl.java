package com.peppacatt.wechat.chat.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.peppacatt.wechat.chat.api.WeChatApi;
import com.peppacatt.wechat.chat.service.MenuService;
import com.peppacatt.wechat.entity.button.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义菜单
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private WeChatApi weChatApi;

    /**
     * 手动调用该接口来创建菜单
     *
     * @return JSONObject
     */
    public JSONObject creatMenu() {
        JSONObject buttonObj = JSONObject.from(createButton());
        System.out.println(buttonObj);
        return weChatApi.creatMenu(buttonObj);
    }

    /**
     * 创建菜单
     *
     * @return Button
     */
    private Button createButton() {
        // 创建一级菜单
        Button button = new Button();
        List<AbstractButton> buttons = new ArrayList<>();
        // 一级菜单中的第一个按钮
        ClickButton button1 = new ClickButton();
        button1.setName("1");
        button1.setKey("1");
        // 一级菜单中的第二个按钮
        ViewButton button2 = new ViewButton();
        button2.setName("2");
        button2.setUrl("https://www.csdn.net/");
        // 一级菜单中的第三个按钮
        SubButton button3 = new SubButton();
        List<AbstractButton> buttons3 = new ArrayList<>();
        button3.setName("3");
        button3.setSub_button(buttons3);
        // 一级菜单中的3.1个按钮
        ViewButton button31 = new ViewButton();
        button31.setName("31");
        button31.setUrl("https://www.csdn.net/");
        // 一级菜单中的3.2个按钮
        ViewButton button32 = new ViewButton();
        button32.setName("32");
        button32.setUrl("https://www.csdn.net/");
        // 组成二级菜单
        buttons3.add(button31);
        buttons3.add(button32);
        // 组成一级菜单
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        button.setButton(buttons);
        return button;
    }
}
