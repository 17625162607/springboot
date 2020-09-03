package springboot.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import springboot.bean.User;
import springboot.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/index")
	public String findUser(Model model,String currentPage){
		List<User> findAllUser = userService.findAllUser(currentPage);
		model.addAttribute("list", findAllUser);
		int pageNum=Integer.parseInt(StringUtils.isNotEmpty(currentPage)?currentPage:"1");
		model.addAttribute("pageNum", pageNum);
		return "index";	
	}
	
	@RequestMapping("/userinfo")
	public String userinfo(String id,Model model){
		User user = userService.selectUser(id);
		model.addAttribute("user", user);
		return "user";	
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "user";	
	}
	
	@RequestMapping("/save")
	public String save(User user){
		int num=0;
		try {
			num=userService.save(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(num==1){
			return "redirect:/user/index";
		}else{
			return "user";
		}
		
		
	}
	
//	@RequestMapping("/updateuser")
//	public String updateuser(User user){
//		int updateUser = userService.updateUser(user);
//		if(updateUser==1){
//			return "redirect:/index";
//		}else{
//			return "updateuser";	
//		}
//	}
	
//	@RequestMapping("/adduser")
//	public String adduser(User user){
//		int insertUser = userService.insertUser(user);
//		return "redirect:/index";
//	}
	
	@RequestMapping("/deleteUser")
	public String deleteUser(String id){
		int deleteUser = userService.deleteUser(id);
		return "redirect:/user/index";
	}
}
