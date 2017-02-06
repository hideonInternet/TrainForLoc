package com.jxyj.train.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jxyj.train.adminservice.AdminFileService;
import com.jxyj.train.entity.AnswerInf;
import com.jxyj.train.entity.FileInf;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class AdminService
 */
@WebServlet("/adminService")
public class AdminService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminFileService adminFileService = new AdminFileService();
	private ZipOutputStream zos = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminService() {
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
		String method = request.getParameter("tackle");
		String fileId = request.getParameter("fileId");
		switch (method) {
		case "delete":
			deleteFile(fileId,request,response);
			request.getRequestDispatcher("/dividePage.do").forward(request, response);
			break;
		case "download":
			downloadFile(fileId, request, response);
			break;
		default:
			break;
		}
	}

	private void downloadFile(String fileId, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		FileInf fileInf = new FileInf();
		fileInf.setFileId(fileId);
		try {
			FileInf file = adminFileService.downloadFile(fileInf);
			if(file==null){
				throw new RuntimeException("文件不存在或已经被删除");
			}
			String filename = file.getFileName();
			filename=filename.substring(filename.lastIndexOf("_")+1);
			String filepath = file.getFilePath();
			/*
			 * http header头要求其内容必须为iso8859-1编码，所以我们最终要把其编码为 ISO8859-1 编码的字符串；
			 */
			if (request.getHeader("User-Agent").contains("MSIE")) {
				filename = URLEncoder.encode(filename, "iso-8859-1");
			} else {
				filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
			}
			// 设置响应头
			response.setHeader("content-disposition", "attachment;fileName=" + filename);

			File srcFile = new File(filepath);
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
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteFile(String fileId, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		FileInf fileInf = new FileInf();
		fileInf.setFileId(fileId);
		try {
			FileInf pointedFile = adminFileService.getFileById(fileId);
			if(pointedFile ==null){
				throw new RuntimeException("文件已经被删除，请联系管理员");
			}
			File file =new File(pointedFile.getFilePath());
			File parentFile=file.getParentFile();
			if(file.exists()){
				adminFileService.deleteFile(fileInf);
				file.delete();
				parentFile.delete();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new RuntimeException("数据库异常，请联系管理员");			
		}
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
