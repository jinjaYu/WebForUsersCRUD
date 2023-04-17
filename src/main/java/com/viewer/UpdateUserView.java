package com.viewer;

import java.io.IOException;
import java.io.PrintWriter;

import com.domain.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateUserView
 */
@WebServlet("/UpdateUserView")
public class UpdateUserView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// get user object send by controller
		user user = (user) request.getAttribute("userinfo");
		// showing
		out.println("<h1>User info modify</h1>");
		out.println("<img src='webImage/Lapis_lazuli(teen_concept).jpg' width=200px/><a>welcome userName loginin~</a><a href='/myweb_project6/MainFrame'>drallBackToMainFrame</a> <a href='myweb_project6/LoginServlet'>safeRetaitLogin</a><hr/>");
		out.println("<form action='/myweb_project6/UserCIServlet?type=update' method='post'>");
		out.println("<table border=1px bordercolor=black cellspacing=0 width=200px>");
		out.println("<tr><td>ID</td><td><input type='text' name='userId' readonly value='"+user.getId()+"'/></td></tr>");
		out.println("<tr><td>User Name</td><td><input type='text' name='userName' value='"+user.getName()+"'/></td></tr>");
		out.println("<tr><td>Email</td><td><input type='text' name='email' value='"+user.getEmail()+"'/></td></tr>");
		out.println("<tr><td>Grade</td><td><input type='text' name='grade' value='"+user.getGrade()+"'/></td></tr>");
		out.println("<tr><td>Password</td><td><input type='text' name='passwd' value='"+user.getPwd()+"'/></td></tr>");
		out.println("<tr><td><input type='submit' value='submit'/></td><td><input type='reset' value='rest'/></td></tr>");
		out.println("</table>");
		out.println("</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
