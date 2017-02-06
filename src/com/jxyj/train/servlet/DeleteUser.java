package com.jxyj.train.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxyj.train.adminservice.AdminFileService;
import com.jxyj.train.adminservice.AdminUserService;
import com.jxyj.train.entity.UserInf;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/deleteUser.do")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int userId=Integer.valueOf(request.getParameter("userId"));
		AdminUserService adminUserService=new AdminUserService();
		JSONObject obj=new JSONObject();
		UserInf userInf=new UserInf();	
		PrintWriter out = response.getWriter();
		try {
			String username=new AdminUserService().getUserNameById(userId);
			userInf.setUserId(userId);
			userInf.setUsername(username);
			if(adminUserService.deleteUser(userInf)){
				obj.put("error", 0);
				obj.put("message", "删除成功！");
			}else{
				obj.put("error", 1);
				obj.put("message", "用户不存在，可能已经被删除");
			}
			out.write(obj.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			obj.put("error", 1);
			obj.put("message", "数据库异常，请联系管理员或者重试");
			e.printStackTrace();
			out.write(obj.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
