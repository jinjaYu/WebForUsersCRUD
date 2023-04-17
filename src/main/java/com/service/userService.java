package com.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.domain.user;
import com.util.sqlhelper;

public class userService {
	// 獲取pageCount
	public int getPageCount(int pageSize) {
		String sql = "select count(*) from dbo.users";
		int rowCount=0;
		ResultSet rs = sqlhelper.executeQuery(sql, null);
		try {
			rs.next();
			rowCount = rs.getInt(1);
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			sqlhelper.close(rs, sqlhelper.getPs(), sqlhelper.getCt());
		}
		return (rowCount-1)/pageSize+1;
	}
	// 按照分頁來獲取用戶
	// 實務上分層封裝對象，ResultSet()--> User對象--> ArrayList(集合)
	// advantage, 1. arraylist中封裝user對象，更佳符合面對對象的編程方式(OOP)
	// 2. user對象封裝到arraylist中之後與resultSet切斷關係，利於rs的資源釋放回收
	public ArrayList getUsersByPage(int pageNow, int pageSize) {
		
		ArrayList<user> alist = new ArrayList<user>();
		
		String sql = "select * from (select *, ROW_NUMBER() over(order by grade asc) as rn from dbo.users) as a"+
				" where a.rn >= "+((pageNow-1)*pageSize+1)+" and rn <= "+pageNow*pageSize;
		
		ResultSet rs = sqlhelper.executeQuery(sql, null);
		// 二次封裝，把ResultSet()--> User對象--> ArrayList(集合)
			
		try {
			while(rs.next()) {
				user user = new user();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGrade(rs.getInt(4));
				alist.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			sqlhelper.close(rs, sqlhelper.getPs(), sqlhelper.getCt());
		}
		return alist;
	}
	// 添加 修改 刪除用戶
	public boolean delUser(String id) {
		boolean b = true;
		String sql = "delete from dbo.users where userId=?";
		String parameters[] = {id};
		try {
			sqlhelper.executeUpdate(sql, parameters);
		}catch (Exception e) {
			b = false;
		}
		return b;
	}
	// 通過id獲取用戶訊息
	public user getUserById (String id) {
		user user = new user();
		String sql = "select * from dbo.users where userId=?";
		String parameters[] = {id};
		ResultSet rs=sqlhelper.executeQuery(sql, parameters);
		try {
			if(rs.next()) {
				// 二次封裝
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGrade(rs.getInt(4));
				user.setPwd(rs.getString(5));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			sqlhelper.close(rs, sqlhelper.getPs(), sqlhelper.getCt());
		}
		return user;
	}
	public boolean upUser(user user) {
		String sql="update dbo.users set userName=?,email=?,grade=?,passwd=? where userId=?";
		String parameters[] = {user.getName(),user.getEmail(),user.getGrade()+"",user.getPwd(),user.getId()+""};
		boolean x=true;
		try {
			sqlhelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			x=false;
		}
		return x;

	}
	// add user
	public boolean addUser(user user) {
		boolean x=true;
		String sql = "insert into dbo.users values(next value for users_seq,?,?,?,?)";
		String[] parameters = {user.getName(),user.getEmail(),user.getGrade()+"",user.getPwd()};
		try {
			sqlhelper.executeUpdate(sql, parameters);
		}catch(Exception e) {
			x=false;
		}
		return x;
	}
	// 寫一個驗證用戶是合法的函數
	public boolean checkUser(user user) {
		boolean b=false;
		
		// 使用sqlhelper來完成查詢任務
		String sql = "select * from dbo.users where userId=? and passwd=?";
		String parameters[] = {user.getId()+"", user.getPwd()};// 小技巧(user.getId()+"")，把int強轉乘str
		ResultSet rs = sqlhelper.executeQuery(sql, parameters);
		// 根據rs判斷用戶是否存在
		try {
			if(rs.next()) {
				b=true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			sqlhelper.close(rs, sqlhelper.getPs(), sqlhelper.getCt());
		}
		return b;
	}
}
