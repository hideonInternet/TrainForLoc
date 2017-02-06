package com.jxyj.train.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxyj.train.adminservice.AdminGradeService;
import com.jxyj.train.entity.FileStd;
import com.jxyj.train.entity.Standard;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class AdminGradeManage
 */
@WebServlet("/adminGradeManage.do")
public class AdminGradeManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminGradeManage() {
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
		Map<String, String[]> map = request.getParameterMap();
		List<Standard> stds=new ArrayList<>();
		AdminGradeService adminGradeService =new AdminGradeService();
		int fileId=Integer.parseInt(request.getParameter("fileId"));
		String operator=(String) request.getSession().getAttribute("logStatus");
		
		FileStd fileStd=new FileStd();
		fileStd.setFileId(fileId);
		fileStd.setOperator(operator);
		
		String[] contents=map.get("stdContent");
		String[] grades=map.get("grade");
		
		for(int i=0;i<contents.length;i++){
			Standard std=new Standard();
			std.setStdContent(contents[i]);
			std.setGrade(grades[i]);
			std.setFileId(fileId);
			stds.add(std);
		}
		
		if(adminGradeService.addStd(stds, fileStd)){
			JSONObject obj = new JSONObject();
			obj.put("error", 1);
			obj.put("message", "成功");
			response.getWriter().write(obj.toString());
		}else{
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("message", "数据库异常，请联系管理员");
			response.getWriter().write(obj.toString());
		}
	}

}
