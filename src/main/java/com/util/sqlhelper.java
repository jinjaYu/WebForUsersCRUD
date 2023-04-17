package com.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class sqlhelper {
	// 定義需要的變數
	private static Connection ct = null;
	// 在大多數況下，我們使用的是PreparedStatement來代替Statement
	// 這樣可以防止sql注入
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;
	
	// 連接數據庫
	private static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	// 讀取配置文件，沒有設properties配置文件
	
	// 加載驅動，只需執行一次
	// 得到連接
	public static Connection getConnection() {
		try {
			Class.forName(driver);
			ct = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-AVJCSDFI;DatabaseName=db_rider;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ct;
	}
	// 分業問題??
	public static ResultSet executeQuery2() {
		return null;
	}
	
	// 調用儲存過，有返回Result
	// sql call過程(?,?,?)
	public static CallableStatement callPro2
	(String sql, String[] inparameters, Integer[] outparameters) {
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			if(inparameters!=null) {
				for(int i=0;i<inparameters.length;i++) {
					cs.setObject(i+1, inparameters[i]);
				}
			}
			// 給out參數賦值
			if(outparameters!=null) {
				for(int i=0;i<outparameters.length;i++) {
					cs.registerOutParameter(inparameters.length+1+i, outparameters[i]);
					//////////////////////////////////////////////////
				}
			}
			cs.execute();
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			// 不需要關閉
		}
		return cs;
	}
	
	// 調用儲存過，有返回的Result
	// sql call過程(?,?,?)
	public static void callPro1(String sql, String[] parameters) {
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			// 給?賦值
			if(parameters!=null) {
				for(int i=0;i<parameters.length;i++) {
					cs.setObject(i+1, parameters[i]);
				}
			}
			cs.execute();
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, cs, ct);
		}
	}
	
	// 統一的select
	// ResultSet-->ArrayList
	public static ResultSet executeQuery(String sql, String[] parameters) {
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			if(parameters!=null&&!parameters.equals("")) {
				for(int i=0;i<parameters.length;i++) {
					ps.setString(i+1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally {
			//close(rs, ps, ct);
		}
		return rs;
	}
	
	// 如果有多個update/delete/insert[需要考慮事務]
	public static void executeUpdate2(String[] sql, String[][] parameters) {
		try {
			// 核心
			// 1.活得連接
			ct = getConnection();
			
			// 因為這時用戶可能傳入多個sql語句
			ct.setAutoCommit(false);
			// ...
			for(int i=0;i<sql.length;i++) {
				if(parameters[i]!=null) {
					ps = ct.prepareStatement(sql[i]);
					for(int j=0;j<parameters[i].length;j++) {
						ps.setString(j+1, parameters[i][j]);
					}
					ps.executeUpdate();
				}
			}
			ct.commit();
		}catch(Exception e) {
			e.printStackTrace();
			// 回滾
			try {
				ct.rollback();
			}catch(SQLException el) {
				el.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}finally {
			close(rs, ps, ct);
		}
	}
	// 先寫一個update/delete/insert
	// sql格式: update表名 set 字段名稱=? where 字段=?
	// parameters應該是{"abc", 23};
	public static void executeUpdate(String sql, String[] parameters) {
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			// 給?賦值
			if(parameters!=null) {
				for(int i=0;i<parameters.length;i++) {
					ps.setString(i+1, parameters[i]);
				}
			}
			// 執行
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();// 開發階段
			// 拋出異常，拋出運行異常，可以給調用該函數的函數一個選擇
			// 看是要處理，或是放棄處理
			throw new RuntimeException(e.getMessage());
		}finally {
			close(rs, ps, ct);
		}
	}
	
	// 關閉資源的函數
	public static void close(ResultSet rs, Statement ps, Connection ct) {
		if(rs!=null) {
			try {
				rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		// 關閉資源(先開後關)
		if(ps!=null) {
			try {
				ps.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			ps = null;// 使用垃圾回收機制
		}
		if(ct!=null) {
			try {
				ct.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			ct = null;
		}
	}
	
	public static Connection getCt() {
		return ct;
	}
	public static PreparedStatement getPs() {
		return ps;
	}
	public static ResultSet getRs() {
		return rs;
	}
	public static CallableStatement getCs() {
		return cs;
	}
}