package cn.bt.selfCustomAutowired;

import cn.bt.selfbdrpp.MySelfBeanDefinitionRegistryPostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * 第二种多此一举的方法：自定义一个BDRPP --> 来注册处理自定义注解@Zhou_Autowired的BPP
 *
 * @author zhouqian
 * @create 2021-05-20 17:31
 */
public class Zhou_AutowiredBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {

	// 使用BDRPP来注册处理自定义注解@Zhou_Autowired的BPP
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("执行postProcessBeanDefinitionRegistry---Zhou_AutowiredBeanDefinitionRegistryPostProcessor");
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(Zhou_AutowiredAnnotationBeanPostProcessor.class);
		registry.registerBeanDefinition("zhou_autowired",builder.getBeanDefinition());
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("执行postProcessBeanFactory---Zhou_AutowiredBeanDefinitionRegistryPostProcessor");
//        BeanDefinition msb = beanFactory.getBeanDefinition("lbwnb");
//        msb.getPropertyValues().getPropertyValue("name").setConvertedValue("dsm");
		System.out.println("===============");
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
