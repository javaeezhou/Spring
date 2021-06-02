package cn.bt.testAutowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhouqian
 * @create 2021-06-02 16:24
 */
@Component
public class T_A {
	private String msg;

	@Autowired
	public T_A(T_B b) {
		this.msg = b.getMsg();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
