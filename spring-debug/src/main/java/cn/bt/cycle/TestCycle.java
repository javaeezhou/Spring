package cn.bt.cycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouqian
 * @create 2021-06-09 11:22
 */
public class TestCycle {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("cycle.xml");
	}
}
