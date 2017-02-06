package com.jxyj.train.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxyj.train.admindao.adminImpl.AdminScorePage;
import com.jxyj.train.adminservice.AdminGradeService;
import com.jxyj.train.adminservice.AdminUserService;
import com.jxyj.train.entity.UserScore;
import com.jxyj.train.utils.PageBean;

/**
 * Servlet implementation class ScoreDividePage
 */
@WebServlet("/scoreDividePage")
public class ScoreDividePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScoreDividePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		AdminUserService adminUserService = new AdminUserService();
		AdminGradeService adminGradeService = new AdminGradeService();
		AdminScorePage adminScorePage = new AdminScorePage();

		String userName = null;
		String userId = request.getParameter("userId");
		String w = request.getParameter("w");
		String curPage = request.getParameter("currentPage");

		if (curPage == null || "".equals(curPage.trim())) {
			curPage = "1";
		}
		try {
			userName = adminUserService.getUserNameById(Integer.valueOf(userId));
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageBean<UserScore> pb = new PageBean();
		pb.setCurrentPage(Integer.valueOf(curPage));
		pb.setPageCount(5);
		boolean flag = adminScorePage.getAll(pb, "userId", Integer.valueOf(userId));
		if (flag) {
			request.setAttribute("pageBean", pb);
			request.setAttribute("map", pb.getObj());
			request.setAttribute("userName", userName);
			request.setAttribute("error", true);
			
		}else{
			request.setAttribute("error", false);
		}
		request.getRequestDispatcher("mCheckScore.jsp").forward(request, response);
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
