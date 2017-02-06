package com.jxyj.train.userdao;

import java.sql.SQLException;

import com.jxyj.train.entity.UserAnsInf;

public interface UserFileTackel {
	/**
	 * �ϴ��û���
	 * @param userAnsInf
	 * @return
	 * @throws SQLException
	 */
	boolean uploadAnswer(UserAnsInf userAnsInf)throws SQLException;
	
	/**
	 * �û��Ƿ��ϴ����ô���ҵ�Ĵ�
	 * @param userFileId
	 * @param userOperator
	 * @return
	 * @throws SQLException
	 */
	boolean isExist(UserAnsInf userAnsInf) throws SQLException;
	
	/**
	 * �����û��ϴ���
	 * @param userAnsInf
	 * @return
	 * @throws SQLException
	 */
	boolean updateAnswer(UserAnsInf userAnsInf)throws SQLException;
	
	/**
	 * ɾ���û���
	 * @param userAnsInf
	 * @return
	 * @throws SQLException
	 */
	boolean deleteAnswer(UserAnsInf userAnsInf)throws SQLException;
	
	/**
	 * ���ݲ�ͬ��������ѯ�û��ϴ��ļ�
	 * @param sql
	 * @param objects
	 * @return
	 * @throws SQLException
	 */
	UserAnsInf findPointedAns(String sql,Object...objects )throws SQLException;
}
