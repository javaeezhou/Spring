package cn.bt.app;

import cn.bt.entity.A;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: zhouqian
 * @Date: 2020/10/26 16:55
 */
public class App {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		A a = (A) ac.getBean("a");
		System.out.println(a);
	}
}
