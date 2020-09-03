package springboot.service;

import java.util.List;
import java.util.Map;

import springboot.bean.Role;
import springboot.bean.Tree;

public interface IRoleService {
	//分配角色，查询就是信息
    public String getRoles(String userid)throws Exception;
    
    public String query(Map<String,Object> map)throws Exception;
    
    //增加 修改
    public int save(Role role)throws Exception;
    
    //删除
    public int del(String id)throws Exception;
    
    //通过ID查询对象
  	public Role getObjById(String id)throws Exception;
  	
    //通过角色 查询分配好的菜单
  	public List<Tree> getAuthMenu(String roleid,String pid)throws Exception;
  	
  	//保存角色分配好的菜单信息
  	public int saveAuthMenu(String roleid,String mids)throws Exception;
}
