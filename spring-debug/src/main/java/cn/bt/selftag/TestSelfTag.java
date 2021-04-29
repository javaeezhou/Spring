package cn.bt.selftag;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouqian
 * @create 2021-04-28 15:27
 */
public class TestSelfTag {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("MyapplicationContext.xml");
		System.out.println(ac.getBean("user1"));
	}
}
