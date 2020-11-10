package cn.bt.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: zhouqian
 * @Date: 2020/11/10 9:32
 */
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

	public MyClassPathXmlApplicationContext(String... configLocations) throws BeansException {
		super(configLocations);
	}

	/**
	 * 重写initPropertySources方法，可以验证系统环境变量中是否有需要的属性
	 */
	@Override
	protected void initPropertySources() {
		getEnvironment().setRequiredProperties("OS");// -> 指的是key
	}

	/**
	 * 重写customizeBeanFactory
	 * 此方法是用来实现BeanFactory的属性设置，主要是设置两个属性：
	 * allowBeanDefinitionOverriding：是否允许覆盖同名称的不同定义的对象
	 * allowCircularReferences：是否允许bean之间的循环依赖
	 * @param beanFactory the newly created bean factory for this context
	 */
	@Override
	protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
		super.setAllowBeanDefinitionOverriding(false);
		super.setAllowCircularReferences(false);
		super.customizeBeanFactory(beanFactory);
	}
}
