package com.cai.dao;

import com.cai.domian.User;
import java.sql.Connection;

public interface UserDao {
/**
 * 根据用户名查找用户是否存在的方法
 * @param username 用户的用户名
 * @param conn 通向数据库的一个连接
 */
	User FindUserByName(String username, Connection conn);
/**
 * 往数据库添加一条数据的方法
 * @param user 要添加的数据的内容
 * @param conn 数据库的连接
 */
	void addUser(User user, Connection conn);
	/**
	 * 根据激活码查找用户的方法
	 * @param activecode激活码
	 * @return返回用户的bean
	 */
User findUserByActivecode(String activecode);
/**
 * 根据用户id修改用户激活状态的方法
 * @param id用户id
 * @param i用户状态
 */
	void updateState(int id, int state);
	/**
	 * 
	 根据用户名密码查找用户bean的方法
	 * @param username用户名
	 * @param password密码
	 * @return用户bean
	 */
User finUserByNameAndPsw(String username, String password);


}
