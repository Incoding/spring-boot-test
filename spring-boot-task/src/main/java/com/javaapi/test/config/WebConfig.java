package com.javaapi.test.config;

import com.javaapi.test.interceptor.AllowOriginIntercepter;
import com.javaapi.test.interceptor.CommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @date 2015-07-16
 * @what
 */
@Configuration
@ComponentScan(basePackages = {"tv.acfun.http.common"})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private CommonInterceptor commonInterceptor;//通用拦截器 可设置通用配置



  @Autowired
  private AllowOriginIntercepter allowOriginIntercepter;

  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(rateLimitInterceptor);
    registry.addInterceptor(allowOriginIntercepter);
//    registry.addInterceptor(myCacheInterceptor);
    registry.addInterceptor(commonInterceptor);
  }

  @Bean
  public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
    ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
    dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    return registration;
  }

  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://aixifan.com", "http://acfun.tv", "*")
        .allowedMethods("OPTIONS", "GET", "PUT", "POST", "DELETE")
        .allowedHeaders("X-Requested-With", "Content-Type", "Accept", "Origin", "*")
        .allowCredentials(false).maxAge(3600);
  }

  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//    argumentResolvers.add(new JsonArgumentResolver());
//    argumentResolvers.add(new ApiHeaderArgumentResolver());
    super.addArgumentResolvers(argumentResolvers);
  }

  public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
//    returnValueHandlers.add(new JsonReturnValueHandler());
    super.addReturnValueHandlers(returnValueHandlers);
  }

  public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//    exceptionResolvers.add(new JsonExceptionResolver());
    super.configureHandlerExceptionResolvers(exceptionResolvers);
  }
}
