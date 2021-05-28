package cn.bt.shouldSkip;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional({WindowsCondition.class})
@Configuration
public class BeanConfig {
    @Bean(name = "lbwnb")
    public Person person1(){
        return new Person("lbw",20);
    }
    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person2(){
        return new Person("Linus",48);
    }
 }