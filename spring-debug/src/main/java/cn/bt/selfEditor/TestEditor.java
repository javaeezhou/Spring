package cn.bt.selfEditor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouqian
 * @create 2021-03-01 15:23
 */
public class TestEditor {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("selfEditor.xml");
		Customer customer = (Customer) ac.getBean("customer");
		System.out.println(customer);
	}
}
