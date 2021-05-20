package cn.bt.selfCustomAutowired.annotation;

import cn.bt.selfCustomAutowired.Zhou_Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhouqian
 * @create 2021-05-20 19:20
 */
@Controller
public class PersonController {

	@Zhou_Autowired
	private PersonService personService;

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
}
