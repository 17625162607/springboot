package springboot.service;

import springboot.bean.Users;

public interface ILoginService {
	
	//登录验证
    public Users login(Users users) throws Exception;
	
	//重置密码
	public int reUpPwd(Users users)throws Exception;
}
