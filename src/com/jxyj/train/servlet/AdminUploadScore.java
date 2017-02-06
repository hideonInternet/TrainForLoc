package com.jxyj.train.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxyj.train.adminservice.AdminGradeService;
import com.jxyj.train.entity.Score;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class AdminUploadScore
 */
@WebServlet("/adminUploadScore")
public class AdminUploadScore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUploadScore() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Score> scoList=new ArrayList<>();
		AdminGradeService adminGradeService=new AdminGradeService();
		PrintWriter writer = response.getWriter();
		
		String userId=request.getParameter("userId");
		String operator=(String) request.getSession().getAttribute("logStatus");
		String[] scores = request.getParameterValues("score");
		String[] stdIds = request.getParameterValues("stdId");
		
		for (int index=0;index<scores.length;index++) {
			Score s=new Score();
			s.setScore(Integer.valueOf(scores[index]));
			s.setStdId(Integer.valueOf(stdIds[index]));
			s.setOperator(operator);
			s.setUserId(Integer.valueOf(userId));
			scoList.add(s);
		}
		
		if(adminGradeService.addScores(scoList)){
			JSONObject json=new JSONObject();
			json.put("message", "成绩录入成功！");
			json.put("error", 0);
			writer.write(json.toString());
		}else{
			JSONObject json=new JSONObject();
			json.put("message", "成绩录入失败，请重试或者联系管理员");
			json.put("error", 1);
			writer.write(json.toString());
		}
	}

}
