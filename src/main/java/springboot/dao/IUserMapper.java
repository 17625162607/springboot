package springboot.dao;

import java.util.List;

import springboot.bean.User;

public interface IUserMapper {
     public List<User> findAllUser();
     public User selectUser(int id);
     public int updateUser(User user);
     public int insertUser(User user);
     public int deleteUser(int id);
}
