package cn.bt.resolveBeforeInstantiation;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @author zhouqian
 * @create 2021-02-05 10:48
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

	@Override
	public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
		System.out.println("beanName:"+beanName+"----执行postProcessProperties方法");

		return pvs;
	}

	// 实例化之前
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		System.out.println("beanName:"+beanName+"----执行postProcessBeforeInstantiation方法");
		if (beanClass == BeforeInstantiation.class){
//            Enhancer enhancer = new Enhancer();
//            enhancer.setSuperclass(beanClass);
//            enhancer.setCallback(new MyMethodInterceptor());
//            BeforeInstantiation beforeInstantiation = (BeforeInstantiation) enhancer.create();
//            System.out.println("创建代理对象："+beforeInstantiation);
			return new BeforeInstantiation();
		}
		return null;
	}

	// 实例化之后
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		System.out.println("beanName:"+beanName+"----执行postProcessAfterInstantiation方法");
		return false;
	}

	// 初始化之前
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("beanName:"+beanName+"----执行postProcessBeforeInitialization方法");

		return bean;
	}

	// 初始化之后
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("beanName:"+beanName+"----执行postProcessAfterInitialization方法");

		return bean;
	}
}
