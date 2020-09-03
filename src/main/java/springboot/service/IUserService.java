package springboot.service;

import java.util.List;

import springboot.bean.User;

public interface IUserService {
	public List<User> findAllUser(String currentPage);
	public User selectUser(String id);
    public int deleteUser(String id);
    
    public int save(User user)throws Exception;
}
