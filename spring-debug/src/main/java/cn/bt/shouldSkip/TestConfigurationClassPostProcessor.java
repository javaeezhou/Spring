package cn.bt.shouldSkip;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouqian
 * @create 2021-05-28 16:39
 */
public class TestConfigurationClassPostProcessor {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("testConfigurationClassPostProcessor.xml");
		Person lbwnb = (Person) ac.getBean("lbwnb");
		System.out.println(lbwnb);
	}
}
