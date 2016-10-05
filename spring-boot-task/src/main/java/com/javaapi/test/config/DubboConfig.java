package com.javaapi.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @date 2015-07-07
 * @what
 */
@Configuration
@ImportResource("classpath:META-INF/spring/dubbo-consumer.xml")
public class DubboConfig {
}
