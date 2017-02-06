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
		 * �ϴ���׼��
		 */
		DiskFileItemFactory fif = new DiskFileItemFactory();
		// 2. �����ļ��ϴ����������ļ��ϴ����Ĺ����ࣩ
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
						// �жϺ�׺��
						String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
						String ilegalExt = extName.get(0);
						if (!ilegalExt.contains(ext)) {
							out.write(getError("��׺������,ֻ�У� "+ilegalExt + "������"));
							return;  
						}
						fileDir = adminFileService.getFileNameById(fileId);
						fileDir = fileDir.substring(fileDir.lastIndexOf("_"), fileDir.indexOf("."));
						fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
						// ���ô𰸵�����
						fileName = fileDir + "_" + fileName;
						// �����ϴ��𰸵��ļ���
						String savePath = this.getServletContext()
								.getRealPath("WEB-INF" + File.separator + "upload" + File.separator + "StandardAns");
						// ������ʱ�ļ����λ��
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
						// ���ù����Ļ������Ĵ�С�����ϴ����ļ���С�����������Ĵ�Сʱ���ͻ�����һ����ʱ�ļ���ŵ�ָ������ʱĿ¼���С�
						fif.setSizeThreshold(1024 * 100);
						// ������ʱ�ļ�Ŀ¼
						fif.setRepository(tempFile);
						upload.setFileSizeMax(10 * 1024 * 1024);
						upload.setSizeMax(30 * 1024 * 1024);
						AnswerInf answerInf = new AnswerInf();
						// ----�����ϴ��ļ�����������----
						// a1. �ȵõ�Ψһ���
						String id = UUID.randomUUID().toString();
						// a2. ƴ���ļ���
						savePath = savePath + File.separator + id + "_" + fileName;
						// b. �õ��ϴ�Ŀ¼
						// c. ����Ҫ�ϴ����ļ�����
						File savefile = new File(savePath);
						answerInf.setAnswerId(fileId);
						answerInf.setOperator(operator);
						answerInf.setAnswerPath(savefile.getAbsolutePath());
						answerInf.setAnswerName(fileName);
						if (adminFileService.uploadAnswer(answerInf)) {
							InputStream is = item.getInputStream();
							FileOutputStream fos = new FileOutputStream(savefile);
							// ����һ��������
							byte buffer[] = new byte[1024];
							int length = 0;
							while ((length = is.read(buffer)) > 0) {
								fos.write(buffer, 0, length);
							}
							// �ر�������
							is.close();
							// �ر������
							fos.close();
							item.delete(); // ɾ���������ʱ��������ʱ�ļ�

							JSONObject obj = new JSONObject();
							obj.put("error", 0);
							out.print(obj.toString());
						}
					}
				}
			}

		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			out.write(getError("�ļ���С��������"));
			return;
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write(getError("�ļ��ϴ�ʧ�ܣ���ϵ����Ա"));
			return;  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write(getError("���ݿ��쳣"));
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
