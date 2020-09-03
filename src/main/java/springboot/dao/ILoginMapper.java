package springboot.dao;

import springboot.bean.Users;

public interface ILoginMapper {
	//登录
	public Users login(Users users) throws Exception;
	
	//重置密码
	public int reUpPwd(Users users) throws Exception;
}
