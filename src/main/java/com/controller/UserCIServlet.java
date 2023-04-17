package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.domain.user;
import com.service.userService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserCIServlet
 */
@WebServlet("/UserCIServlet")
public class UserCIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get response in utf-8 coding
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		// get type
		String type = request.getParameter("type");
		userService userService = new userService();
		if(type.equals("del")) {// if equal to word of del, then process user-deleting
			String id = request.getParameter("id");
			System.out.print("id="+id);
			// 調用userService執行刪除
			if(userService.delUser(id)) {
				request.setAttribute("note", "your operation have been commited successfully!");
				request.getRequestDispatcher("/Sucess").forward(request, response);
			}else {
				request.setAttribute("note", "due to some reasons, we are failing to process your operation.");
				request.getRequestDispatcher("/Failed").forward(request, response);
			}
		}else if(type.equals("tolist")) {
			// 去到修改介面
			String id = request.getParameter("id");
			// 通過id獲得一個user bean
			user user = userService.getUserById(id);
			// put our 'user' into request object, so the next Servlet can assess it.
			request.setAttribute("userinfo", user);
			// snedRedirect will failed
			request.getRequestDispatcher("/UpdateUserView").forward(request, response);
		}else if(type.equals("update")) {
			// getParameter method only get index with string type, no matter form it use to be.
			String id = request.getParameter("userId");
			String name = request.getParameter("userName");
			String email = request.getParameter("email");
			String pwd = request.getParameter("passwd");
			String grade = request.getParameter("grade");
			
			// boxing the messages we get as a user object
			user user = new user(Integer.parseInt(id), name, email, Integer.parseInt(grade), pwd);
			if(userService.upUser(user)) {
				request.setAttribute("note", "successfully modified a account info.");
				request.getRequestDispatcher("/Sucess").forward(request, response);
			}else {
				request.setAttribute("note", "failed to process your commit, pls~ checking the under-hint to adjust your input context.");
				request.getRequestDispatcher("/Failed").forward(request, response);
			}
		}else if(type.equals("toadd")) {
			request.getRequestDispatcher("/AddUserView").forward(request, response);
		}else if(type.equals("add")) {
			// get user info
			String name = request.getParameter("userName");
			String email = request.getParameter("email");
			String pwd = request.getParameter("passwd");
			String grade = request.getParameter("grade");
			// 把項目封裝成一個user對象，但與user(,,,)的格式不合，只能一一丟入
			user user = new user();
			user.setName(name);
			user.setEmail(email);
			user.setGrade(Integer.parseInt(grade));
			user.setPwd(pwd);
			if(userService.addUser(user)) {
				request.setAttribute("note", "you just created on new account");
				request.getRequestDispatcher("/Sucess").forward(request, response);
			}else {
				request.setAttribute("note", "account you intend to creating is not successful, which might out from some tipping issue~ You may check hint for remodify.");
				request.getRequestDispatcher("/Failed").forward(request, response);
			}
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
