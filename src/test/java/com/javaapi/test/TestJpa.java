package com.javaapi.test;


import com.javaapi.test.dao.jpa.dao.UserDao;
import com.javaapi.test.dao.jpa.model.User;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

//http://docs.spring.io/spring-boot/docs/1.1.x/reference/htmlsingle/#boot-features-testing
//http://www.jayway.com/2014/07/04/integration-testing-a-spring-boot-application/
@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = HellowController.class)   // 2
// 标示是个web应用
@WebAppConfiguration   // 3
// 会启动一个内置服务器
@IntegrationTest("server.port:8088")
public class TestJpa {
    @Autowired
    UserDao userDao;

    @Before
    public void setUp(){
        RestAssured.port = 8088;
    }

    //    http://www.tuicool.com/articles/zEz2QrY
    @Test
    public void test() {
        List<User> all = userDao.findAll();
        System.out.println(all);
    }

    @Test
    public void canFetchMickey() {
        try {
            TimeUnit.MINUTES.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("nihao");
    }
    //http://www.ibm.com/developerworks/cn/java/j-lo-rest-assured/
    //Rest-Assured
    @Test
    public void canFetchAll() {
        Response kk = when().
                get("/nihao?name={name}", "kk");
        System.out.println("done");
    }


    RestTemplate template = new TestRestTemplate();

    @Test
    public void testRequest() throws Exception {
        HttpHeaders headers = template.getForEntity("http://xxx.com", String.class).getHeaders();
        assertThat(headers.getLocation().toString(), containsString("myotherhost"));
    }


}
