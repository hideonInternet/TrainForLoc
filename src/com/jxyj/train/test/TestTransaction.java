package com.jxyj.train.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import com.jxyj.train.entity.FileInf;
import com.jxyj.train.entity.UserInf;
import com.jxyj.train.utils.JDBCUtils;

public class TestTransaction {

	@Test
	public void deleteFile() {
		// TODO Auto-generated method stub
		String answerId = "2";
		QueryRunner tranQr = new QueryRunner();		
		String delStdAnsSql = "delete from answerupload where answerId=?";
		String delUserAnsSql = "delete from useruphomework where userFileId=?";
		String delFile = "delete from uphomework where fileId=?";
		try {
			JDBCUtils.startTransaction(); // 开启事务
			Connection conn = JDBCUtils.getContainer().get();
			if (conn == null)
				System.out.println("null");
			tranQr.update(conn, delStdAnsSql, answerId);
			tranQr.update(conn, delUserAnsSql, answerId);
			tranQr.update(conn, delFile, answerId);
			JDBCUtils.commit(); // 提交事务
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JDBCUtils.rollback();
			e.printStackTrace();
		}
		
	}
	@Test
	public void uploadAnswer() throws SQLException {
		// TODO Auto-generated method stub
		String answerId = "1";
		String answerName = "test";
		String answerPath = "test";
		String operator = "test";
		QueryRunner qr=JDBCUtils.getQueryRunner();
		//Timestamp uploadTime = new Timestamp(System.currentTimeMillis());

		String sql = "insert into answerupload(answerId,answerName,answerPath,"
				+ "operator) values(?,?,?,?)";
		if (qr.update(sql, answerId, answerName, answerPath, operator) > 0)
			System.out.println("yes");
		else
			System.out.println("no");
	}
	@Test
	public void uploadAllFile() throws SQLException {
		// TODO Auto-generated method stub
		//使用批处理，必须新建一个QueryRunner对象，若使用qr，则会自动关闭
		List<FileInf> fileInfs =new ArrayList<FileInf>();
		for(int i=0;i<10;i++){
			FileInf fileInf=new FileInf();
			fileInf.setFileId("3"+i);
			fileInf.setFileName("test"+i);
			fileInf.setFilePath("path"+i);
			fileInf.setOperator("test");
			fileInf.setUploadTime(new Timestamp(System.currentTimeMillis()));
			fileInfs.add(fileInf);
		}
		FileInf fileInf=new FileInf();
		fileInf.setFileId("3");
		fileInf.setFileName("test");
		fileInf.setFilePath("path");
		fileInf.setOperator("test234");
		fileInf.setUploadTime(new Timestamp(System.currentTimeMillis()));
		fileInfs.add(fileInf);
		QueryRunner tranQr = new QueryRunner();
		if(fileInfs==null){
			System.out.println("no");
		}
		Object[][] strs=new Object[fileInfs.size()][4];
		//批量上传作业，使用批处理方式，预防重复多次上传文件
		for(int i=0;i<fileInfs.size();i++){				
			if (fileInfs.get(i) != null) {
				String fileName = fileInfs.get(i).getFileName();
				String fileId = fileInfs.get(i).getFileId();
				String filePath = fileInfs.get(i).getFilePath();
				String operator = fileInfs.get(i).getOperator();
				Object[] temp={fileName,fileId,filePath,operator};
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
			// TODO Auto-generated catch block\
			//e.printStackTrace();
			JDBCUtils.rollback();
			SQLException e2=new SQLException("批添加失败，请再次重试或联系管理员");
			e2.initCause(e);
			throw e2;
		}
		System.out.println("yes");
	}
	
	@Test
	public void isRegistered() throws SQLException {
		// TODO Auto-generated method stub
		String username="jxyj";
		QueryRunner qr=JDBCUtils.getQueryRunner();
		String sql="select username,password from user where username=?";
		/*
		 * 如果不存在结果集，就会返回null
		 */
		UserInf userInfDb=qr.query(sql, new BeanHandler<UserInf>(UserInf.class), username);
		System.out.println(userInfDb.getUsername());
		System.out.println(userInfDb);
	}
	@Test
	public void registerUser() throws SQLException {
		// TODO Auto-generated method stub
		UserInf userInf=new UserInf();
		userInf.setUsername("jxyj");
		userInf.setPassword("123");;
		userInf.setEmail("jxyj@163.com");
		userInf.setOperator("test");
		userInf.setSchName("测试");
		userInf.setTelephone("123");
		Object[] objs=userInf.getValues(userInf);
		QueryRunner qr=JDBCUtils.getQueryRunner();
		int line=0;
		String sql=
				"insert into user(userName,password,email,telephone,schName,operator)"
				+ " values(?,?,?,?,?,?)";
		line=qr.update(sql,objs);
		if(line<=0){
			System.out.println(false);
		}
		System.out.println(true);
	}
	public void testThrowExcption() throws Exception{
		try {
			int i=10/0;
			System.out.println("一号");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("二号");
			throw new Exception();
		}
		System.out.println("三号");
	}
	@Test
	public void testUpMethod(){
		try {
			System.out.println("begin");
			this.testThrowExcption();
			System.out.println("num 4");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void updateUser() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr=JDBCUtils.getQueryRunner();
		UserInf userInf=new UserInf();
		userInf.setUserId(2);
		userInf.setUsername("jxyj");
		userInf.setPassword("123");;
		userInf.setEmail("jxyj@163.com");
		userInf.setOperator("test");
		userInf.setSchName("更新测试");
		userInf.setTelephone("123343");
		int userId=userInf.getUserId();
		String sql="update user set username=?,password=?,email=?,"
				+ "telephone=?,schName=?,operator=? where userId=?";
		Object[] objs=UserInf.getValues(userInf);
		Object[] objs2=new Object[7];
		System.arraycopy(objs, 0, objs2, 0, 6);
		objs2[6]=userInf.getUserId();
		qr.update(sql, objs2);
	}
	
}
