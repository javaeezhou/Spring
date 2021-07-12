package cn.bt.factoryBean;

import cn.bt.entity.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouqian
 * @create 2021-07-12 17:00
 */
public class TestFactoryBean {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("factoryBean.xml");
		// beanName加上前缀&获取的就是实现FactoryBean的factoryBean，不加获取的就是factoryBean中的getObject返回的对象
		MyFactoryBean myFactoryBean = (MyFactoryBean) ac.getBean("&myFactoryBean");
		Student student = (Student) ac.getBean("myFactoryBean");
		System.out.println(student);
	}
}
