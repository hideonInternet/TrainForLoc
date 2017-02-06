package com.jxyj.train.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxyj.train.admindao.Page;
import com.jxyj.train.admindao.adminImpl.AdminUserPage;
import com.jxyj.train.entity.UserInf;
import com.jxyj.train.utils.PageBean;

/**
 * Servlet implementation class UserDividePage
 */
@WebServlet("/userDividePage.do")
public class UserDividePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDividePage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String curPage = request.getParameter("currentPage");
		Page<UserInf> page = new AdminUserPage();
		final PageBean<UserInf> pb = new PageBean<UserInf>();

		if (curPage == null || "".equals(curPage.trim())) {
			curPage = "1";
		}
		pb.setPageCount(5);
		pb.setCurrentPage(Integer.valueOf(curPage));
		page.getAll(pb);
		request.getSession().setAttribute("userpageBean", pb);
		request.getRequestDispatcher("/mUser.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
