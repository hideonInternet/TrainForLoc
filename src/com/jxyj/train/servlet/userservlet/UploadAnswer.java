package com.jxyj.train.servlet.userservlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jxyj.train.adminservice.AdminFileService;
import com.jxyj.train.adminservice.AdminUserService;
import com.jxyj.train.entity.UserAnsInf;
import com.jxyj.train.userservice.UserFileService;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UploadAnswer
 */
@WebServlet("/uploadAnswer")
public class UploadAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadAnswer() {
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
		DiskFileItemFactory fif = new DiskFileItemFactory();
		fif.setSizeThreshold(20 * 1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(fif);
		// 3.�����ϴ��ĵ����ļ���С
		upload.setFileSizeMax(10 * 1024 * 1024);
		PrintWriter out = response.getWriter();
		// ���ñ����ʽ
		upload.setHeaderEncoding("UTF-8");

		AdminFileService adminFileService = new AdminFileService();
		UserFileService userFileService = new UserFileService();

		String userFileId = null; // �ڸ���ҵ�𰸶��ڵĹ���Ա�ϴ���ҵID
		String pointedName = null;// ����Ա�ϴ���ҵID��Ӧ���ļ���
		String userFileName = null;// �û��ϴ����ļ���
		String userFilePath = null;// ��ҵ����λ��
		String userOperator = null;// �ôβ����Ĳ�����
		String userSchool = null;
		String savePath = null;// �ļ��ı���·������һ�θ�ֵΪ��ȷ�����ļ�·�����ڣ��ڶ��θ�ֵΪ�˱�����ļ�
		String tempPath = null;// ��ʱ�ļ�����·�������ļ��ϴ���С�����ˣ����õ�Threshold������ʱ���������ļ�·��
		String legalExt = "zip,rar";
		String userFileExt = null;// �ϴ��ļ���׺
		String caution = null;
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField() && "fileId".equals(item.getFieldName())) {
					userFileId = item.getString();
				} // ���ϴ��ļ����в�������
				if (!item.isFormField()) {
					if(item.getSize()==0L){
						out.write(getError("������ѡ��һ���ļ�"));
						return;
					}
					userOperator = (String) request.getSession().getAttribute("logStatus");
					pointedName = adminFileService.getFileNameById(userFileId);
					userSchool = new AdminUserService().getSchByusername(userOperator);
					userFileName = item.getName();
					userFileName = UUID.randomUUID().toString() + "_"
							+ userFileName.substring(userFileName.lastIndexOf(File.separator) + 1);

					userFileExt = userFileName.substring(userFileName.lastIndexOf(".") + 1);
					if (!legalExt.contains(userFileExt)) {
						out.write(getError("��׺�����Ϸ���ֻ���ϴ���׺��Ϊ��" + legalExt + "�ļ�"));
						return;
					}
					savePath = this.getServletContext()
							.getRealPath(File.separator + "WEB-INF" + File.separator + "upload" + File.separator
									+ "homework" + File.separator + pointedName + File.separator + userFileName);
					tempPath = this.getServletContext().getRealPath(File.separator + "WEB-INF" + File.separator
							+ "upload" + File.separator + "homeworkTemp" + File.separator + pointedName);
					File tempFileDir = new File(tempPath);
					if (!tempFileDir.exists() && !tempFileDir.isDirectory()) {
						tempFileDir.mkdir();
					}

					// ���������ݿ�
					UserAnsInf userAnsInf = new UserAnsInf();
					userAnsInf.setUserFileId(userFileId);
					userAnsInf.setUserFilePath(savePath);
					userAnsInf.setUserFileName(userFileName);
					userAnsInf.setUserOperator(userOperator);
					userAnsInf.setUserSchool(userSchool);
					/*
					 * 
					 * ������ ���ȸ���userFileId��userOperator�жϸ��û��Ƿ����ύ�˸ô���ҵ�Ĵ𰸣�
					 * ����о���ɾ���ôδ𰸣����ύ�����û�У���ֱ���ύ����Ϊ��ÿ���û�����ÿ����ҵֻ�� �ύһ�Ρ�
					 * 
					 */
					boolean flag = false;
					if (userFileService.isExist(userAnsInf)) {
						if (userFileService.deleteAnsFromDiskByFileId(userFileId)) {
							flag = userFileService.updateAnswer(userAnsInf);
						} else
							caution = "�����û���ʧ�ܣ�����ϵ����Ա";
					} else {
						flag = userFileService.uploadAnswer(userAnsInf);
					}
					if (flag) {
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(savePath)));
						InputStream in = item.getInputStream();
						byte[] b = new byte[8192];
						int len = -1;

						while ((len = in.read(b)) != -1) {
							bos.write(b, 0, len);
						}
						bos.close();
						in.close();
						item.delete();
						JSONObject obj2 = new JSONObject();
						obj2.put("error", 0);
						obj2.put("message", "�ϴ��ɹ���");
						out.write(obj2.toString());
						return;
					}
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			System.out.println("1_____________-" + response.isCommitted());
			e.printStackTrace();
			caution = "�ļ���С��������";
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			caution = "�ļ��ϴ�ʧ�ܣ���ϵ����Ա";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// �Ҳ������ڵĸ�ID��Ӧ���ļ���
			caution = "�ô���ҵ�Ѿ���ɾ��������ϵ����Ա";
			e.printStackTrace();
		}
		System.out.println("here" + getError(caution));
		out.write(getError(caution));
		out.close();
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}
}
