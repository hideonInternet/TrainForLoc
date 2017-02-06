package com.jxyj.train.entity;

import java.io.Serializable;

public class Standard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6346364685884095551L;
	private int stdId;
	private String stdContent;
	private String grade;
	private int fileId;
	
	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}



	public Standard() {
		super();
	}
	
	

	public Standard(int stdId, String stdContent, String grade,int fileId) {
		super();
		this.stdId = stdId;
		this.stdContent = stdContent;
		this.grade = grade;
		this.fileId=fileId;
	}



	public int getStdId() {
		return stdId;
	}


	public void setStdId(int stdId) {
		this.stdId = stdId;
	}


	public String getStdContent() {
		return stdContent;
	}


	public void setStdContent(String stdContent) {
		this.stdContent = stdContent;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}



	@Override
	public String toString() {
		return "Standard [stdId=" + stdId + ", stdContent=" + stdContent + ", grade=" + grade + ", fileId=" + fileId
				+ "]";
	}


	
}
