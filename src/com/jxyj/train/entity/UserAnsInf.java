package com.jxyj.train.entity;

import java.io.Serializable;

public class UserAnsInf implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6265677324252034755L;
	private String userAnsId;
	private String userFileId;
	private String userFileName;
	private String userFilePath;
	private String userSchool;
	private String userOperator;
	private String userUploadTime;
	
	public UserAnsInf(String userAnsId, String userFileId, String userFileName, String userFilePath, String userSchool,
			String userOperator, String userUploadTime) {
		super();
		this.userAnsId = userAnsId;
		this.userFileId = userFileId;
		this.userFileName = userFileName;
		this.userFilePath = userFilePath;
		this.userSchool = userSchool;
		this.userOperator = userOperator;
		this.userUploadTime = userUploadTime;
	}

	public UserAnsInf() {
	}

	public String getUserAnsId() {
		return userAnsId;
	}

	public void setUserAnsId(String userAnsId) {
		this.userAnsId = userAnsId;
	}

	public String getUserFileId() {
		return userFileId;
	}

	public void setUserFileId(String userFileId) {
		this.userFileId = userFileId;
	}

	public String getUserFileName() {
		return userFileName;
	}

	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
	}

	public String getUserFilePath() {
		return userFilePath;
	}

	public void setUserFilePath(String userFilePath) {
		this.userFilePath = userFilePath;
	}

	public String getUserSchool() {
		return userSchool;
	}

	public void setUserSchool(String userSchool) {
		this.userSchool = userSchool;
	}

	public String getUserOperator() {
		return userOperator;
	}

	public void setUserOperator(String userOperator) {
		this.userOperator = userOperator;
	}

	public String getUserUploadTime() {
		return userUploadTime;
	}

	public void setUserUploadTime(String userUploadTime) {
		this.userUploadTime = userUploadTime;
	}

	@Override
	public String toString() {
		return "UserAnsInf [userAnsId=" + userAnsId + ", userFileId=" + userFileId + ", userFileName=" + userFileName
				+ ", userFilePath=" + userFilePath + ", userSchool=" + userSchool + ", userOperator=" + userOperator
				+ ", userUploadTime=" + userUploadTime + "]";
	}
	
	
}
