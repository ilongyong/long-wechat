package com.wechat.commons.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@Component
public class SubscriptionInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(SubscriptionInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //logger.info("SubscriptionInterceptor  start");
        showParameters(request);
    }


    private void showParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap == null || parameterMap.isEmpty()){
            return;
        }

        String line = "--> 下面是参数展示：";
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String value = Arrays.toString(entry.getValue());
            String message = String.format(" %s:%s ", key, value);

            line += message;
        }
        logger.info(line);
    }
}
