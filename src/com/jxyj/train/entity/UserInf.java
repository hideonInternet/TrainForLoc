package com.jxyj.train.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserInf implements Serializable, Comparable<UserInf>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4483332479158643334L;
	private int  userId;
	private String username;
	private String password;
	private String email;
	private String telephone;
	private String schName;
	private String operator;
	private Timestamp registerTime;
	public UserInf(int userId, String username, String password, String email, String telephone, String schName,
			String operator, Timestamp registerTime) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.telephone = telephone;
		this.schName = schName;
		this.operator = operator;
		this.registerTime = registerTime;
	}
	public UserInf() {
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSchName() {
		return schName;
	}
	public void setSchName(String schName) {
		this.schName = schName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Timestamp getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	@Override
	public String toString() {
		return "UserInf [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", telephone=" + telephone + ", schName=" + schName + ", operator=" + operator + ", registerTime="
				+ registerTime + "]";
	}
	
	public static Object[] getValues(UserInf userInf){
		if(userInf == null){
			return null;
		}		
		String email = userInf.getEmail();
		String operator = userInf.getOperator();
		String password = userInf.getPassword();
		String schName = userInf.getSchName();
		String telephone = userInf.getTelephone();
		String username = userInf.getUsername();
		
		Object[] obj=new Object[]{username,password,email,telephone,
				schName,operator};
		return obj;
	}
	@Override
	public int compareTo(UserInf o) {
		// TODO Auto-generated method stub
		return this.registerTime.compareTo(o.getRegisterTime());
	}
	
	
	
	
	
	
	
}
