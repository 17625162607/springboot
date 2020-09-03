package springboot.dao;

import java.util.List;
import java.util.Map;



import springboot.bean.Users;

public interface IUsersMapper {
	//查询
	public List<Users> selectUsers(Map<String,Object> map)throws Exception;
	//增加
	public int insertUsers(Users users)throws Exception;
	
	//修改
	public int update(Users users)throws Exception;
	
	//删除
	public int del(Map<String,Object> map)throws Exception;
	
	//通过用户ID,删除用户角色关联表信息
	public int delUserRoleRel(Map<String,Object> map) throws Exception;
	
	public Users getObjById(String id)throws Exception;
	//通过用户ID，删除用户对应的角色
	public int delUserRole(String userid) throws Exception;
	
	public int saveUserRole(String userid, String roleid);
	
	
}
