package com.javaapi.test;

import com.javaapi.test.dao.jpa.dao.UserDao;
import com.javaapi.test.dao.jpa.model.User;
import com.javaapi.test.service.UserJdbcService;
import com.javaapi.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//You should only ever add one @EnableAutoConfiguration annotation. We generally recommend that you add it to your primary @Configuration class.
@Configuration
//@EnableTransactionManagement
@Controller
// exclude 不让spring 提供的异常处理页面生效
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@ImportResource("classpath:application-context.xml")
//打war包注意
// http://mrlee23.iteye.com/blog/2047968
@EnableScheduling
@ServletComponentScan
@ComponentScan(basePackages={"com.javaapi.test.task","com.javaapi.test.controller","com.javaapi.test.session"})
public class HellowController extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HellowController.class);
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

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
        User byUsername2 = userDao.findByUsername(name);
        User byUsername3 = userDao.findByUsername(name);
        userDao.findOne("1");
        userDao.findOne("2");
        userDao.findOne("4");
        userDao.exists("5");
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
     * 测试事物2
     * @param id
     * @return
     */
    @RequestMapping("/updateUser2")
    @ResponseBody
    public String updateUser2(String id) {
        UserJdbcService userJdbcService = (UserJdbcService) applicationContext.getBean("userJdbcServiceImpl");

        userJdbcService.updateUser(id);
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
     * @param
     * @return
     */
    @RequestMapping("/getRealContext")
    @ResponseBody
    public String getRealContext(HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("/");
        /**
         * 没打包的情况下输出
         /Users/user/workspace-2015-08-13/spring-boot-test/src/main/webapp/
         打成可执行jar输出
         realPath = /private/var/folders/f4/45zf68wx2xz7gbl37lp981wh0000gp/T/tomcat-docbase.1284768531660808595.8080/
         打成可执行war输出
         null
         *
         */
        System.out.println("realPath = " + realPath);
        /** 没打包的情况下输出
         /Users/user/workspace-2015-08-13/spring-boot-test
         打成jar输出
         /Users/user/workspace-2015-08-13/spring-boot-test/target
         打成可执行war输出
         /Users/user/workspace-2015-08-13/spring-boot-test/target
         *
         */
        System.out.println("realPath = " + System.getProperty("user.dir"));

        return "success";
    }



    /**
     * 在spring-boot 1.2.3中
     * 要用war包 (不能用jar,也就是不能直接使用main函数启动)
     * 配置方式, 注意在main中启动时会报错的
     *
     * http://bbs.csdn.net/topics/390872423
     *
     *
     * 1   启动的类继承  extends SpringBootServletInitializer
         2   重写configure方法
         @Override
         protected SpringApplicationBuilder configure(
         SpringApplicationBuilder application) {
         // TODO Auto-generated method stub
         return application.sources(SampleSimpleApplication.class);
         }
         3     在resources下加入
         spring.view.prefix: /WEB-INF/jsp/
         spring.view.suffix: .jsp

      * @param map
     * @return
     */
    @RequestMapping("/testJsp")
    public String testJsp(ModelMap map) {
//        map.put("nihao", "nihao");
        return "helloworld";

    }



    public static void main(String[] args) {
        //第一个简单的应用，
        SpringApplication.run(HellowController.class, args);

    }

    /**
     *
     *
     * 1
     * @Conditional(MyCondition.class)
    这句代码可以标注在类上面，表示该类下面的所有@Bean都会启用配置
    也可以标注在方法上面，只是对该方法启用配置

    除了自己自定义Condition之外，Spring还提供了很多Condition给我们用
     @ConditionalOnBean（仅仅在当前上下文中存在某个对象时，才会实例化一个Bean）
     @ConditionalOnClass（某个class位于类路径上，才会实例化一个Bean）
     @ConditionalOnExpression（当表达式为true的时候，才会实例化一个Bean）
     @ConditionalOnMissingBean（仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean）
     @ConditionalOnMissingClass（某个class类路径上不存在的时候，才会实例化一个Bean）
     @ConditionalOnNotWebApplication（不是web应用）


     2
     http://miclee.cn/2015/12/23/spring-boot-6/

     延伸几个 spring-boot提供的 @Conditional的子集，具体了Conditional的条件：

     @ConditionalOnClass: 等同于 @Conditional(OnClassCondition.class)，表示存在对应的Class文件时才会去创建该bean
     @ConditionalOnMissingBean: 等同于 @Conditional(OnBeanCondition.class)，表示spring上下文里缺失某个bean时才会去创建该bean
     @ConditionalOnWebApplication: 等同于 @Conditional(OnWebApplicationCondition.class)，表示只有在WEB应用时才会创建该bean

     @ConditionalOnProperty 等同于 @Conditional(OnPropertyCondition.class) (另外判断条件比较逗逼)



     */
    public void test(){

    }

}  