package com.jxyj.train.admindao;

import java.sql.SQLException;
import java.util.List;

import com.jxyj.train.entity.InformInf;

public interface AdminInformTackle {
	/**
	 * ����֪ͨ
	 * @param infromInf
	 * @return
	 * @throws SQLException 
	 */
	boolean announce(InformInf informInf) throws SQLException;
	/**
	 * ɾ��֪ͨ
	 * @param informInf
	 * @return
	 * @throws SQLException 
	 */
	boolean deleteInf(InformInf informInf) throws SQLException;
	
	/**
	 * ��ѯ����֪ͨ
	 * @param informInf
	 * @return
	 * @throws SQLException 
	 */
	List<InformInf> findAllInform() throws SQLException;
	/**
	 * ��ѯָ��֪ͨ����
	 * @param sql
	 * @param objs
	 * @return
	 * @throws SQLException 
	 */
	List<InformInf> findPointedInform(String sql,Object... objs) throws SQLException;
	
}
