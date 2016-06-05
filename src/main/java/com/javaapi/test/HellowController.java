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

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 测试controller
     * @return
     */
    @RequestMapping("/hellow")
    @ResponseBody
    public String hellow(){

        return "哈喽，Spring Boot ！";
    }


    /**
     * 测试dao
     * @param name
     * @return
     */
    @RequestMapping("/nihao")
         @ResponseBody
         public String nihao(String name) {
        User byUsername = userDao.findByUsername(name);
        return byUsername== null ? "user is not found":byUsername.toString();
    }

    /**
     * 测试事物
     * @param id
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(String id) {
        userService.updateUser(id);
        return "success";
    }


    /**
     * @param platformTransactionManager
     * 测试事物的实现类
     * @return
     */
    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager){
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }


    /**
     * 测试事物
     * @param id
     * @return
     */
    @RequestMapping("/getRealContext")
    @ResponseBody
    public String getRealContext(HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("/");
        /**
         * 没打包的情况下输出
         /Users/user/workspace-2015-08-13/spring-boot-test/src/main/webapp/
         *
         */
        System.out.println("realPath = " + realPath);
        /** 没打包的情况下输出
         /Users/user/workspace-2015-08-13/spring-boot-test
         *
         */
        System.out.println("realPath = " + System.getProperty("user.dir"));

        return "success";
    }



    public static void main(String[] args) {
        //第一个简单的应用，
        SpringApplication.run(HellowController.class, args);

    }

}  