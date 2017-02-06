package com.jxyj.train.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxyj.train.admindao.Page;
import com.jxyj.train.admindao.adminImpl.AdminPage;
import com.jxyj.train.entity.FileInf;
import com.jxyj.train.entity.UserInf;
import com.jxyj.train.utils.PageBean;
import com.mysql.jdbc.StringUtils;

/**
 * Servlet implementation class Page
 */
@WebServlet( urlPatterns={"/dividePage.do"},
			 initParams={
				@WebInitParam(name="MANAGERPAGE",value="/mFile.jsp"),
				@WebInitParam(name="USERPAGE",value="/userpage/uFile.jsp")
			 })
public class DividePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String MANAGERPAGE;
	private static String USERPAGE;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DividePage() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		 MANAGERPAGE=getInitParameter("MANAGERPAGE");
		 USERPAGE=getInitParameter("USERPAGE");
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String curPage = request.getParameter("currentPage");
		Page<FileInf> page = new AdminPage();
		final PageBean<FileInf> pb = new PageBean<FileInf>();

		if (curPage == null || "".equals(curPage.trim())) {
			curPage = "1";
		}
		pb.setPageCount(5);
		pb.setCurrentPage(Integer.valueOf(curPage));
		page.getAll(pb);
		request.getSession().setAttribute("pageBean", pb);
		
		String PAGE=null;
		String identity=
				(String) request.getSession().getAttribute("identity");
		if(!StringUtils.isNullOrEmpty(identity) && "管理员".equals(identity)){
			PAGE=MANAGERPAGE;
		}else if(!StringUtils.isNullOrEmpty(identity) && "普通用户".equals(identity)){
			PAGE=USERPAGE;
		}
		request.getRequestDispatcher(PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
