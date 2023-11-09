package com.peppacatt.wechat.chat.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.peppacatt.wechat.chat.service.WeChatService;
import com.peppacatt.wechat.entity.vo.TextMsg;
import com.thoughtworks.xstream.XStream;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 微信公众号重要逻辑service实现类
 */
@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

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
    public String verify(String signature, String timestamp, String nonce, String echostr, String wechatToken) {
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
     * @return 回复的消息
     */
    public String receiveMsg(HttpServletRequest request) {
        // 获取inputStream
        ServletInputStream inputStream;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            log.error("getInputStream fail! ex: {}", e.getMessage());
            return null;
        }
        // 解析xml
        SAXReader saxReader = new SAXReader();
        Document document;
        try {
            document = saxReader.read(inputStream);
        } catch (DocumentException e) {
            log.error("read inputStream fail! ex: {}", e.getMessage());
            return null;
        }
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            map.put(element.getName(), element.getStringValue());
        }
        return replyMsg(map);
    }

    /**
     * 被动回复用户消息
     *
     * @param map map
     * @return 消息
     */
    private String replyMsg(Map<String, String> map) {
        TextMsg textMsg = new TextMsg()
                .setToUserName(map.get("FromUserName"))
                .setFromUserName(map.get("ToUserName"))
                .setCreateTime(new Date().getTime())
                .setMsgType("text")
                .setContent("我回复了一条消息");
        // 转为xml字符串
        XStream xStream = new XStream();
        xStream.processAnnotations(TextMsg.class);
        return xStream.toXML(textMsg);
    }
}
