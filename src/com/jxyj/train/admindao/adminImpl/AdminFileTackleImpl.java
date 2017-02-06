package com.jxyj.train.admindao.adminImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.jxyj.train.admindao.AdminFileTackle;
import com.jxyj.train.entity.AnswerInf;
import com.jxyj.train.entity.FileInf;
import com.jxyj.train.entity.UserAnsInf;
import com.jxyj.train.utils.JDBCUtils;

public class AdminFileTackleImpl implements AdminFileTackle {
	static QueryRunner qr = JDBCUtils.getQueryRunner();

	@Override
	public List<FileInf> findAllFile() throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from uphomework";
		List<FileInf> fileList = qr.query(sql, new BeanListHandler<FileInf>(FileInf.class));
		return fileList;
	}

	@Override
	public List<FileInf> findPointedFile(String sql, Object... objs) throws SQLException {
		// TODO Auto-generated method stub
		List<FileInf> pointedFileList = null;
		if (sql != null) {
			pointedFileList = qr.query(sql, new BeanListHandler<>(FileInf.class), objs);
		}
		return pointedFileList;
	}

	@Override
	public boolean uploadFile(FileInf fileInf) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into uphomework(fileName,fileId,filePath,operator) values(?,?,?,?)";
		int line = 0;
		if (fileInf != null) {
			String fileName = fileInf.getFileName();
			String fileId = fileInf.getFileId();
			String filePath = fileInf.getFilePath();
			String operator = fileInf.getOperator();
			line = qr.update(sql, fileName, fileId, filePath, operator);
		}
		if (line > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean uploadAllFile(List<FileInf> fileInfs) throws SQLException {
		// TODO Auto-generated method stub
		//使用批处理，必须新建一个QueryRunner对象，若使用qr，则会自动关闭
		QueryRunner tranQr = new QueryRunner();
		if(fileInfs==null){
			return false;
		}
		Object[][] strs=new Object[fileInfs.size()][5];
		//批量上传作业，使用批处理方式，预防重复多次上传文件
		for(int i=0;i<fileInfs.size();i++){				
			if (fileInfs.get(i) != null) {
				String fileName = fileInfs.get(i).getFileName();
				String fileId = fileInfs.get(i).getFileId();
				String filePath = fileInfs.get(i).getFilePath();
				String operator = fileInfs.get(i).getOperator();
				Timestamp uploadTime = fileInfs.get(i).getUploadTime();
				Object[] temp={fileName,fileId,filePath,operator,uploadTime};
				strs[i]=temp;
			}			
		}
		String sql = "insert into uphomework(fileName,fileId,filePath,operator) values(?,?,?,?)";
		try {
			JDBCUtils.startTransaction();
			Connection conn=JDBCUtils.getContainer().get();			
			tranQr.batch(conn, sql,strs );
			JDBCUtils.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JDBCUtils.rollback();
			throw e;
		}
		return true;
	}

	@Override
	public boolean deleteFile(FileInf fileInf) throws SQLException{
		// TODO Auto-generated method stub
		String answerId = fileInf.getFileId();
		QueryRunner tranQr = new QueryRunner();
		String delStdAnsSql = "delete from answerupload where answerId=?";
		String delUserAnsSql = "delete from useruphomework where userFileId=?";
		String delFile = "delete from uphomework where fileId=?";
		try {
			JDBCUtils.startTransaction(); // 开启事务
			Connection conn = JDBCUtils.getContainer().get();
			tranQr.update(conn, delStdAnsSql, answerId);
			tranQr.update(conn, delUserAnsSql, answerId);
			tranQr.update(conn, delFile, answerId);
			JDBCUtils.commit(); // 提交事务
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JDBCUtils.rollback();
			//不在本地处理异常，捕获到异常进行数据回滚后，在抛出该异常，交由调用者处理
			throw e;
		}
		return true;
	}

	@Override
	public Map<String, UserAnsInf> downloadAllUserAnswer(FileInf fileInf) throws SQLException {
		// TODO Auto-generated method stub
		// 即将要下载的作业ID
		Map<String, UserAnsInf> ansMap = null;
		if (fileInf != null) {
			String userFileId = fileInf.getFileId();
			String sql = "select * from useruphomework where userFileId=?";
			ansMap = qr.query(sql, new BeanMapHandler<String, UserAnsInf>(UserAnsInf.class, "userAnsId"), userFileId);
		}
		return ansMap;
	}

	@Override
	public boolean uploadAnswer(AnswerInf answerInf) throws SQLException {
		// TODO Auto-generated method stub
		int line = 0;
		if (answerInf != null) {
			String answerId = answerInf.getAnswerId();
			String answerName = answerInf.getAnswerName();
			String answerPath = answerInf.getAnswerPath();
			String operator = answerInf.getOperator();
			String sql = "insert into answerupload(answerId,answerName,answerPath," + "operator) values(?,?,?,?)";
			line = qr.update(sql, answerId, answerName, answerPath, operator);
		}
		if (line >= 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteAnswer(AnswerInf answerInf) throws SQLException {
		// TODO Auto-generated method stub
		int line = 0;
		if (answerInf != null) {
			String answerId = answerInf.getAnswerId();
			String sql = "delete from answerupload where answerId=?";
			line = qr.update(sql, answerId);
		}
		if (line >= 0)
			return true;
		else
			return false;

	}

	@Override
	public boolean deleteUserAnswer(String sql, Object[] objs) throws SQLException {
		// TODO Auto-generated method stub
		if (sql != null) {
			if (qr.update(sql, objs) >= 0)
				return true;
		}
		return false;
	}

	@Override
	public FileInf downloadFile(FileInf fileInf) throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from uphomework where fileId=?";
		FileInf resFileInf=null;
		if(fileInf !=null){
			String fileId=fileInf.getFileId();
			resFileInf= qr.query(sql, new BeanHandler<FileInf>(FileInf.class), fileId);
		}	
		return resFileInf;
	}

	@Override
	public AnswerInf downloadStdAns(String fileId) throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from answerupload where answerId=?";
		AnswerInf userAnsInf =null;
		if(fileId !=null && StringUtils.isNotEmpty(fileId)){
			 userAnsInf = qr.query(sql, new BeanHandler<AnswerInf>(AnswerInf.class), fileId);
		}
		return userAnsInf;
	}

}
