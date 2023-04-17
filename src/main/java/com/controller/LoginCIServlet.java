package com.controller;

import java.io.*;
import java.sql.*;

import com.domain.user;
import com.service.userService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class LoginCIServlet
 */
//@WebServlet("/LoginCIServlet")
public class LoginCIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginCIServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		//System.out.print(name+";"+pwd);
		
		userService userService = new userService();
		user user = new user();
		user.setId(Integer.parseInt(id));
		user.setPwd(pwd);

//		    根據結果處理
			if(userService.checkUser(user)) {
				// 說明該用戶合法
				request.getRequestDispatcher("/MainFrame").forward(request, response);
			}else {
				// 則否
				//UserInfo user = new UserInfo();
				//user.setOi(false);
				//request.setAttribute("oi", false);
				
				request.setAttribute("err", "<font color='red'>Either your user id or password isn't correct!</font>");
				request.getRequestDispatcher("/LoginServlet").forward(request, response);
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
