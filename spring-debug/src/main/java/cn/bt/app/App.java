package cn.bt.app;

import cn.bt.entity.A;
import cn.bt.entity.Car;
import cn.bt.test.MyClassPathXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: zhouqian
 * @Date: 2020/10/26 16:55
 */
public class App {
	public static void main(String[] args) {
//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("test.xml");
		MyClassPathXmlApplicationContext mac = new MyClassPathXmlApplicationContext("applicationContext.xml");
//		A a = (A) ac.getBean("a");
		A a = (A) mac.getBean("a");
//		Car car = (Car) ac.getBean("car");
		System.out.println(a);
//		System.out.println(car);
	}
}
