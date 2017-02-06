package com.jxyj.train.userdao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jxyj.train.entity.UserAnsInf;
import com.jxyj.train.userdao.UserFileTackel;
import com.jxyj.train.utils.JDBCUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class UseFileTackleImpl implements UserFileTackel {
	public QueryRunner qr = JDBCUtils.getQueryRunner();

	@Override
	public boolean uploadAnswer(UserAnsInf userAnsInf) throws SQLException {
		// TODO Auto-generated method stub
		String userFileId = userAnsInf.getUserFileId();
		String userFileName = userAnsInf.getUserFileName();
		String userFilePath = userAnsInf.getUserFilePath();
		String userOperator = userAnsInf.getUserOperator();
		String userSchool = userAnsInf.getUserSchool();
		int row = 0;
		boolean flag = false;
		String sql = "insert into useruphomework(userFileId,userFileName,userFilePath,userOperator,userSchool)"
				+ "values(?,?,?,?,?)";
		row = qr.update(sql, userFileId, userFileName, userFilePath, userOperator, userSchool);
		if (row > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean isExist(UserAnsInf userAnsInf) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		Long count = 0L;
		if (userAnsInf != null) {
			String userFileId = userAnsInf.getUserFileId();
			String userOperator = userAnsInf.getUserOperator();
			String sql = "select count(*) from useruphomework where userFileId=? and userOperator=?";
			count = qr.query(sql, new ScalarHandler<Long>(), userFileId, userOperator);
		}
		if (count > 0L)
			flag = true;
		return flag;
	}

	@Override
	public boolean updateAnswer(UserAnsInf userAnsInf) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update useruphomework SET userFileName=?,userFilePath=?,"
				+ "userOperator=?,userSchool=? WHERE userFileId=?";
		boolean flag = false;
		int row = 0;
		if (userAnsInf != null) {
			String userFileId = userAnsInf.getUserFileId();
			String userFileName = userAnsInf.getUserFileName();
			String userFilePath = userAnsInf.getUserFilePath();
			String userOperator = userAnsInf.getUserOperator();
			String userSchool = userAnsInf.getUserSchool();
			row = qr.update(sql, userFileName, userFilePath, userOperator, userSchool,userFileId);
		}
		if (row > 0)
			flag = true;
		return flag;
	}

	@Override
	public boolean deleteAnswer(UserAnsInf userAnsInf) throws SQLException {
		// TODO Auto-generated method stub
		String sql="delete from useruphomework where userFileId=?";
		boolean flag=false;
		int row=0;
		if(userAnsInf !=null){
			String userFileId = userAnsInf.getUserFileId();
			row=qr.update(sql,userFileId);
		}
		if(row>0)
			flag=true;
		return flag;
	}

	@Override
	public UserAnsInf findPointedAns(String sql, Object... objects) throws SQLException {
		// TODO Auto-generated method stub
		UserAnsInf userAnsInf = qr.query(sql, new BeanHandler<>(UserAnsInf.class), objects);
		return userAnsInf;
	}
	
	

}
