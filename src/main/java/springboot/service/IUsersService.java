package springboot.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import springboot.bean.Users;

public interface IUsersService {
	
	public String selectUsers(Map<String,Object> map)throws Exception;
	
	public int save(Users users)throws Exception;
	
	//删除
	public int del(String id)throws Exception;
	
	public Users getObjById(String id)throws Exception;
	
	//导出excel
    public HSSFWorkbook exportExcel(Map<String,Object> map)throws Exception;
    
    //保存用户与角色
  	public int saveUserRole(String userid,String roleid)throws Exception;
  	
}
