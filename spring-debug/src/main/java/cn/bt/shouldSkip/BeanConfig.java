package cn.bt.shouldSkip;

import cn.bt.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Conditional({WindowsCondition.class})
@Configuration
@Import(Student.class)
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