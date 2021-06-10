package cn.bt.testAutowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhouqian
 * @create 2021-06-02 16:25
 */
@Component
public class T_B {
	private T_A t_a;

//	@Autowired
//	public T_B(T_A t_a) {
//		this.t_a = t_a;
//	}

	public String getMsg(){
		return "周谦你真的好帅！";
	}

	public T_A getT_a() {
		return t_a;
	}

	@Autowired
	public void setT_a(T_A t_a) {
		this.t_a = t_a;
	}
}
