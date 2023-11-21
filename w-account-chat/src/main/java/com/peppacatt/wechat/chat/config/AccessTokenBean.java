package com.peppacatt.wechat.chat.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * 获取 Access token
 */
@Component
@Slf4j
public class AccessTokenBean {
    @Value("${wechat.app-id}")
    private String appId;

    @Value("${wechat.appsecret}")
    private String appsecret;

    @Autowired
    private RestTemplate restTemplate;

    private JSONObject accessTokenObj = new JSONObject();

    /**
     * accessToken过期时间
     */
    private long expireTime;

    /**
     * 获取accessToken
     *
     * @return accessToken
     */
    public String getAccessToken() {
        // 为避免反复获取accessToken, 需要判断判断过期时间, 即过期之后再重新获取
        long now = new Date().getTime();
        if (StrUtil.isEmpty(accessTokenObj.getString("access_token")) || expireTime <= now) {
            refreshAccessToken();
        }
        System.out.printf("accessToken: %s\n", accessTokenObj.getString("access_token"));
        return accessTokenObj.getString("access_token");
    }

    /**
     * 刷新accessToken(即重新发请求获取accessToken)
     */
    private void refreshAccessToken() {
        String url = String.format(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                appId,
                appsecret);
        try {
            accessTokenObj = restTemplate.getForObject(url, JSONObject.class);
        } catch (RestClientException e) {
            log.error("Request accessToken fail!");
            return;
        }
        expireTime = (accessTokenObj.getLongValue("expires_in") * 1000L) + new Date().getTime();
    }
}
