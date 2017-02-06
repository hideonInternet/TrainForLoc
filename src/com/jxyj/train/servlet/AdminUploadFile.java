package com.jxyj.train.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jxyj.train.adminservice.AdminFileService;
import com.jxyj.train.entity.FileInf;

/**
 * Servlet implementation class AdminUploadFile
 */
@WebServlet("/adminUploadFile")
public class AdminUploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUploadFile() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * 上传作业
		 */
		DiskFileItemFactory fif = new DiskFileItemFactory();
		fif.setSizeThreshold(20 * 1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(fif);
		try {
			List<FileItem> items = upload.parseRequest(request);
			if (!items.get(0).isFormField()) {
				FileItem item = items.get(0);
				upload.setHeaderEncoding("UTF-8");
				String uuid = UUID.randomUUID().toString();
				String fileName = item.getName();
				fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
				fileName=uuid+"_"+fileName;
				String operator = (String) request.getSession().getAttribute("logStatus");
				// 设置上传文件路径，若不存在，就创建
				String savePath = this.getServletContext().getRealPath(File.separator + "WEB-INF" + File.separator
						+ "upload" + File.separator + "homework" + File.separator+fileName);
				String tempPath = this.getServletContext().getRealPath(File.separator + "WEB-INF" + File.separator
						+ "upload" + File.separator + "homeworkTemp" + File.separator+fileName);
				File saveFileDir = new File(savePath);
				File tempFileDir = new File(tempPath);
				if (!tempFileDir.exists() && !tempFileDir.isDirectory()) {
					tempFileDir.mkdir();
				}
				if (!saveFileDir.exists() && !tempFileDir.isDirectory()) {
					saveFileDir.mkdirs();
				}
				fif.setRepository(tempFileDir);
				// 3.设置上传的单个文件大小
				upload.setFileSizeMax(10 * 1024 * 1024);
				// 4.设置上传的所有文件总和大小
				upload.setSizeMax(30 * 1024 * 1024);
				savePath = savePath + File.separator + fileName;
				FileInf fileInf = new FileInf();
				fileInf.setFileName(fileName);
				fileInf.setFilePath(savePath);
				fileInf.setOperator(operator);
				
				byte[] b = new byte[8192];
				int len = 0;
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(savePath)));
				//FileOutputStream fos = new FileOutputStream(new File(savePath));
				InputStream in = item.getInputStream();
				while ((len = in.read(b)) > 0) {
					bos.write(b, 0, len);
				}
				in.close();
				bos.close();
				item.delete();
				AdminFileService adminFileService = new AdminFileService();
				adminFileService.uploadFile(fileInf);
				}else{
					request.setAttribute("message", "请上传文件");
				}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			request.setAttribute("message", "单个文件超出最大值！！！");
		} catch (FileUploadBase.SizeLimitExceededException e) {
			e.printStackTrace();
			request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("/dividePage.do").forward(request, response);			
	}

}
