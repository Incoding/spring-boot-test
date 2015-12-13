package com.javaapi.test;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by user on 15/12/13.
 */
public class Test implements ApplicationContextAware {
    
    public void test(){
        System.out.println("\"====================\" = " + "====================");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("applicationContext = ===================");
    }
}
