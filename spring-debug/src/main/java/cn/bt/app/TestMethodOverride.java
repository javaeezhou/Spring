package cn.bt.app;

import cn.bt.lookup.Apple;
import cn.bt.lookup.FruitPlate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring中默认的对象都是单例的，spring会在一级缓存中持有该对象，方便下次直接获取，
 * 那么如果是原型作用域的话，会创建一个新的对象
 * 如果想在一个单例模式的bean下引用一个原型模式的bean,怎么办？
 * 在此时就需要引用lookup-method标签来解决此问题
 *
 * 通过拦截器的方式每次需要的时候都去创建最新的对象，而不会把原型对象缓存起来，
 *
 */
public class TestMethodOverride {
	// look-up -> 解决单例引用原型
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("methodOverride.xml");
		/*Apple apple1 = (Apple) ac.getBean("apple");
		System.out.println(apple1.getBanana());
		Apple apple2 = (Apple) ac.getBean("apple");
		System.out.println(apple2.getBanana());*/

		FruitPlate fruitPlate1 = (FruitPlate) ac.getBean("fruitplate1");
		Apple apple1 = (Apple) fruitPlate1.getFruit();
		Apple apple2 = (Apple) fruitPlate1.getFruit();
		System.out.println(apple1);
		System.out.println(apple2);



		/*FruitPlate fruitPlate2 = (FruitPlate) ac.getBean("fruitplate2");
		fruitPlate2.getFruit();*/
	}
}
