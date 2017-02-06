package com.jxyj.train.adminservice;

import java.sql.SQLException;
import java.util.List;

import com.jxyj.train.admindao.AdminUserTackle;
import com.jxyj.train.admindao.adminImpl.AdminUserTackleImpl;
import com.jxyj.train.entity.UserInf;

public class AdminUserService {
	private AdminUserTackle adminUsertackle = new AdminUserTackleImpl();

	public List<UserInf> getAllUser() throws SQLException {
		List<UserInf> allList = adminUsertackle.findAllUser();
		return allList;
	}

	public List<UserInf> findPointedUser(String sql, Object... objs) throws SQLException {
		List<UserInf> pointedList = adminUsertackle.findPointedUser(sql, objs);
		return pointedList;
	}

	public boolean registerUser(UserInf userInf) throws SQLException {
		return adminUsertackle.registerUser(userInf);
	}

	public boolean hasRegistered(UserInf userInf) throws SQLException {
		return adminUsertackle.hasRegistered(userInf);
	}

	public boolean deleteUser(UserInf userInf) throws SQLException {
		return adminUsertackle.deleteUser(userInf);
	}

	public boolean deleteBundleUser(List<UserInf> userInfs) throws SQLException {
		return adminUsertackle.deleteBundleUser(userInfs);
	}

	public boolean updateUser(UserInf userInf) throws SQLException {
		return adminUsertackle.updateUser(userInf);
	}

	public boolean isRegistered(UserInf userInf, String identity) throws SQLException {
		return adminUsertackle.isRegistered(userInf, identity);
	}
	public String getUserNameById(int id) throws SQLException{
		String sql="select username from user where userId=?";
		String username=null;
		if(id>0)
			username=findPointedUser(sql, id).get(0).getUsername();
		return username;
	}
	
	public String getSchByusername(String username) throws SQLException{
		String sql="select schName from user where username=?";
		String schName=null;
		if(username !=null){
			schName=findPointedUser(sql, username).get(0).getSchName();
		}
		return schName;
	}
	public String getUserIdByUserName(String username){
		AdminUserTackle adminUserTackle =new AdminUserTackleImpl();
		String sql="select userId from user where username=?";
		List<UserInf> list=null;
		try {
			 list = adminUserTackle.findPointedUser(sql, username);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list.get(0).getUserId()+"";
	}
}
