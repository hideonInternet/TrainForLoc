package com.jxyj.train.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 该实体类是管理员上传标准答案的管理类，
 * answerId与fileId对应
 * answerName与fileName对应。
 * operator属性为上传标准答案的管理员用户名
 * uploadTime为上传时间
 * answerPath为文件保存路径
 * @author lenovo
 *
 */
public class AnswerInf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2803468115077840297L;

	
	private String answerId;
	private String answerName;
	private String answerPath;
	private String operator;
	private Timestamp uploadTime;
	public AnswerInf(String answerId, String answerName, String answerPath, String operator, Timestamp uploadTime) {
		super();
		this.answerId = answerId;
		this.answerName = answerName;
		this.answerPath = answerPath;
		this.operator = operator;
		this.uploadTime = uploadTime;
	}
	public AnswerInf() {
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getAnswerName() {
		return answerName;
	}
	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}
	public String getAnswerPath() {
		return answerPath;
	}
	public void setAnswerPath(String answerPath) {
		this.answerPath = answerPath;
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
	
	
}
