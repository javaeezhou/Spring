package cn.bt.selfCustomAutowired;

import cn.bt.selfCustomAutowired.annotation.ZhouController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouqian
 * @create 2021-05-20 19:18
 */
public class Test_Zhou_Autowired {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("test_Zhou_Autowired.xml");
		ZhouController zhouController = (ZhouController) ac.getBean("zhouController");
		zhouController.show();
	}
}
