package com.javaapi.test;

import com.javaapi.test.dao.jpa.dao.UserDao;
import com.javaapi.test.dao.jpa.model.User;
import com.javaapi.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//You should only ever add one @EnableAutoConfiguration annotation. We generally recommend that you add it to your primary @Configuration class.
@EnableTransactionManagement
@Controller
@EnableAutoConfiguration
@ImportResource("classpath:application-context.xml")
//打war包注意
// http://mrlee23.iteye.com/blog/2047968

public class HellowController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager){
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }



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



    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(String id) {
        userService.updateUser(id);
        return "success";
    }


    public static void main(String[] args) {
        //第一个简单的应用，
        SpringApplication.run(HellowController.class, args);

    }

}  