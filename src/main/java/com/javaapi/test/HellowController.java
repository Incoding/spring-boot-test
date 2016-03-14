package com.javaapi.test;

import com.javaapi.test.dao.jpa.dao.UserDao;
import com.javaapi.test.dao.jpa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@ImportResource("classpath:application-context.xml")
//打war包注意
// http://mrlee23.iteye.com/blog/2047968

public class HellowController {

    @Autowired
    private UserDao userDao;


    @RequestMapping("/hellow")
    @ResponseBody
    public String hellow(){

        return "哈喽，Spring Boot ！";
    }

    @RequestMapping("/nihao")
    @ResponseBody
    public String nihao(String name) {
        User byUsername = userDao.findByUsername(name);
        return byUsername== null ? "user is not found":byUsername.toString();
    }


    public static void main(String[] args) {
        //第一个简单的应用，
        SpringApplication.run(HellowController.class, args);

    }

}  