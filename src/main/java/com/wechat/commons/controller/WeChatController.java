package com.wechat.commons.controller;

import com.wechat.commons.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/wechat")
public class WeChatController {

	Logger logger = LoggerFactory.getLogger(WeChatController.class);

	@Autowired
	private WechatUtil wechatUtil;
	/**
	 *
	 * @方法名： init
	 * @作者：longyong
	 * @日期时间：2016年9月13日 下午2:10:50
	 * @返回类型：void
	 * @描述：创建菜单
	 */
	@RequestMapping(value = "/initSub")
	@ResponseBody
	public String initSub(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ret="订阅号菜单初始化成功";
		try {
			wechatUtil.createSubMneu();
		}catch (Exception e){
			ret="订阅号菜单初始化失败,原因："+e.getMessage();
		}
		logger.info(ret);
		return ret;
	}

	@RequestMapping(value = "/test.do")
	public ModelAndView subscription(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView("test");
//		logger.info(String.valueOf(((int)(Math.random()*9+1)*1000)));
		System.out.println((int)((Math.random()*9+1)*1000));

		return mv;
	}
}
