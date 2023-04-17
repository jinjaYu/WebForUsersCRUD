package com.viewer;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class MainFrame
 */
@WebServlet("/MainFrame")
public class MainFrame extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MainFrame() {
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
       
		out.println("<a>Welcome onboard, the enginer in cyber-diver suit</a><img src='webImage/trace.gif' width=200px/>");
		out.println("<a href='myweb_project6/LoginServlet'>retraitLogin</a>");
		//out.println("<audio src='webImage/TeraForest.mp3' controls><embed name= 'TERAS' src='webImage/TeraForest.mp3' loop='true' hidden='true' autostart='true'></audio>");
		
		out.println("<iframe src='TeraForest.mp3' allow='autoplay' id='audio' hidden style='display: none;' ></iframe>");
	    //out.println("<iframe src='TeraForest.mp3' allow='autoplay' id='audio' style='display: none;'></iframe>");
	    out.println("<audio id='player' autoplay controls>");
	        out.println("<source src='webImage/TeraForest.mp3' type='audio/mp3' hidden='true'>");
	    out.println("</audio>");
		
		out.println("<h3>請選擇操作</h3>");
		out.println("<a href='/myweb_project6/ManageUsers'>Management</a><br/>");
		out.println("<a href='/myweb_project6/UserCIServlet?type=toadd'>Adding users</a><br/>");
		out.println("<a href='/myweb_project6'>Search users</a><br/>");
		out.println("<a href='/myweb_project6'>Exit system</a><br/>");
		// 發現，java ee上的css是否包在head內部並不會影響功能
		  out.println("<head>");
		    out.println("<style>");
		      out.println("input:invalid { border: 2px dashed red;}");
		      out.println("input:invalid:required { background-image: linear-gradient(to right, pink, lightgreen);}");
		      out.println("input:valid { border: 2px solid black;}");
		    out.println("</style>");
		  out.println("</head>");

	    out.println("<form>");
	      out.println("<label for='choose'>Would you prefer a banana or a cherry?</label>");
	      out.println("<input id='choose' name='i_like' required>");
	      out.println("<button>Submit</button>");
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
