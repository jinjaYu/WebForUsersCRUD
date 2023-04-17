package com.viewer;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		// jdbc connect
		Connection ct = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
//			首先引入jar包，看l25 20:00流程
//		    加載驅動
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		    取得連線
			ct = DriverManager.getConnection
			("jdbc:sqlserver://LAPTOP-AVJCSDFI;DatabaseName=db_rider;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true");
//		    創建PrepareStatement
			ps = ct.prepareStatement("select * from dbo.users");
//		    執行操作

			rs = ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int colSize = rsmd.getColumnCount();
//		    根據結果處理
			while(rs.next()) {
				for(int i=1; i<colSize+1; i++) {
					out.print(rs.getString(i));
				}
				out.println("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 關閉資源
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
		out.println("<img src='webImage/google.gif' width=500px/><hr/>");
		
		out.println("<h1>user login:</h1>");
		out.println("<form action='/myweb_project6/LoginCIServlet' method='post'>");
		out.println("user id: <input type='text' name='id'><br/>");
		out.println("password: <input type='password' name='pwd'><br/>");
		out.println("sexual: <input type='radio' name='male'/>Male <input type='radio' name='female'/>Female <input type='radio' name='biosexual'/>Bios <input type='radio' name='disexual'/>Dials");
		out.println("hobbies: <input type='checkbox' name='h' value='h1'/>h1 <input type='checkbox' name='h' value='h2'/>h2 <input type='checkbox' name='h' value='h3'/>h3 <input type='checkbox' name='h' value='h4'/>h4");
		out.println("<select name='city'><option value='桃'>桃園</option><option value='北七'>台北</option><option value='彰'>彰化</option></select>");
		out.println("<input type='submit' name='sub'><br/>");
		out.println("</form>");


		// 改用sendRedirect就不會有事了
		String errInfo = (String) request.getAttribute("err");
		if(errInfo!=null) {
			out.println("pwd error: "+errInfo);
		}
		out.println("<script type='text/javascript'>alert(Valid());</script>");
		// 只能輸入int id被輸入string會抱錯，怎麼辦?
//		boolean ois =request.getAttribute("oi").equals(true);
//		if(ois) {
//			out.println(ois+"pwd error "+request.getAttribute("oi"));
//		}else if(!ois){
//			out.println(ois+"你寧願獨自一人把玩著手鞠，也不甘願與惡魔們共桌為伍?<br/>我理解這份艱苦隱忍所會帶來的果實，但最終臨死時也都只會有你一人喔!"+request.getAttribute("oi"));
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
