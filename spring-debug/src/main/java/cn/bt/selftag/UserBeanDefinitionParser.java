package cn.bt.selftag;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @Author: zhouqian
 * @Date: 2020/11/26 15:45
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	@Override
	@SuppressWarnings("rawtypes") // "rawtypes"，这个参数是告诉编译器不用提示使用基本类型参数时相关的警告信息。一般是在通过传参调用某个方法的时候进行标识。
	protected Class<?> getBeanClass(Element element) {
		return User.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		String username = element.getAttribute("username");
		String email = element.getAttribute("email");
		if(StringUtils.hasText(username)){
			builder.addPropertyValue("username", username);
		}
		if(StringUtils.hasText(username)){
			builder.addPropertyValue("email", email);
		}
	}


}
