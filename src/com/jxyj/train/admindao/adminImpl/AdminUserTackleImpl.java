package com.jxyj.train.admindao.adminImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jxyj.train.admindao.AdminUserTackle;
import com.jxyj.train.entity.UserInf;
import com.jxyj.train.utils.JDBCUtils;

public class AdminUserTackleImpl implements AdminUserTackle {
	static QueryRunner qr = JDBCUtils.getQueryRunner();

	@Override
	public List<UserInf> findAllUser() throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from user";
		List<UserInf> userList = qr.query(sql, new BeanListHandler<UserInf>(UserInf.class));
		return userList;
	}

	@Override
	public List<UserInf> findPointedUser(String sql, Object... objs) throws SQLException {
		// TODO Auto-generated method stub
		List<UserInf> pointedUserList = null;
		if (sql != null) {
			pointedUserList = qr.query(sql, new BeanListHandler<UserInf>(UserInf.class), objs);
		}
		return pointedUserList;
	}

	@Override
	public boolean registerUser(UserInf userInf) throws SQLException {
		// TODO Auto-generated method stub
		Object[] objs = userInf.getValues(userInf);
		int line = 0;
		String sql = "insert into user(userName,password,email,telephone,schName,operator)" + " values(?,?,?,?,?,?)";
		line = qr.update(sql, objs);
		if (line <= 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean hasRegistered(UserInf userInf) throws SQLException {
		// TODO Auto-generated method stub
		String username = userInf.getUsername();
		String sql = "select username,password from user where username=?";
		UserInf userInfDb = qr.query(sql, new BeanHandler<UserInf>(UserInf.class), username);
		return userInfDb == null ? false : true;
	}

	@Override
	public boolean deleteUser(UserInf userInf) throws SQLException {
		// TODO Auto-generated method stub
		if(userInf ==null)
			return false;
		String username = userInf.getUsername();
		int userId = userInf.getUserId();
		String delUserAnsSql = "delete from useruphomework where userOperator=?";
		String delUserSql = "delete from user where userId=?";
		QueryRunner tranQr = new QueryRunner();
		JDBCUtils.startTransaction();
		try {
			Connection conn = JDBCUtils.getContainer().get();
			tranQr.update(conn, delUserAnsSql, username);
			tranQr.update(conn, delUserSql, userId);
			JDBCUtils.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JDBCUtils.rollback();
			throw e;
		}
		return true;
	}

	@Override
	public boolean deleteBundleUser(List<UserInf> userInfs) throws SQLException {
		// TODO Auto-generated method stub		
		if (userInfs == null) {
			return false;
		}
		// 使用批处理，必须新建一个QueryRunner对象，若使用qr，则会自动关闭		
		QueryRunner tranQr = new QueryRunner();
		String delUserAnsSql = "delete from useruphomework where userOperator=?";
		String delUserSql = "delete from user where userId=?";
		Object[][] userPara = new Object[userInfs.size()][1];
		Object[][] ansPara=new Object[userInfs.size()][1];
		for(int i=0;i<userInfs.size();i++){
			Object[] userTemp=new Object[]{userInfs.get(i).getUserId()};
			Object[] ansTemp=new Object[]{userInfs.get(i).getUsername()};
			userPara[i]=userTemp;
			ansPara[i]=ansTemp;
		}
		// 批量上传作业，使用批处理方式，预防重复多次上传文件
		try {
			JDBCUtils.startTransaction();
			Connection conn=JDBCUtils.getContainer().get();
			tranQr.batch(conn, delUserAnsSql,ansPara);
			tranQr.batch(conn, delUserSql, userPara);
			JDBCUtils.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JDBCUtils.rollback();
			throw e;
		}
		return true;
	}

	@Override
	public boolean updateUser(UserInf userInf) throws SQLException {
		// TODO Auto-generated method stub
		if(userInf ==null){
			return false;
		}
		int userId=userInf.getUserId();
		String sql="update user set username=?,password=?,email=?,"
				+ "telephone=?,schName=?,operator=? where userId=?";
		Object[] objs=UserInf.getValues(userInf);
		Object[] objs2=new Object[7];
		System.arraycopy(objs, 0, objs2, 0, 6);
		objs2[6]=userInf.getUserId();
		qr.update(sql, objs2);
		return true;
	}

	@Override
	public boolean isRegistered(UserInf userInf,String identity) throws SQLException {
		// TODO Auto-generated method stub
		if(userInf==null)
			return false;
		if(identity==null || !"管理员".equals(identity) && !"普通用户".equals(identity))
			return false;
		String username=userInf.getUsername();
		String password=userInf.getPassword();
		String sql=null;
		if("普通用户".equals(identity)){
			 sql="select * from user where username=? and password=?";
			
		}else if("管理员".equals(identity)){
			 sql="select * from supervisor where supername=? and superpassword=?";
		}else
			return false;
		UserInf res=qr.query(sql, new BeanHandler<UserInf>(UserInf.class), username,password);
		return res==null?false:true;
	}

}
