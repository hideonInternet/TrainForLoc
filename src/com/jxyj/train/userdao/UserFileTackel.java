package com.jxyj.train.userdao;

import java.sql.SQLException;

import com.jxyj.train.entity.UserAnsInf;

public interface UserFileTackel {
	/**
	 * 上传用户答案
	 * @param userAnsInf
	 * @return
	 * @throws SQLException
	 */
	boolean uploadAnswer(UserAnsInf userAnsInf)throws SQLException;
	
	/**
	 * 用户是否上传过该次作业的答案
	 * @param userFileId
	 * @param userOperator
	 * @return
	 * @throws SQLException
	 */
	boolean isExist(UserAnsInf userAnsInf) throws SQLException;
	
	/**
	 * 更新用户上传答案
	 * @param userAnsInf
	 * @return
	 * @throws SQLException
	 */
	boolean updateAnswer(UserAnsInf userAnsInf)throws SQLException;
	
	/**
	 * 删除用户答案
	 * @param userAnsInf
	 * @return
	 * @throws SQLException
	 */
	boolean deleteAnswer(UserAnsInf userAnsInf)throws SQLException;
	
	/**
	 * 根据不同的条件查询用户上传文件
	 * @param sql
	 * @param objects
	 * @return
	 * @throws SQLException
	 */
	UserAnsInf findPointedAns(String sql,Object...objects )throws SQLException;
}
