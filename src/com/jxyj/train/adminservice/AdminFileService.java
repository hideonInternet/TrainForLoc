package com.jxyj.train.adminservice;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jxyj.train.admindao.AdminFileTackle;
import com.jxyj.train.admindao.adminImpl.AdminFileTackleImpl;
import com.jxyj.train.entity.AnswerInf;
import com.jxyj.train.entity.FileInf;
import com.jxyj.train.entity.UserAnsInf;

public class AdminFileService {
	
	private   AdminFileTackle adminFileTackle=new AdminFileTackleImpl();
	private List<FileInf> allFile;
	private List<FileInf> pointedFile;
	public void setAllFile(List<FileInf> allFile) {
		this.allFile = allFile;
	}
	/**
	 * ���������ϴ���ҵ
	 * @return
	 * @throws SQLException
	 */
	public   List<FileInf> getAllFile() throws SQLException{
		List<FileInf> list=adminFileTackle.findAllFile();
		if(list == null)
			throw new IllegalStateException("�ļ�Ϊ��");
		return list;
	}
	public List<FileInf> getPointedFile() {
		return pointedFile;
	}
	public void setPointedFile(List<FileInf> pointedFile) {
		this.pointedFile = pointedFile;
	}
	/**
	 * �ҵ�����ָ���������ϴ��ļ�
	 * @param sql
	 * @param objs
	 * @return
	 * @throws SQLException
	 */
	public   List<FileInf> getPointedFile(String sql,Object... objs) throws SQLException{
		List<FileInf> list=adminFileTackle.findPointedFile(sql, objs);
		if(list == null)
			throw new IllegalStateException("�ļ�Ϊ��");
		return list;
	}
	/**
	 * �ϴ���ҵ
	 * @param fileInf
	 * @return
	 * @throws SQLException
	 */
	public   boolean uploadFile(FileInf fileInf) throws SQLException{
		return adminFileTackle.uploadFile(fileInf);
	}
	
	/**
	 * �ϴ�������ҵ
	 * @param fileInfs
	 * @return
	 * @throws SQLException
	 */
	public   boolean uploadAllFile(List<FileInf> fileInfs) throws SQLException{
		return adminFileTackle.uploadAllFile(fileInfs);
	}
	/**
	 * ɾ����ҵ
	 * @param fileInf
	 * @return
	 * @throws SQLException
	 */
	public   boolean deleteFile(FileInf fileInf) throws SQLException{
		return adminFileTackle.deleteFile(fileInf);
	}
	/**
	 * ���������û��ϴ���ҵ
	 * @param fileInf
	 * @return
	 * @throws SQLException
	 */
	public   Map<String, UserAnsInf> downloadAllUserAnswer(FileInf fileInf) throws SQLException{
		Map<String,UserAnsInf> userAnsInf=adminFileTackle.downloadAllUserAnswer(fileInf);
		if(userAnsInf == null)
			throw new IllegalStateException("�ļ�Ϊ��");
		return userAnsInf;
	}
	/**
	 * �ϴ���ҵ��׼��
	 * @param answerInf
	 * @return
	 * @throws SQLException
	 */
	public   boolean uploadAnswer(AnswerInf answerInf) throws SQLException{
		return adminFileTackle.uploadAnswer(answerInf);
	}
	/**
	 * ɾ����ҵ��׼��
	 * @param answerInf
	 * @return
	 * @throws SQLException
	 */
	public   boolean deleteAnswer(AnswerInf answerInf) throws SQLException{
		return adminFileTackle.deleteAnswer(answerInf);
	}
	/**
	 * ɾ���û��ϴ��Ĵ�
	 * @param sql
	 * @param objs
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteUserAnswer(String sql,Object[] objs) throws SQLException{
		return adminFileTackle.deleteUserAnswer(sql, objs);
	}
	/**
	 * ������ҵ
	 * @param fileInf
	 * @return
	 * @throws SQLException
	 */
	public FileInf downloadFile(FileInf fileInf) throws SQLException{		
		FileInf downloadFile = adminFileTackle.downloadFile(fileInf);
		return downloadFile;
	}
	
	/**
	 * ���ر�׼��
	 * @param fileId
	 * @return
	 * @throws SQLException
	 */
	public AnswerInf downloadStdAns(String fileId) throws SQLException {
		AnswerInf answerInf = adminFileTackle.downloadStdAns(fileId);
		return answerInf;
		
	}
	/**
	 * ����fileId�ҵ����Ӧ��fileName
	 * @param fileId
	 * @return �ҵ���fileName����null����û�ж�Ӧ���ļ����ҵ����߲���fileIdΪnull
	 * @throws SQLException
	 */
	public String getFileNameById(String fileId) throws SQLException{
		String fileName=null;
		FileInf file = getFileById(fileId);
		if(file!=null){
			fileName=file.getFileName();
		}
		return fileName;
	}
	public FileInf getFileById(String fileId) throws SQLException{
		List<FileInf> fileList=null;
		FileInf file=null;
		if(fileId==null){
			return null;
		}
		String sql="select * from uphomework where fileId=?";
		fileList=adminFileTackle.findPointedFile(sql, fileId);
		if(fileList!=null && fileList.size()>0)
			file=fileList.get(0);
		
		return file;
	}
	
}
