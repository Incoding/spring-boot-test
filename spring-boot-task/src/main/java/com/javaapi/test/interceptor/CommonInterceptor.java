package com.javaapi.test.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
    String queryString = request.getQueryString();
    logger.info("Before invoke method. url '" + request.getMethod() + " " + request.getRequestURL() + (queryString != null && !queryString.isEmpty() ? "?" + queryString : "") + "'");
    return true;
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
    logger.info("After invoke method.");
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    logger.info("End render data.");
  }
}
