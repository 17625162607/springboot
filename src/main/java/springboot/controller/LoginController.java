package springboot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springboot.bean.Users;
import springboot.service.ILoginService;
import springboot.util.MD5;
import springboot.util.RedisUtil;
import springboot.util.SMS;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private ILoginService loginService;
	@Autowired
	private RedisUtil redisUtil;
	
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping("/login")
	public String login(Users users,HttpSession session,Model model){
		try {
			//对密码加密
			String md5pwd=MD5.encodeMD5(users.getName(), users.getPassword());
			users.setPassword(md5pwd);
			Users u=loginService.login(users);
			if(u!=null && StringUtils.isNotEmpty(u.getId())){
				session.setAttribute("userinfo", u);
				return "redirect:/tomain";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message","用户名或密码错误");
		return "login";
	}
	
	//发送验证码
	@RequestMapping("/sendCode")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String sendCode(String tel){
		String res="";
		//短信验证码生命周期(有效期),目前设置了90秒
		long time=1*90;
		if(StringUtils.isNotEmpty(tel)){
			//随机验证码6位
			String valcode=SMS.getRandCode(6);
			Map<String,String> map=new HashMap<String,String>();
			map.put("code", valcode);
			String param=JSONObject.toJSONString(map);
			//发送短信，返回发送结果
			String mess=SMS.sendSms(tel, param);
			//将json格式的字符串转化为对象
			Map<String,String> resmap=JSONObject.parseObject(mess, Map.class);
			//短信发送返回结果的状态码
			String statusCode=resmap.get("Code");
			if("OK".equals(statusCode)){
				//发送成功,记录验证码到redis
				boolean f=redisUtil.set(tel, valcode, time);
				if(f){
					res=time+"_OK";
				}
			}
		}
		System.err.println(res);
		return res;
	}
	//提交验证码
	@RequestMapping("/subValCocde")
	@ResponseBody
	public String subValCocde(String tel,String valcode){
		String res="0";
		//通过key(手机号),获取验证码,验证码由于有生命周期，所以过期后是取不到的
		String valicode=redisUtil.get(tel)==null?null:(String)redisUtil.get(tel);
		if(valcode.equals(valicode)){
			//如果用户提交过来的验证码与系统发送给用户的一致，则返回1，反之返回0
			res="1";
		}
		return res;
	}
	//重置密码
	@RequestMapping("/reUpPwd")
	@ResponseBody
	public String reUpPwd(Users users){
		int k=0;
		try {
			k=loginService.reUpPwd(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k+"";
	}

}
