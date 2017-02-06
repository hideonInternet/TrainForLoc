package com.jxyj.train.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.jxyj.train.adminservice.AdminUserService;
import com.jxyj.train.entity.UserInf;
import com.mysql.jdbc.StringUtils;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/registerUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> values = request.getParameterMap();
		boolean needreturn=false;
		UserInf userInf=new UserInf();
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		StringBuilder sb=new StringBuilder();
		AdminUserService adminUserService=new AdminUserService();
		System.out.println(request.getParameter("userId"));
		for(String s:values.keySet()){
			if(StringUtils.isNullOrEmpty(request.getParameter(s)) && !"userId".equals(s)){
				setError(paraMatch(s)+"����Ϊ��",obj,sb);
				needreturn=true;
			}
			try {
				BeanUtils.setProperty(userInf, s, request.getParameter(s));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				setError("��Ϣ��ʽ����ȷ����ȷ�Ϻ��ٴ��ύ",obj,sb);
				out.write(getError(obj));
				return;
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		if(!StringUtils.isNullOrEmpty(request.getParameter("userId"))){
			try {
				if(!adminUserService.hasRegistered(userInf)){
				adminUserService.updateUser(userInf);
				JSONObject obj2=new JSONObject();
				obj2.put("error", 0);
				obj2.put("message", "�û�������Ϣ�ɹ���");
				out.write(obj2.toString());
				return;
				}else{
					setError("�û����Ѿ���ע��",obj,sb);
					out.write(getError(obj));
					return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				setError("�����û���Ϣʧ�ܣ�",obj,sb);
				out.write(getError(obj));
				return;
			}		
		}
		
		if(needreturn){
			out.write(getError(obj));
			return;
		}
		if(!request.getParameter("password").equals(request.getParameter("ensurePw"))){
			setError("�����������벻��������",obj,sb);
			out.write(getError(obj));
			return;
		}
		try {
			if(adminUserService.hasRegistered(userInf)){
				setError("�û����ѱ�ע��", obj, sb);
				out.write(getError(obj).toString());
				return;
			}
			if(adminUserService.registerUser(userInf)){
				JSONObject obj2=new JSONObject();
				obj2.put("error", 0);
				obj2.put("message", "�û�ע��ɹ���");
				out.write(obj2.toString());
			}else{
				setError("ע��ʧ�ܣ������Ի���ϵ����Ա", obj, sb);
				out.write(getError(obj).toString());
				return;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			setError("���ݿ��쳣�������Ի�����ϵ����Ա",obj,sb);
			out.write(getError(obj));
			e.printStackTrace();
		}
		
	}
	private void setError(String message, JSONObject obj, StringBuilder sb) {		
		obj.put("error", 1);
		obj.put("message", sb.append(message+"\n").toString());
	}
	
	private String getError(JSONObject obj){
		return obj.toString();
	}
	private String paraMatch(String para){
		String match=null;
		switch (para) {
		case "username":
			match="�û���";
			break;
		case "password":
			match="����";
			break;
		case "ensurePw":
			match="ȷ������";
			break;	
		case "telephone":
			match="��ϵ�绰";
			break;
		case "email":
			match="��������";
			break;
		case "schName":
			match="ע��ԺУ";
			break;
		}
		return match;
	}

}
