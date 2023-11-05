package com.peppacatt.wechat.chat.controller;

import cn.hutool.crypto.SecureUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@Slf4j
public class WeChatController {
    @Value("${wechat.token}")
    private String wechatToken;

    /**
     * 验证消息的确来自微信服务器
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return echostr
     */
    @GetMapping("/")
    public String verify(String signature, String timestamp, String nonce, String echostr) {
        // 1）将token、timestamp、nonce三个参数进行字典序排序
        List<String> mySignatures = Arrays.asList(wechatToken, timestamp, nonce);
        Collections.sort(mySignatures);
        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder mySignatureStr = new StringBuilder();
        for (String temp : mySignatures) {
            mySignatureStr.append(temp);
        }
        String sha1Str = SecureUtil.sha1(mySignatureStr.toString());
        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (signature.equals(sha1Str)) {
            log.info("验证微信服务器成功！");
            return echostr;
        } else {
            log.error("验证微信服务器失败, sinature: {}, mySignature: {}", signature, sha1Str);
            return null;
        }
    }

    /**
     * 接收微信服务器的消息
     *
     * @param request request
     */
    @PostMapping("/")
    public void receiveMsg(HttpServletRequest request) {
        log.info("接收到消息了");
        // 获取inputStream
        ServletInputStream inputStream;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            log.error("getInputStream fail! ex: {}", e.getMessage());
            return;
        }
        // 解析xml
        SAXReader saxReader = new SAXReader();
        Document document;
        try {
            document = saxReader.read(inputStream);
        } catch (DocumentException e) {
            log.error("read inputStream fail! ex: {}", e.getMessage());
            return;
        }
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            map.put(element.getName(), element.getStringValue());
        }
        System.out.println(map);
    }
}
