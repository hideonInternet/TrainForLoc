package com.jxyj.train.admindao;

import java.sql.SQLException;
import java.util.List;

import com.jxyj.train.entity.InformInf;

public interface AdminInformTackle {
	/**
	 * 发布通知
	 * @param infromInf
	 * @return
	 * @throws SQLException 
	 */
	boolean announce(InformInf informInf) throws SQLException;
	/**
	 * 删除通知
	 * @param informInf
	 * @return
	 * @throws SQLException 
	 */
	boolean deleteInf(InformInf informInf) throws SQLException;
	
	/**
	 * 查询所有通知
	 * @param informInf
	 * @return
	 * @throws SQLException 
	 */
	List<InformInf> findAllInform() throws SQLException;
	/**
	 * 查询指定通知集合
	 * @param sql
	 * @param objs
	 * @return
	 * @throws SQLException 
	 */
	List<InformInf> findPointedInform(String sql,Object... objs) throws SQLException;
	
}
