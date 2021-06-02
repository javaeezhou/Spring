package cn.bt.testAutowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouqian
 * @create 2021-06-02 16:29
 */
public class TestAutowired {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("testAutowired.xml");
		T_A t_a = (T_A) ac.getBean("t_A");
		System.out.println(t_a.getMsg());
	}
}
