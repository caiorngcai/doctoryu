package com.cai.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.cai.domian.User;
import com.cai.util.DaoUtils;


public class UserDaoImpl implements UserDao {

	public User FindUserByName(String username, Connection conn) {
		String sql = "select * from user where username = ?";
		try{
			QueryRunner runner = new QueryRunner();
			return runner.query(conn,sql, new BeanHandler<User>(User.class),username);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void addUser(User user, java.sql.Connection conn) {
		String sql="insert into user values(null,?,?,?,?,?,?,?,null)";
		QueryRunner runner=new QueryRunner();
		try {
			runner.update(conn, sql,user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getRole(),user.getState(),user.getAvtivecode());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public User findUserByActivecode(String activecode) {
		String sql = "select * from user where activecode = ?";
		try{
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),activecode);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public void updateState(int id, int state) {
		String sql = "update user set state = ? where id=?";
		try{
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			runner.update(sql,state,id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}

	public User finUserByNameAndPsw(String username, String password) {
		String sql="select * from user where username=? and password=?";
		try {
			QueryRunner runner=new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),username,password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
