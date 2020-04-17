package shuangjia.shuangjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import shuangjia.shuangjia.component.InitThread;

@SpringBootApplication
@EnableScheduling
//@ServletComponentScan
//@MapperScan("org.shuangjia.monitor.mapper")
public class ShuangjiaApplication  {

    public static void main(String[] args)  {
        SpringApplication springApplication =new SpringApplication(
                ShuangjiaApplication.class);
       // springApplication.addListeners(new InitThread());
        springApplication.run(args);
    }

}
