package cn.bt.app;

import cn.bt.entity.Student;
import cn.bt.selfEditor.Customer;
import cn.bt.selftag.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: zhouqian
 * @Date: 2020/11/26 17:44
 */
public class TestApp {
	public static void main(String[] args) {
		/*ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("MyApplicationContext.xml");
		User zq = (User) ac.getBean("user1");
		System.out.println(zq);*/

		/*ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("selfEditor.xml");
		Customer customer = (Customer) ac.getBean("customer");
		System.out.println(customer);*/

		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("testMergedBeanDefinition.xml");
		Student student = (Student) ac.getBean("student");
		System.out.println(student);
	}
}

