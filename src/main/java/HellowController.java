import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@ImportResource("classpath:application-context.xml")
public class HellowController {



    @RequestMapping("/hellow")
    @ResponseBody
    public String hellow(){

        return "哈喽，Spring Boot ！";
    }


    public static void main(String[] args) {
        //第一个简单的应用，   
        SpringApplication.run(HellowController.class, args);

    }

}  