package cn.bt.selftag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Author: zhouqian
 * @Date: 2020/11/26 16:27
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("zq", new UserBeanDefinitionParser());
	}
}
