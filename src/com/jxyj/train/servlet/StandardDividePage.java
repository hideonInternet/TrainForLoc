package com.jxyj.train.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxyj.train.adminservice.AdminGradeService;
import com.jxyj.train.entity.Standard;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class StandardDividePage
 */
@WebServlet("/standardDividePage")
public class StandardDividePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StandardDividePage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int fileId=Integer.valueOf(request.getParameter("fileId"));

		AdminGradeService adminGradeService=new AdminGradeService();
		try {
			List<Standard> stds = adminGradeService.getAllStds(fileId);
			if(stds==null || stds.size()==0){
				response.getWriter().write(getError("该次作业还未设置评分标准，是否现在设置？","-1"));
				return;
			}
			JSONArray arr=new JSONArray();
			for(Standard std:stds){
				JSONObject json=new JSONObject();
				json.put("stdContent", std.getStdContent());
				json.put("grade", std.getGrade());
				json.put("stdId", std.getStdId());
				arr.add(json);
			}
			response.getWriter().write(arr.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().write(getError("数据库异常，请联系管理员","-1"));
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String getError(String message,String status) {
		JSONObject obj = new JSONObject();
		obj.put("error", status);
		obj.put("message", message);
		return obj.toString();
	} 

}
