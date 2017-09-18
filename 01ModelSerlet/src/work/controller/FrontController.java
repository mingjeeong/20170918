package work.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.service.Service;

/**
 * Servlet implementation class FrontController
 * 
 * 
 */


public class FrontController extends HttpServlet {
	
	private Service service = new Service();
	
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(">>>>> 로그인요청서비스 메서드 수행");
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		System.out.println("아이디 : "+userid);
		System.out.println("비밀번호 :"+userpw);
		
		if(userid==null || userid.trim().length()==0) {
			System.out.println("오류 :아이디 미입력");
			request.setAttribute("errorMessage", "아이디 미입력");
			request.getRequestDispatcher("/Error.jsp").forward(request,response);
			return;
			
		}
		if(userpw==null||userpw.trim().length()==0) {
			System.out.println("오류 :비밀번호 미입력");
			response.sendRedirect("Login.jsp");  
			return;
			
		}
		String username = service.login(userid,userpw);
		if(username != null) {
			request.setAttribute("successMessage", "로그인성공 :"+userid+"님");
			
			HttpSession session = request.getSession(true);
			
			session.setAttribute("userId", userid);
			session.setAttribute("userName", username);
			System.out.println(userid+username);
			request.getRequestDispatcher("/Service.jsp").forward(request,response);
		}
		else {
			request.setAttribute("errorMessage", "로그인실패 ");
			request.getRequestDispatcher("/Error.jsp").forward(request,response);
			
		}
		
		
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(">>>action : "+action);
		switch(action) {
		case "login" :
			login(request,response);
			break;
		default :
			System.out.println("미지원 서비스입니다");
				
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request, response);
	}

}
