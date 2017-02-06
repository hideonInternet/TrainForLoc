package com.jxyj.train.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jxyj.train.adminservice.AdminFileService;
import com.jxyj.train.adminservice.AdminUserService;
import com.jxyj.train.entity.FileInf;
import com.jxyj.train.entity.UserInf;

/**
 * Servlet implementation class Login
 */
@WebServlet(urlPatterns = { "/login.do" }, initParams = {
		@WebInitParam(name = "MANAGERPAGE", value = "/WEB-INF/jsp/mindex.jsp"),
		@WebInitParam(name = "USERPAGE", value = "/WEB-INF/jsp/uindex.jsp"),
		@WebInitParam(name = "INDEXPAGE", value = "/login.jsp") })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String MANAGERPAGE;
	private String USERPAGE;
	private String INDEXPAGE;
	private String PAGE;
	private String message;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		MANAGERPAGE = this.getInitParameter("MANAGERPAGE");
		USERPAGE = this.getInitParameter("USERPAGE");
		INDEXPAGE = this.getInitParameter("INDEXPAGE");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		AdminUserService adminUserService = new AdminUserService();
		AdminFileService adminFileService = new AdminFileService();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String identity = request.getParameter("identity");
		String validate = request.getParameter("validate");
		String validateCode = (String) request.getSession().getAttribute(ValidateCode.RANDOMCODEKEY);

			if (validate == null || validateCode ==null ||!validateCode.equalsIgnoreCase(validate)) {
				message = "验证码校验错误！";
				request.setAttribute("error", message);
				PAGE = INDEXPAGE;
			} else {
				UserInf userInf = new UserInf();
				userInf.setUsername(username);
				userInf.setPassword(password);
				try {
					if (adminUserService.isRegistered(userInf, identity)) {
						HttpSession session = request.getSession();
						session.setAttribute("logStatus", username);
						session.setAttribute("identity",identity);
						if (identity != null && "管理员".equals(identity)) {
							PAGE = MANAGERPAGE;
						} else if (identity != null && "普通用户".equals(identity)) {
							session.setAttribute("userId", adminUserService.getUserIdByUserName(username));
							PAGE = USERPAGE;
						}
					} else {
						message = "用户或密码错误";
						PAGE = INDEXPAGE;
						request.setAttribute("error", message);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			request.getRequestDispatcher(PAGE).forward(request, response);
		}
	

}
