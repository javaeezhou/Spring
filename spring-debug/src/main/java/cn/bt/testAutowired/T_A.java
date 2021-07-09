package cn.bt.testAutowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhouqian
 * @create 2021-06-02 16:24
 */
// 测试循环依赖
@Component
public class T_A {
	@Resource
	private T_B t_b;

	private String msg;

	// 该注解放在有参构造器上，在spring解析构造器的过程中，会找到该参数，如果是引用类型则进行实例化
//	@Autowired
//	public T_A(T_B b) {
////		this.msg = b.getMsg();
//		this.t_b = b;
//	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T_B getT_b() {
		return t_b;
	}
//	@Autowired
	public void setT_b(T_B t_b) {
		this.t_b = t_b;
	}
}
