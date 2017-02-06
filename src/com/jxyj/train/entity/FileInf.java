package com.jxyj.train.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class FileInf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 455766781494779878L;
	private String fileId;
	private String fileName;
	private String filePath;
	private String operator;
	private Timestamp uploadTime;
	public FileInf(String fileId, String fileName, String filePath, String operator, Timestamp uploadTime) {
		super();
		this.fileId = fileId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.operator = operator;
		this.uploadTime = uploadTime;
	}
	public FileInf() {
		
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Timestamp getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
	@Override
	public String toString() {
		return "FileInf [fileId=" + fileId + ", fileName=" + fileName + ", filePath=" + filePath + ", operator="
				+ operator + ", uploadTime=" + uploadTime + "]";
	}
	
	
}