package com.jxyj.train.admindao;

import java.sql.SQLException;
import java.util.List;

import com.jxyj.train.entity.UserInf;

public interface AdminUserTackle {
	/**
	 * ��ѯ����ע���û�
	 * @return ����ע���û���list����
	 * @throws SQLException 
	 */
	List<UserInf> findAllUser() throws SQLException;
	/**
	 * ���ݾ�����������ѯָ���û�,��sql���Ϊ�գ��򷵻ؿռ���
	 * @return �����������û�list����
	 * @throws SQLException 
	 */
	List<UserInf> findPointedUser(String sql,Object... objs) throws SQLException;
	/**
	 * ע���û�
	 * @param userInf Ҫע����û�������Ϣ
	 * @throws SQLException 
	 */
	boolean registerUser(UserInf userInf) throws SQLException;
	
	/**
	 * ����Ƿ���ڸ��û�
	 * @param userInf
	 * @return
	 * @throws SQLException 
	 */
	boolean hasRegistered(UserInf userInf) throws SQLException;
	

	/**
	 * ɾ��ѡ���û�����ɾ���û�֮ǰ��Ӧ��ȷ�����û�֮ǰ�ϴ���������ҵ���Ѿ���ɾ��
	 * ��������
	 * @param userInf
	 * @return
	 * @throws SQLException 
	 */
	boolean deleteUser(UserInf userInf) throws SQLException;
	
	/**
	 * ����ɾ���û�
	 * @throws SQLException 
	 */
	boolean deleteBundleUser(List<UserInf> userInfs) throws SQLException;
	
	/**
	 * �����û�
	 * @throws SQLException 
	 */
	boolean updateUser(UserInf userInf) throws SQLException;
	
	/**
	 * �Ƿ�ͨ����֤
	 * @param userInf
	 * @return
	 * @throws SQLException
	 */
	boolean isRegistered(UserInf userInf,String identity) throws SQLException;
	
}
