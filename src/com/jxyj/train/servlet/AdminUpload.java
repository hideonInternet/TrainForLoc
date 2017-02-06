package com.jxyj.train.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jxyj.train.adminservice.AdminFileService;
import com.jxyj.train.entity.AnswerInf;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class AdminUpload
 */
@WebServlet("/adminUpload")
public class AdminUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUpload() {
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
		 * 上传标准答案
		 */
		DiskFileItemFactory fif = new DiskFileItemFactory();
		// 2. 设置文件上传解析器（文件上传核心工具类）
		ServletFileUpload upload = new ServletFileUpload(fif);
		AdminFileService adminFileService = new AdminFileService();
		PrintWriter out = response.getWriter();
		upload.setHeaderEncoding("UTF-8");
		String fileId = null;
		String method = null;
		String operator = null;
		String fileDir = null;
		List<FileItem> items = null;
		List<String> extName = new ArrayList<String>();
		extName.add("doc,txt,docx");
		try {
			if (upload.isMultipartContent(request)) {
				items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField() && "tackle".equals(item.getFieldName())) {
						method = item.getString();
					} else if (item.isFormField() && "fileId".equals(item.getFieldName())) {
						fileId = item.getString();
					} else if (item.isFormField() && "operator".equals(item.getFieldName())) {
						operator = item.getString();
					} else {
						String fileName = item.getName();
						// 判断后缀名
						String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
						String ilegalExt = extName.get(0);
						if (!ilegalExt.contains(ext)) {
							out.write(getError("后缀名不符,只有： "+ilegalExt + "被允许"));
							return;  
						}
						fileDir = adminFileService.getFileNameById(fileId);
						fileDir = fileDir.substring(fileDir.lastIndexOf("_"), fileDir.indexOf("."));
						fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
						// 设置答案的名字
						fileName = fileDir + "_" + fileName;
						// 设置上传答案的文件夹
						String savePath = this.getServletContext()
								.getRealPath("WEB-INF" + File.separator + "upload" + File.separator + "StandardAns");
						// 设置临时文件存放位置
						String tempPath = this.getServletContext().getRealPath(
								"WEB-INF" + File.separator + "upload" + File.separator + "StandardAnsTemp");
						File tempFile = new File(tempPath);
						File saveFile = new File(savePath);

						while (!tempFile.exists() && !tempFile.isDirectory()) {
							tempFile.mkdirs();
						}
						while (!saveFile.exists() && !saveFile.isDirectory()) {
							saveFile.mkdirs();
						}
						// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
						fif.setSizeThreshold(1024 * 100);
						// 设置临时文件目录
						fif.setRepository(tempFile);
						upload.setFileSizeMax(10 * 1024 * 1024);
						upload.setSizeMax(30 * 1024 * 1024);
						AnswerInf answerInf = new AnswerInf();
						// ----处理上传文件名重名问题----
						// a1. 先得到唯一标记
						String id = UUID.randomUUID().toString();
						// a2. 拼接文件名
						savePath = savePath + File.separator + id + "_" + fileName;
						// b. 得到上传目录
						// c. 创建要上传的文件对象
						File savefile = new File(savePath);
						answerInf.setAnswerId(fileId);
						answerInf.setOperator(operator);
						answerInf.setAnswerPath(savefile.getAbsolutePath());
						answerInf.setAnswerName(fileName);
						if (adminFileService.uploadAnswer(answerInf)) {
							InputStream is = item.getInputStream();
							FileOutputStream fos = new FileOutputStream(savefile);
							// 创建一个缓冲区
							byte buffer[] = new byte[1024];
							int length = 0;
							while ((length = is.read(buffer)) > 0) {
								fos.write(buffer, 0, length);
							}
							// 关闭输入流
							is.close();
							// 关闭输出流
							fos.close();
							item.delete(); // 删除组件运行时产生的临时文件

							JSONObject obj = new JSONObject();
							obj.put("error", 0);
							out.print(obj.toString());
						}
					}
				}
			}

		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			out.write(getError("文件大小超过限制"));
			return;
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write(getError("文件上传失败，联系管理员"));
			return;  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write(getError("数据库异常"));
			return;  
		}
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}

}
