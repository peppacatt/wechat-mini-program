package com.peppacatt.wechat.chat.api;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JiaoshuApi implements PeppaApi {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public JSONObject dailyEnglish() {
        return restTemplate.getForObject("https://api.oioweb.cn/api/common/OneDayEnglish", JSONObject.class);
    }
}
