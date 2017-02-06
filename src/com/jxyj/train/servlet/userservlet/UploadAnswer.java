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
		// 3.设置上传的单个文件大小
		upload.setFileSizeMax(10 * 1024 * 1024);
		PrintWriter out = response.getWriter();
		// 设置编码格式
		upload.setHeaderEncoding("UTF-8");

		AdminFileService adminFileService = new AdminFileService();
		UserFileService userFileService = new UserFileService();

		String userFileId = null; // 于该作业答案对于的管理员上传作业ID
		String pointedName = null;// 管理员上传作业ID对应得文件名
		String userFileName = null;// 用户上传的文件名
		String userFilePath = null;// 作业保存位置
		String userOperator = null;// 该次操作的操作人
		String userSchool = null;
		String savePath = null;// 文件的保存路径，第一次赋值为了确保该文件路径存在，第二次赋值为了保存该文件
		String tempPath = null;// 临时文件保存路径，若文件上传大小超过了，设置的Threshold，则暂时保持至该文件路径
		String legalExt = "zip,rar";
		String userFileExt = null;// 上传文件后缀
		String caution = null;
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField() && "fileId".equals(item.getFieldName())) {
					userFileId = item.getString();
				} // 对上传文件进行操作处理
				if (!item.isFormField()) {
					if(item.getSize()==0L){
						out.write(getError("请至少选择一个文件"));
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
						out.write(getError("后缀名不合法，只能上传后缀名为：" + legalExt + "文件"));
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

					// 保存至数据库
					UserAnsInf userAnsInf = new UserAnsInf();
					userAnsInf.setUserFileId(userFileId);
					userAnsInf.setUserFilePath(savePath);
					userAnsInf.setUserFileName(userFileName);
					userAnsInf.setUserOperator(userOperator);
					userAnsInf.setUserSchool(userSchool);
					/*
					 * 
					 * 操作： 首先根据userFileId和userOperator判断该用户是否替提交了该次作业的答案，
					 * 如果有就先删除该次答案，再提交，如果没有，就直接提交，因为，每个用户对于每次作业只能 提交一次。
					 * 
					 */
					boolean flag = false;
					if (userFileService.isExist(userAnsInf)) {
						if (userFileService.deleteAnsFromDiskByFileId(userFileId)) {
							flag = userFileService.updateAnswer(userAnsInf);
						} else
							caution = "更新用户答案失败，请联系管理员";
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
						obj2.put("message", "上传成功！");
						out.write(obj2.toString());
						return;
					}
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			System.out.println("1_____________-" + response.isCommitted());
			e.printStackTrace();
			caution = "文件大小超过限制";
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			caution = "文件上传失败，联系管理员";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// 找不到对于的该ID对应的文件名
			caution = "该次作业已经被删除，请联系管理员";
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
