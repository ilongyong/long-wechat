package com.wechat.commons.utils;

import com.wechat.commons.controller.WeChatController;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 
 * 类名:WeChatUtil
 * 作者:longyong
 * 时间 :2020-6-11 上午10:01:35
 * 描述:公众平台通用接口工具类
 */
@Component
public class WechatUtil {

	Logger logger = LoggerFactory.getLogger(WeChatController.class);

	@Value("${wechat.subscription.token}")
	private String token;

	@Value("${wechat.subscription.appId}")
	private String appId;

	@Value("${wechat.subscription.appSecret}")
	private String appSecret;

	@Value("${wechat.subscription.url}")
	private String url;


	public String getToken() {
		return token;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getUrl() {
		return url;
	}

	/**
	 *  调用微信接口
	 */
	private WxMpService wxMpService;

	/**
	 *      * 初始化
	 *     
	 */
	@PostConstruct
	private void init() {
		WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId(appId);
		wxMpConfigStorage.setSecret(appSecret);
		wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
	}

	/**
	 *      * 获取 access_token 不刷新
	 *      * @return access_token
	 *      * @throws WxErrorException
	 *      
	 */
	public String getAccessToken() throws WxErrorException {
		return wxMpService.getAccessToken();
	}

	/**
	 *      * 创建订阅号菜单
	 *      * @return access_token
	 *      * @throws WxErrorException
	 *      
	 */
	public void createSubMneu() throws WxErrorException {

		WxMenuButton button1 = new WxMenuButton();
		button1.setType("click"); //点击事件按钮
		button1.setName("购买元宝");
		button1.setKey("key1"); //根据标志获取点击菜单

		//创建一个复合菜单
		WxMenuButton button2 = new WxMenuButton();
		button2.setName("游戏攻略");

		WxMenuButton button2_1 = new WxMenuButton();
		button2_1.setType("click"); //点击事件按钮
		button2_1.setName("升级攻略");
		button2_1.setKey("key2"); //根据标志获取点击菜单

		WxMenuButton button2_2 = new WxMenuButton();
		button2_2.setType("click"); //点击事件按钮
		button2_2.setName("装备攻略");
		button2_2.setKey("key3"); //根据标志获取点击菜单


		WxMenuButton button3 = new WxMenuButton();
		button3.setName("官方网站");
		button3.setType("view");
		button3.setUrl("http://www.zhulumir2.com");  //必须添加http

		List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
		subButtons.add(button2_1);
		subButtons.add(button2_2);
		button2.setSubButtons(subButtons);

		List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();
		buttons.add(button3);
		buttons.add(button1);
		buttons.add(button2);


		WxMenu menu = new WxMenu();
		menu.setButtons(buttons);

		try {
			String ret=wxMpService.getMenuService().menuCreate(menu);
			logger.info(ret);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}
}
	//
//	/**
//	 *
//	 * @方法名： checkSign
//	 * @作者：szh
//	 * @日期时间：2016年7月20日 下午4:52:08
//	 * @返回类型：boolean
//	 * @描述：检验签名
//	 */
//	public static boolean checkSign(HttpServletRequest request, String token){
//		String timestamp = request.getParameter("timestamp");
//		String nonce = request.getParameter("nonce");
//		String signature = request.getParameter("signature");
//
//		if (StringUtil.isEmpty(timestamp, nonce, signature)) {
//			return false;
//		}
//
//		/*Long intervalMinutes = (System.currentTimeMillis() - Long.parseLong(timestamp))/60000;//计算链接生成时间分钟数
//		if(intervalMinutes < 0 || intervalMinutes > 5){//链接存在时间超过五分钟，已失效
//			logger.error("链接存在时间超过五分钟，已失效");
//			return false;
//		}*/
//
//		String bigStr = timestamp + token + nonce;
//		// SHA1加密
//		String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
//		return digest.equalsIgnoreCase(signature);
//	}
//	/**
//	 *
//	 * @方法名： generateAuthNo
//	 * @作者：szh
//	 * @日期时间：2016年7月20日 下午4:52:08
//	 * @返回类型：String
//	 * @描述：客户端生成用户授权码
//	 */
////	public static String generateAuthNo(String username, String password) {
////		long result = username.hashCode() + password.hashCode() + get6Code() + System.currentTimeMillis();
////		String authNo = MD5Utils.encode(result + "").toLowerCase().trim().replace(" ", "");
////		return authNo;
////	}
//
//	/**
//	 *
//	 * @方法名： get6Code
//	 * @作者：szh
//	 * @日期时间：2016年7月20日 下午4:52:32
//	 * @返回类型：int
//	 * @描述：生产随机6位数
//	 */
//	public static int get6Code() {
//		int code = new Random().nextInt(999999);
//		if(code < 100000) {
//			code = code + 100000;
//		}
//
//		return code;
//	}
//
//	/**
//	 * 元转换成分
//	 * @return
//	 */
//	public static String getMoney(String amount) {
//		if(amount==null){
//			return "";
//		}
//		// 金额转化为分为单位
//		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
//        int index = currency.indexOf(".");
//        int length = currency.length();
//        Long amLong = 0l;
//        if(index == -1){
//            amLong = Long.valueOf(currency+"00");
//        }else if(length - index >= 3){
//            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));
//        }else if(length - index == 2){
//            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);
//        }else{
//            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");
//        }
//        return amLong.toString();
//	}
//
//	/**
//	 * @方法名： getIpAddr
//	 * @作者：xyd
//	 * @日期时间：2016-3-14 下午08:44:06
//	 * @返回类型：String
//	 * @描述：获取ip地址
//	 */
//	public static String getIpAddr(HttpServletRequest request) {
//	      String ip = request.getHeader("x-forwarded-for");
//	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//	         ip = request.getHeader("Proxy-Client-IP");
//	     }
//	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//	         ip = request.getHeader("WL-Proxy-Client-IP");
//	      }
//	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//	          ip = request.getRemoteAddr();
//	     }
//	     return ip;
//	}
//
//
//	/**
//	 *
//	 * @方法名： getDCZURL
//	 * @作者：szh
//	 * @日期时间：2015-6-1 上午11:03:50
//	 * @返回类型：String
//	 * @描述：网页授权获取用户openid
//	 */
//	public static String getSubscriptionURL(String url) {
//		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
//				+ IConstants.getSubscriptionAppId()
//				+ "&redirect_uri="
//				+ changeUrl(url)
//				+ "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//	}
//
//
//	/**
//	 *
//	 * @方法名： changeUrl
//	 * @作者：szh
//	 * @日期时间：2015年5月19日 上午10:11:38
//	 * @返回类型：String
//	 * @描述：加密
//	 */
//	public static String changeUrl(String url) {
//		try {
//			long timestamp = (DateUtils.dateAddMonth(new Date(), 100)).getTime();
//			String[] str = { IConstants.URL_SECURITY_KEY, timestamp + "", timestamp + "" };
//			Arrays.sort(str); // 字典序排序
//			String bigStr = str[0] + str[1] + str[2];
//
//			// SHA1加密
//			String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
//
//			if (url != null && !"".equals(url.trim())) {
//				url = url.trim();
//				if (url.contains("?")) {
//					url = url + "&time=" + timestamp + "&sign=" + digest;
//				} else {
//					url = url + "?time=" + timestamp + "&sign=" + digest;
//				}
//				url = URLEncoder.encode(url, "UTF-8");
//			} else {
//				throw new Exception("大财子URL非法，不能生成链接!");
//			}
//		} catch (Exception e) {
//			log.error(e);
//		}
//		return url;
//	}
//
//	/**
//	 *
//	 * @方法名： getURL
//	 * @作者：szh
//	 * @日期时间：2016年7月21日 下午3:06:56
//	 * @返回类型：String
//	 * @描述：获取原生访问的url
//	 */
//	public static String getURL(HttpServletRequest request) {
//		try{
//			String url = request.getScheme() + "://" + request.getServerName() + (request.getServerPort()==80?"":":"+request.getServerPort()) + request.getContextPath() + request.getServletPath();
//
//			@SuppressWarnings("unchecked")
//			Map<String, String[]> param = request.getParameterMap();
//
//			if(!param.isEmpty()){
//				url = url + "?";
//				Set<String> names = param.keySet();
//				for (Iterator<String> it = names.iterator(); it.hasNext();) {
//					String name = (String) it.next();
//					if(!"state".equals(name)&&!"sign".equals(name)&&!"time".equals(name)&&!"code".equals(name)){
//						url = url + name + "=" + ((String[])param.get(name))[0] + "&";
//					}
//				}
//				url=url.substring(0, url.length()-1);
//			}
//			return url;
//		}catch (Exception e) {
//			log.error(e);
//		}
//		return "";
//	}
//
//	/**
//	 *
//	 * @方法名： validParam
//	 * @作者：szh
//	 * @日期时间：2016年7月21日 下午3:07:26
//	 * @返回类型：boolean
//	 * @描述：验证参数
//	 */
//	public static boolean validParam(HttpServletRequest request){
//		//判断参数
//		String timestamp = request.getParameter("time");
//		if(timestamp==null||"".equals(timestamp.trim())||!NumberUtils.isNumber(timestamp.trim())){
//			return false;
//		}
//
//		//判断url是否失效
//		Date urltime = new Date(Long.parseLong(timestamp));
//		Date validtime = DateUtils.dateAddHour(urltime, 1);//一小时失效时间
//		if(validtime.getTime() < (new Date()).getTime()){
//			return false;
//		}
//
//		//判断参数
//		String sign = request.getParameter("sign");
//		if(sign == null || (sign != null && sign.equals(""))){
//			return false;
//		}
//
//		String[] str = {IConstants.URL_SECURITY_KEY, timestamp+"", timestamp+""};
//		Arrays.sort(str); // 字典序排序
//		String bigStr = str[0] + str[1] + str[2];
//		// SHA1加密
//		String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
//
//		if(digest.equals(sign.trim())){
//			return true;
//		}else{
//			return false;
//		}
//	}
//
//	/**
//	 *
//	 * @方法名： httpsRequest
//	 * @作者：szh
//	 * @日期时间：2016年7月20日 下午3:59:32
//	 * @返回类型：JSONObject
//	 * @描述：发起https请求并获取json结果
//	 */
////    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
////        JSONObject jsonObject = null;
////        StringBuffer buffer = new StringBuffer();
////        try {
////            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
////            TrustManager[] tm = { new MyX509TrustManager() };
////            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
////            sslContext.init(null, tm, new java.security.SecureRandom());
////            // 从上述SSLContext对象中得到SSLSocketFactory对象
////            SSLSocketFactory ssf = sslContext.getSocketFactory();
////
////            URL url = new URL(requestUrl);
////            HttpsURLConnection httpsUrlConn = (HttpsURLConnection) url.openConnection();
////            httpsUrlConn.setSSLSocketFactory(ssf);
////
////            httpsUrlConn.setDoOutput(true);
////            httpsUrlConn.setDoInput(true);
////            httpsUrlConn.setUseCaches(false);
////            // 设置请求方式（GET/POST）
////            httpsUrlConn.setRequestMethod(requestMethod);
////
////            if ("GET".equalsIgnoreCase(requestMethod))
////            	httpsUrlConn.connect();
////
////            // 当有数据需要提交时
////            if (null != outputStr) {
////                OutputStream outputStream = httpsUrlConn.getOutputStream();
////                // 注意编码格式，防止中文乱码
////                outputStream.write(outputStr.getBytes("UTF-8"));
////                outputStream.close();
////            }
////
////            // 将返回的输入流转换成字符串
////            InputStream inputStream = httpsUrlConn.getInputStream();
////            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
////            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
////
////            String str = null;
////            while ((str = bufferedReader.readLine()) != null) {
////                buffer.append(str);
////            }
////            bufferedReader.close();
////            inputStreamReader.close();
////            // 释放资源
////            inputStream.close();
////            inputStream = null;
////            httpsUrlConn.disconnect();
////            jsonObject = JSONObject.fromObject(buffer.toString());
////        } catch (ConnectException ce) {
////			log.error("【微信公众平台】微信服务器响应超时!");
////        } catch (Exception e) {
////			log.error("【微信公众平台】", e);
////        }
////        return jsonObject;
////    }
//    /**
//     *
//     * @方法名： httpRequest
//     * @作者：szh
//     * @日期时间：2016年7月20日 下午4:00:39
//     * @返回类型：String
//     * @描述：发起http请求并获取String结果
//     */
//    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
//    	String returnStr = "";
//    	StringBuffer buffer = new StringBuffer();
//    	try {
//    		URL url = new URL(requestUrl);
//    		HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//    		httpUrlConn.setDoOutput(true);
//    		httpUrlConn.setDoInput(true);
//    		httpUrlConn.setUseCaches(false);
//    		// 设置请求方式（GET/POST）
//    		httpUrlConn.setRequestMethod(requestMethod);
//
//    		if ("GET".equalsIgnoreCase(requestMethod))
//    			httpUrlConn.connect();
//
//    		// 当有数据需要提交时
//    		if (null != outputStr) {
//    			OutputStream outputStream = httpUrlConn.getOutputStream();
//    			// 注意编码格式，防止中文乱码
//    			outputStream.write(outputStr.getBytes("UTF-8"));
//    			outputStream.close();
//    		}
//
//    		// 将返回的输入流转换成字符串
//    		InputStream inputStream = httpUrlConn.getInputStream();
//    		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//    		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//    		String str = null;
//    		while ((str = bufferedReader.readLine()) != null) {
//    			buffer.append(str);
//    		}
//    		bufferedReader.close();
//    		inputStreamReader.close();
//    		// 释放资源
//    		inputStream.close();
//    		inputStream = null;
//    		httpUrlConn.disconnect();
//    		returnStr = buffer.toString();
//    	} catch (ConnectException ce) {
//			log.error("【微信公众平台】微信服务器响应超时!");
//    	} catch (Exception e) {
//			log.error("【微信公众平台】", e);
//    	}
//    	return returnStr;
//    }
//
//    /**
//     *
//     * @方法名： getAccessToken
//     * @作者：longyong
//     * @日期时间：2016年7月20日 下午4:09:32
//     * @返回类型：String
//     * @描述：从MongoDB中获取accessToken
//     */
//	public static String getAccessToken(String accessTokenKey) {
//
//		AccessToken accessToken = null;
//		String token = "";
//		String tokenDate = "";
//
//		String subscriptionAppId = IConstants.getSubscriptionAppId();
//		String subscriptionAppSecret = IConstants.getSubscriptionAppSecret();
//
//		String accessTokenValue = XUtils.getPropertiesBean().getSubscriptionAccessToken();
//		if(accessTokenValue==null){
//			accessToken = getAccessToken(subscriptionAppId,subscriptionAppSecret);
//			if (accessToken != null) {
//				token = accessToken.getToken();
//				tokenDate = DateUtils.dateToStringYYYY_MM_DD_MM_HH_SS(accessToken.getDate());
//				XUtils.getPropertiesBean().setSubscriptionAccessToken(token + "#" + tokenDate);
//				logger.info("【逐鹿基地】内存中accessToken为空，获取token:" + token);
//			}
//		}else{
//			String[] array = accessTokenValue.split("#");
//			token = array[0];
//			Date accessTokenDate = DateUtils.strToDate(array[1]);
//			Date now = new Date();
//			logger.info("【逐鹿基地】内存中accessToken为:" + token);
//			long intervalTime = now.getTime() - accessTokenDate.getTime();
//			if (intervalTime > 600 * 1000 || intervalTime < 0) {// 超过10分钟，重新获取token;(间隔时间小于0
//				logger.info("【逐鹿基地】内存中accessToken过期，重新获取token:" + token);
//				// ，重新获取token;用于测试)
//				accessToken = getAccessToken(subscriptionAppId,subscriptionAppSecret);
//				if (accessToken != null) {
//					token = accessToken.getToken();
//					tokenDate = DateUtils.dateToStringYYYY_MM_DD_MM_HH_SS(accessToken.getDate());
//					XUtils.getPropertiesBean().setSubscriptionAccessToken(token + "#" + tokenDate);
//				}
//			}
//		}
//
//		return token;
//	}
//
//	/**
//	 *
//	 * @方法名： getAccessToken
//	 * @作者：longyong
//	 * @日期时间：2016年7月20日 下午4:12:33
//	 * @返回类型：AccessToken
//	 * @描述：获取accessToken
//	 */
//    public static AccessToken getAccessToken(String appId,String appSecret){
//   	 String requestUrl = IConstants.ACCESS_TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
//   	 JSONObject jsonObject = null;
//        if(requestUrl!=null&&!"".equals(requestUrl.trim())){
//        	jsonObject = httpsRequest(requestUrl, "GET", null);
//        }
//        AccessToken accessToken = new AccessToken();
//        // 如果请求成功
//        if (null != jsonObject) {
//            try {
//            	String token = jsonObject.getString("access_token");
//            	accessToken.setToken(token);
//            	accessToken.setDate(new Date());
//            	logger.info("【逐鹿基地】获取token成功");
//            	return accessToken;
//            } catch (JSONException e) {
//            	logger.error("【逐鹿基地】获取token失败");
//            	e.printStackTrace();
//                return null;
//            }
//        }else{
//       	 return null;
//        }
//   }
//}