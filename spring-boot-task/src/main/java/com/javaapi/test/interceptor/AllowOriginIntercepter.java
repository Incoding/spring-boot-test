package com.javaapi.test.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 */
@Component
public class AllowOriginIntercepter implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AllowOriginIntercepter.class);
    private String allowValue = "*";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String origin = request.getHeader("origin");
        String headers = request.getHeader("Access-Control-Request-Headers");
        if (isNotEmpty(headers)) {
            headers = ", " + headers;
        } else {
            headers = "";
        }
        if (isNotEmpty(origin)) {
            if (originIsAllow(origin, allowValue)) {
                response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept" + headers);
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.setHeader("Access-Control-Allow-Credentials", "true");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private boolean originIsAllow(String origin, String value) {
        if (isBlank(value)) {
            return false;
        }
        if (("*").equals(value)) {
            return true;
        }
        String[] values = value.split(",");
        for (String s : values) {
            if (origin.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
    }
}
