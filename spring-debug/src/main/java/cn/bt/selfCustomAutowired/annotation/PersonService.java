package cn.bt.selfCustomAutowired.annotation;

import cn.bt.selfCustomAutowired.Zhou_Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouqian
 * @create 2021-05-20 19:23
 */
@Service
public class PersonService {

	@Zhou_Autowired
	private PersonDao personDao;
}
