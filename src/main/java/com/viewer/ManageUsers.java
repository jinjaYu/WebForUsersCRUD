package com.viewer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.domain.user;
import com.service.userService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ManageUsers
 */
@WebServlet("/ManageUsers")
public class ManageUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script type='text/javascript' language='javasscript'>");
			out.println("function gotoPageNow() { var jump=document.getElementById('jump');"+
				"window.alert('pageNow='+jump.value);"+
				"window.open('/myweb_project6/ManageUsers?pageNow='+jump.value, '_self');}"+
				"function confirmOper(){return window.confirm('sure to delete this account?')};");
		out.println("</script>");
		out.println("<h1>welcome to user management interface</h1>");
		out.println("<img src='webImage/Lapis_lazuli(teen_concept).jpg' width=200px/><a>welcome userName loginin~</a><a href='/myweb_project6/MainFrame'>drallBackToMainFrame</a> <a href='myweb_project6/LoginServlet'>safeRetaitLogin</a><hr/>");
		// 定義分頁變數
		int pageNow = 1;
		String pageNowTemp = request.getParameter("pageNow");
		// 有傳入值再設定，其則使用default
		if(pageNowTemp!=null) {
			pageNow = Integer.parseInt(request.getParameter("pageNow"));
			
		}
		
		int pageSize = 3;
		int pageCnt;
//		int rowCnt;
		// 分頁邏輯使用
//		pageCnt = rowCnt%pageSize == 0? rowCnt/pageSize: rowCnt/pageSize+1;
//		pageCnt = (rowCnt-1)/pageSize+1;
		
		try {
			userService userService = new userService();
			pageCnt = userService.getPageCount(pageSize);
			
			
			ArrayList<user> alist = userService.getUsersByPage(pageNow, pageSize);
			
			out.println("<table border=1 width=500px>");
			out.println("<tr><th>UserId</th><th>UserName</th><th>Email</th><th>Grades</th><th>Amend</th><th>Delete</th></tr>");
			for(user user:alist) {
				out.println("<tr><th>"+user.getId()
						+ "</th><th>"+user.getName()
						+ "</th><th>"+user.getEmail()
						+ "</th><th>"+user.getGrade()
						+"<th><a href='/myweb_project6/UserCIServlet?id="+user.getId()+"&type=tolist'>Amend</a></th>"
						+"<th><a onClick='return confirmOper();' href='/myweb_project6/UserCIServlet?id="+user.getId()+"&type=del'>Delete</a></th></tr>;");
			}
			out.println("</table><br/>");
			if(pageNow!=1) {
			out.println("<a href='/myweb_project6/ManageUsers?pageNow="+(pageNow-1)+"'>{Last}</a>");
			}
			for(int i=1; i<=pageCnt;i++) {
				// 提醒href所帶的變數pageNow要埋進傳送url裡面，就要注意i的擺放位置喔!!
				out.println("<a href='/myweb_project6/ManageUsers?pageNow="+i+"'>{"+i+"}</a>");
			}
			if(pageNow!=pageCnt)
			out.println("<a href='/myweb_project6/ManageUsers?pageNow="+(pageNow+1)+"'>{Next}</a>");
			// 顯示分頁訊息
			out.println("&nbsp;&nbsp;&nbsp;page:"+pageNow+" /for total:"+pageCnt+"<br/>");
			out.println("Change pages to <input type='text' name='jump' id='jump'/><input type='button' onClick='gotoPageNow()' value='select'/>");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
