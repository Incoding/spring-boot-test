package com.javaapi.test.servletFilter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 */
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
        initParams={
                @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),// 忽略资源
                @WebInitParam(name="profileEnable",value="true")// 统计一个请求下面执行了那些sql
        })
public class DruidStatFilter extends WebStatFilter {

}