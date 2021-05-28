package cn.bt.shouldSkip;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan("cn.bt.selftag")
public class MyComponentScan {

    @ComponentScan("cn.bt.selftag")
    @Configuration
    @Order(90)
    class InnerClass{

    }

}
