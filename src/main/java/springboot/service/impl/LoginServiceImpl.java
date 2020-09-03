package springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bean.Users;
import springboot.dao.ILoginMapper;
import springboot.service.ILoginService;
import springboot.util.MD5;

@Service
public class LoginServiceImpl implements ILoginService{
	@Autowired
	private ILoginMapper loginMapper;

	@Override
	public Users login(Users users) throws Exception {
		return loginMapper.login(users);
	}

	@Override
	public int reUpPwd(Users users) throws Exception {
		//对密码加密
		//String md5pwd=MD5.encryptPassword(user.getName(), user.getPassword(), null);
		String md5pwd=MD5.encodeMD5(users.getName(), users.getPassword());
		users.setPassword(md5pwd);
		return loginMapper.reUpPwd(users);
	}

}
