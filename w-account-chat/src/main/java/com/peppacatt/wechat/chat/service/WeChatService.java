package com.peppacatt.wechat.chat.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 微信公众号重要逻辑service
 */
public interface WeChatService {

    /**
     * 验证消息的确来自微信服务器
     *
     * @param signature   微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp   时间戳
     * @param nonce       随机数
     * @param echostr     如果验证成功,原样返回该值
     * @param wechatToken 微信公众号自己设置的Token
     * @return echostr 入参echostr
     */
    String verify(String signature, String timestamp, String nonce, String echostr, String wechatToken);

    /**
     * 接收微信服务器的消息
     *
     * @param request request
     * @return 回复的消息
     */
    String receiveMsg(HttpServletRequest request);
}
