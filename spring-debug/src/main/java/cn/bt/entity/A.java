package cn.bt.entity;

/**
 * @Author: zhouqian
 * @Date: 2020/10/26 16:48
 */
public class A {
	private String name;

	public A() {
	}

	public A(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "我是真的帅 ->" + this.name;
	}
}
