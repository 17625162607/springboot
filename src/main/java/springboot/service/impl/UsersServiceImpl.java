package springboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import springboot.bean.Users;
import springboot.dao.IUsersMapper;
import springboot.service.IUsersService;
import springboot.util.ExcelUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class UsersServiceImpl implements IUsersService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUsersMapper usersMapper;

	@Override
	public String selectUsers(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum = Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize = Integer.parseInt((String)map.get("pageSize"));
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		List<Users> usersList = usersMapper.selectUsers(map);
		//记录日志 
		logger.info("总记录数==="+page.getTotal()+",每页记录数==="+page.getPageSize()+",总页数==="+page.getPages()+","+ "当前第几页=="+page.getPageNum());
		Map<String,Object> map1=new HashMap<>();
		map1.put("rows", usersList);
		map1.put("total", page.getTotal());
		String json = JSONObject.toJSONString(map1);
		return json;
	}

	@Override
	public int save(Users users)throws Exception {
		
		if(StringUtils.isNotEmpty(users.getId())){
			return usersMapper.update(users);
		}else{
			String id = UUID.randomUUID().toString().replace("-", "");
			users.setId(id);
			return usersMapper.insertUsers(users);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class},isolation=Isolation.READ_COMMITTED)
	public int del(String id) throws Exception {
		System.err.println(id);
		Map<String,Object> map=new HashMap<>();
		map.put("id", id);
		usersMapper.delUserRoleRel(map);
		return usersMapper.del(map);
	}

	@Override
	public Users getObjById(String id) throws Exception{
		return usersMapper.getObjById(id);
	}

	@Override
	public HSSFWorkbook exportExcel(Map<String, Object> map) throws Exception {
		//定义表头
		 String[] headers={"用户名","邮件","性别","QQ","微信","注册日期"};
		 //获取数据集合
		 List<Users> list=usersMapper.selectUsers(map);
		 
		 String [][] values=new String[list.size()][headers.length];
		 for (int i = 0; i < list.size(); i++) {
           Users u = list.get(i);
           values[i][0] = u.getName();
           values[i][1] = u.getEmail();
           values[i][2] = u.getSex();
           values[i][3] = u.getQq();
           values[i][4] = u.getWeixin();
           values[i][5] = u.getRegtime();
	     }
		 HSSFWorkbook workbook=ExcelUtil.getHSSFWorkbook("用户管理", headers, values, null);
		 return workbook;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class},isolation=Isolation.READ_COMMITTED)
	public int saveUserRole(String userid, String roleid) throws Exception{
		//将用户对应的角色删除
		usersMapper.delUserRole(userid);
		//int k=1/0;
		return usersMapper.saveUserRole(userid,roleid);
	}

}
