package com.peppacatt.wechat.chat.controller;

import com.peppacatt.wechat.chat.api.PeppaApi;
import com.peppacatt.wechat.chat.service.WeChatService;
import com.peppacatt.wechat.chat.config.AccessTokenBean;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信公众号重要逻辑controller
 */
@RestController
@Slf4j
public class WeChatController {
    @Value("${wechat.token}")
    private String wechatToken;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private PeppaApi peppaApi;

    @Autowired
    private AccessTokenBean accessTokenBean;

    @GetMapping("/xx")
    public String xx() {
        return accessTokenBean.getAccessToken();
    }

    /**
     * 验证消息的确来自微信服务器
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   如果验证成功,原样返回该值
     * @return echostr 入参echostr
     */
    @GetMapping("/")
    public String verify(String signature, String timestamp, String nonce, String echostr) {
        return weChatService.verify(signature, timestamp, nonce, echostr, wechatToken);
    }

    /**
     * 接收微信服务器的消息
     *
     * @param request request
     * @return 回复的消息
     */
    @PostMapping("/")
    public String receiveMsg(HttpServletRequest request) {
        return weChatService.receiveMsg(request);
    }
}
