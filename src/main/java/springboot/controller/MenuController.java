package springboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springboot.bean.Users;
import springboot.service.IMenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private IMenuService menuService;
	
	@RequestMapping("/getTreeAuthMenu")
	@ResponseBody
	public String getTreeAuthMenu(HttpSession session){
		Users users=(Users) session.getAttribute("userinfo");
		//User user=(User)redisUtil.get(session.getId());
		String menus="";
		try {
			 menus=menuService.getTreeAuthMenu(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}

}
