package ih.webapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ih.webapp.dao.UserDAO;
import ih.webapp.model.PasswordUtils;
import ih.webapp.model.User;

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
 
	
    public UserServlet() {
    	this.userDAO = new UserDAO();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		
		switch (action) {
		case "/new":
			showNewForm(request,response);
			break;
		case "/insert":
			insertUser(request,response);
			break;
		case "/delete":
			deleteUser(request,response);
			break;
		case "/edit":
			showEditForm(request,response);
			break;
		case "/update":
			updateUser(request,response);
			break;
		case "/login":
				loginUser(request,response);
			break;
		case "/logout":
			logoutUser(request,response);
			break;
		case "/register":
			registerUser(request,response);
			break;
		default:
			listUser(request,response);
			break;
		}
		
	}
	
	protected void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void logoutUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("username");
		session.invalidate();
		response.sendRedirect("list");
	}
	
	protected void registerUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		String passc = request.getParameter("passc");
		String email = request.getParameter("email");
		String salt = PasswordUtils.getSalt(30);
		String mySecurePassword = PasswordUtils.generateSecurePassword(pass, salt);
		
		UserDAO obj = new UserDAO();
		PrintWriter out = response.getWriter();
		
		if(pass.equals(passc)) {
			if(obj.registerUser(user, mySecurePassword, email, salt)) {
				response.sendRedirect("list");
			}
		    else{  
		        out.print("Error");  
		    } 
		}
	}
	
	
	protected void loginUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
		UserDAO obj = new UserDAO();
		
		System.out.println(user + " " + email + pass + " ");
		
			if(obj.validateUser(user, pass, email)) {
				HttpSession session= request.getSession();
				session.setAttribute("username", user);
				response.sendRedirect("list");
			}
		    else{  
		        request.setAttribute("error", "true");
		    }  
	}
	
	protected void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String residenza = request.getParameter("residenza");
		
		User newUser = new User(nome, cognome, email, residenza);
		try {
			userDAO.insertUser(newUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");
	}
	
	protected void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			userDAO.deleteUser(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");
	}
	
	protected void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			int id = Integer.parseInt(request.getParameter("id"));
			User existingUser = userDAO.selectUser(id);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			request.setAttribute("user", existingUser);
			dispatcher.forward(request, response);
			
		}
	
	protected void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String residenza = request.getParameter("residenza");
		
		User user = new User(id, nome, cognome, email, residenza);
		try {
			userDAO.updateUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");
	}
	
	protected void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<User> listUser = userDAO.selectAllUser();
		
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
		
	}
	
}
