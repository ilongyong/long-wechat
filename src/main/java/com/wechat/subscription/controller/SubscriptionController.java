package com.wechat.subscription.controller;

import com.wechat.commons.constants.IConstants;
import com.wechat.commons.interceptor.SubscriptionInterceptor;
import com.wechat.commons.utils.SHA1;
import com.wechat.commons.utils.WechatUtil;
import com.wechat.commons.vo.ReceiveMsgBody;
import com.wechat.commons.vo.ResponseMsgBody;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

@RestController
public class SubscriptionController {

    Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private WechatUtil wechatUtil;

    @RequestMapping(value = "/subscription" , method = RequestMethod.GET)
    public String checkSignature(String signature, String timestamp, String nonce, String echostr){
        String[] str = { wechatUtil.getToken(), timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
        // 确认请求来至微信
        if (digest.equals(signature)) {
            return echostr;
        }
        return "";
    }

    @RequestMapping(value = "/subscription" , method = RequestMethod.POST, produces = {"application/xml; charset=UTF-8"})
    @ResponseBody
    public Object  getUserMessage(@RequestBody ReceiveMsgBody receiveMsgBody){

        String type=receiveMsgBody.getMsgType();
        logger.info("type="+type);

        ResponseMsgBody textMsg = new ResponseMsgBody();
        textMsg.setToUserName(receiveMsgBody.getFromUserName());
        textMsg.setFromUserName(receiveMsgBody.getToUserName());
        textMsg.setCreateTime(new Date().getTime());
        textMsg.setMsgType("text");

        if("text|image|news|voice|video".contains(type)){

            textMsg.setContent("暗号不对！");
            if(type.equals("text") && receiveMsgBody.getContent().equals("签到")){
                int random=(int)((Math.random()*9+1)*1000);
                textMsg.setContent("你的签到码为："+random);
            }
            return textMsg;
        }else if(type.equals("event")){
            String event=receiveMsgBody.getEvent();
            String eventKey=receiveMsgBody.getEventKey();
            if(event.equals("CLICK") && eventKey.equals("key1")) {
                textMsg.setContent("请去官网购买元宝~");
                return textMsg;
            }else if(event.equals("CLICK") && eventKey.equals("key2")) {
                textMsg.setContent("你点的是升级攻略~");
                return textMsg;
            }else if(event.equals("CLICK") && eventKey.equals("key3")) {
                textMsg.setContent("你点的是装备攻略~");
                return textMsg;
            }
        }
        return null;
    }


    @RequestMapping("/getAccessToken")
    public void getAccessToken(){
        try{
            String accessToken= wechatUtil.getAccessToken();
            logger.info("access_token = {}",accessToken);
        }catch(WxErrorException e){
            logger.error("获取access_token失败。",e);
        }
    }

}
