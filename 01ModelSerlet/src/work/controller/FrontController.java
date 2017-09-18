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
		System.out.println(">>>>> �α��ο�û���� �޼��� ����");
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		System.out.println("���̵� : "+userid);
		System.out.println("��й�ȣ :"+userpw);
		
		if(userid==null || userid.trim().length()==0) {
			System.out.println("���� :���̵� ���Է�");
			request.setAttribute("errorMessage", "���̵� ���Է�");
			request.getRequestDispatcher("/Error.jsp").forward(request,response);
			return;
			
		}
		if(userpw==null||userpw.trim().length()==0) {
			System.out.println("���� :��й�ȣ ���Է�");
			response.sendRedirect("Login.jsp");  
			return;
			
		}
		String username = service.login(userid,userpw);
		if(username != null) {
			request.setAttribute("successMessage", "�α��μ��� :"+userid+"��");
			
			HttpSession session = request.getSession(true);
			
			session.setAttribute("userId", userid);
			session.setAttribute("userName", username);
			System.out.println(userid+username);
			request.getRequestDispatcher("/Service.jsp").forward(request,response);
		}
		else {
			request.setAttribute("errorMessage", "�α��ν��� ");
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
			System.out.println("������ �����Դϴ�");
				
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
