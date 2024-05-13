package com.peppacatt.wechat.chat.api;

import com.alibaba.fastjson2.JSONObject;
import com.peppacatt.wechat.chat.config.AccessTokenBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeChatApi {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccessTokenBean accessTokenBean;

    /**
     * 自定义菜单_创建接口
     *
     * @param jsonObject 按钮JSONObject
     * @return 微信返回结果
     */
    public JSONObject creatMenu(JSONObject jsonObject) {
        return restTemplate.postForObject(String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s",
                        accessTokenBean.getAccessToken()),
                jsonObject,
                JSONObject.class);
    }
}
