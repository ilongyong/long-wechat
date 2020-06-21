package com.wechat.commons.constants;


/** 
 * @ClassName: IConstants 
 * @Description: 常见常量
 * @author longyong
 * @date 2015-3-25 上午10:26:31
 *  
 */
public class IConstants {

	public static String TOKEN = "zhulumir2";

	public static String ACCESS_TOKEN_SUBSCRIPTION = "ACCESS_TOKEN_SUBSCRIPTION";
	/**
	 * 大财子jsapiToken
	 */
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 前端js调用ticket获取
     */
    public final static String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	 /**
	 * 客服接口（POST） 限5000000（次/天） 
	 */
    public final static String CUSTOM_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; 
	/**
	 * 微信模板消息请求URL
	 */
	public static String DCZ_TEMPLATE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	/**
	 * 微信支付--获取预支付repay_id请求地址 xyd
	 */
	public final static String WX_REPAY_GETREPAYID_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 微信退款请求地址
	 */
	public final static String WX_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	/**
	 * 微信获取素材列表
	 */
	public final static String WX_GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";


}