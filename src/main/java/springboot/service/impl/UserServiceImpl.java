package springboot.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import springboot.bean.User;
import springboot.dao.IUserMapper;
import springboot.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IUserMapper userMapper;

	@Override
	public List<User> findAllUser(String currentPage) {
		//分页插件
		int pageSize=3;//多少行数据
		int pageNum = Integer.parseInt(StringUtils.isNotEmpty(currentPage)?currentPage:"1");//第几页
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		List<User> list = userMapper.findAllUser();
		//日志
		logger.info("总记录数==="+page.getTotal()+",每页记录数==="+page.getPageSize()+",总页数==="+page.getPages()+","
				+ "当前第几页=="+page.getPageNum());
		return list;
	}

	@Override
	public User selectUser(String id) {
		return userMapper.selectUser(Integer.parseInt(id));
	}


	@Override
	public int deleteUser(String id) {
		return userMapper.deleteUser(Integer.parseInt(id));
	}

	@Override
	public int save(User user) throws Exception {
		int a=0;
		if(user.getId()>0){
			a=userMapper.updateUser(user);
		}else{
			a=userMapper.insertUser(user);
		}
		return a;
	}

	

}
