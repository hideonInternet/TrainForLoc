package com.jxyj.train.servlet.userservlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxyj.train.adminservice.AdminFileService;
import com.jxyj.train.entity.AnswerInf;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class DownloadStaAns
 */
@WebServlet("/downloadStaAns")
public class DownloadStaAns extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadStaAns() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileId=request.getParameter("fileId");
		AdminFileService adminFileService=new AdminFileService();
		AnswerInf answerInf=null;
		try {
			answerInf = adminFileService.downloadStdAns(fileId);
			if(answerInf ==null){
				response.getWriter().write(getError("文件还未上传或已被删除"));
				return;
			}
			String answerPath = answerInf.getAnswerPath();
			String answerName = answerInf.getAnswerName();
			answerName=answerName.substring(answerName.lastIndexOf("_")+1);
			if (request.getHeader("User-Agent").contains("MSIE")) {
				answerName = URLEncoder.encode(answerName, "iso-8859-1");
			} else {
				answerName = new String(answerName.getBytes("UTF-8"), "iso-8859-1");
			}
			// 设置响应头
			response.setHeader("content-disposition", "attachment;fileName=" + answerName);
			
			File srcFile = new File(answerPath);
			int readLen = -1;
			byte[] readBuf = new byte[8192];

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));

			// 用response获取输出流
			OutputStream out = response.getOutputStream();
			while ((readLen = bis.read(readBuf)) != -1) {
				out.write(readBuf, 0, readLen);
			}
			//// 关闭
			out.close();
			bis.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(getError("数据库异常，请联系管理员"));
			e.printStackTrace();
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
	
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}

}
