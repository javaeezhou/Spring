package cn.bt.app;

import cn.bt.entity.A;
import cn.bt.entity.Car;
import cn.bt.entity.Person;
import cn.bt.entity.Student;
import cn.bt.factoryBean.MyFactoryBean;
import cn.bt.test.MyClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: zhouqian
 * @Date: 2020/10/26 16:55
 */
public class App {
	public static void main(String[] args) {
//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("test.xml");
//		MyClassPathXmlApplicationContext mac = new MyClassPathXmlApplicationContext("applicationContext.xml");
//		A a = (A) ac.getBean("a");
//		A a = (A) mac.getBean("a");
//		Car car = (Car) ac.getBean("car");
//		System.out.println(a);
//		System.out.println(car);

		// -----------------------------------------factoryBean----------------------------------------
//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("factoryBean.xml");
//		Student student = (Student) ac.getBean("&myFactoryBean");
//		System.out.println(student);
//		System.out.println(ac.getBean("&myFactoryBean"));

//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("factoryBean.xml");
//		MyFactoryBean myFactoryBean = (MyFactoryBean) ac.getBean("&myFactoryBean");
//		Student student = (Student) ac.getBean("myFactoryBean");
//		System.out.println(myFactoryBean);//print MyFactoryBean@255316f2
//		System.out.println(student);//print Student{name='韩金轮', age=32}


		ApplicationContext ac = new ClassPathXmlApplicationContext("person.xml");
        Person bean = ac.getBean(Person.class);
//        Person bean2 = ac.getBean(Person.class);

//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("factoryBean.xml");
//		MyFactoryBean myFactoryBean = (MyFactoryBean) ac.getBean("&myFactoryBean");
//		Student student = (Student) ac.getBean("myFactoryBean");
//		System.out.println(myFactoryBean);
//		System.out.println(student);
	}
}
