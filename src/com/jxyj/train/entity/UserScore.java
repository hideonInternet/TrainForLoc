package com.jxyj.train.entity;

import java.io.Serializable;
import java.sql.SQLException;

import com.jxyj.train.adminservice.AdminFileService;

public class UserScore implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4623540540675041386L;
	private int fileId;
	private int userId;
	private int score;//µÃ·Ö
	private String stdContent;
	private int grade;//Âú·Ö
	private String operator;
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getStdContent() {
		return stdContent;
	}
	public void setStdContent(String stdContent) {
		this.stdContent = stdContent;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public UserScore() {
		super();
	}

	public UserScore(int fileId, int userId, int score, String stdContent, int grade, String operator) {
		super();
		this.fileId = fileId;
		this.userId = userId;
		this.score = score;
		this.stdContent = stdContent;
		this.grade = grade;
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "UserScore [fileId=" + fileId + ", userId=" + userId + ", score=" + score + ", stdContent=" + stdContent
				+ ", grade=" + grade + ", operator=" + operator + "]";
	}
	
	public String getFileName(){
		AdminFileService adminFileService=new AdminFileService();
		String fileName=null;
		try {
			fileName=adminFileService.getFileNameById(String.valueOf(fileId));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}
}
