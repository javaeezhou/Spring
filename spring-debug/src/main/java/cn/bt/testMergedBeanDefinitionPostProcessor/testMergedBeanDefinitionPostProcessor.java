package cn.bt.testMergedBeanDefinitionPostProcessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouqian
 * @create 2021-05-11 17:33
 */
public class testMergedBeanDefinitionPostProcessor {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("testMergedBeanDefinitionPostProcessor.xml");

	}
}
