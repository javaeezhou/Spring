package cn.bt.factoryBean;

import cn.bt.entity.Student;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author zhouqian
 * @create 2021-01-22 11:14
 */
public class MyFactoryBean implements FactoryBean {
	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public Object getObject() throws Exception {
		return new Student("韩金轮",32);
	}

	@Override
	public Class<?> getObjectType() {
		return Student.class;
	}
}
