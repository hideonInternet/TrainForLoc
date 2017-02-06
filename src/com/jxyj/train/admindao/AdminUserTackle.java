package com.jxyj.train.admindao;

import java.sql.SQLException;
import java.util.List;

import com.jxyj.train.entity.UserInf;

public interface AdminUserTackle {
	/**
	 * 查询所有注册用户
	 * @return 所有注册用户的list集合
	 * @throws SQLException 
	 */
	List<UserInf> findAllUser() throws SQLException;
	/**
	 * 根据具体条件，查询指定用户,若sql语句为空，则返回空集；
	 * @return 符合条件的用户list集合
	 * @throws SQLException 
	 */
	List<UserInf> findPointedUser(String sql,Object... objs) throws SQLException;
	/**
	 * 注册用户
	 * @param userInf 要注册的用户具体信息
	 * @throws SQLException 
	 */
	boolean registerUser(UserInf userInf) throws SQLException;
	
	/**
	 * 检查是否存在该用户
	 * @param userInf
	 * @return
	 * @throws SQLException 
	 */
	boolean hasRegistered(UserInf userInf) throws SQLException;
	

	/**
	 * 删除选定用户，在删除用户之前，应该确保该用户之前上传的所有作业答案已经被删除
	 * 否则会出错
	 * @param userInf
	 * @return
	 * @throws SQLException 
	 */
	boolean deleteUser(UserInf userInf) throws SQLException;
	
	/**
	 * 批量删除用户
	 * @throws SQLException 
	 */
	boolean deleteBundleUser(List<UserInf> userInfs) throws SQLException;
	
	/**
	 * 更新用户
	 * @throws SQLException 
	 */
	boolean updateUser(UserInf userInf) throws SQLException;
	
	/**
	 * 是否通过验证
	 * @param userInf
	 * @return
	 * @throws SQLException
	 */
	boolean isRegistered(UserInf userInf,String identity) throws SQLException;
	
}
