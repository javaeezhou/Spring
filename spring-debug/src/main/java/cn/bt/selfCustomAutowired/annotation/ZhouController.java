package cn.bt.selfCustomAutowired.annotation;

import cn.bt.selfCustomAutowired.Zhou_Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhouqian
 * @create 2021-05-20 19:20
 */
@Controller
public class ZhouController {

	@Zhou_Autowired
	private ZhouService zhouService;

	public void show(){
		zhouService.show();
	}
}
