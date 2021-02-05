package cn.bt.resolveBeforeInstantiation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouqian
 * @create 2021-02-05 11:01
 */
public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("resolveBeforeInstantiation.xml");
		BeforeInstantiation bean = ac.getBean(BeforeInstantiation.class);
		bean.doSomeThing();
	}
}
