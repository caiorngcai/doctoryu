package com.cai.service;

import com.cai.domian.User;

public interface UserService {
	/**
	 * 往数据库添加数据的操作
	 * @param user要插入数据的Javabean对象
	 */
	void regist(User user);

	void activeUser(String activecode);
/**
 * 根据用户名密码查找用户bean的方法
 * @param username用户名
 * @param password密码
 * @return用户bean
 */
	User findUserByNameAndPsw(String username, String password);

}
