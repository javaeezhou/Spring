package cn.bt.testMergedBeanDefinitionPostProcessor;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zhouqian
 * @create 2021-05-11 10:39
 */
@Component
public class Merged_Person {
	private String name;
	private int age;

	//初始化方法
	@PostConstruct
	public void init(){
		System.out.println("初始化 -> lbwnbn");
	}

	//销毁方法
	@PreDestroy
	public void destroy(){
		System.out.println("销毁 -> 起飞！");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
