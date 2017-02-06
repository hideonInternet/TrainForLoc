package com.jxyj.train.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ��ʵ�����ǹ���Ա�ϴ���׼�𰸵Ĺ����࣬
 * answerId��fileId��Ӧ
 * answerName��fileName��Ӧ��
 * operator����Ϊ�ϴ���׼�𰸵Ĺ���Ա�û���
 * uploadTimeΪ�ϴ�ʱ��
 * answerPathΪ�ļ�����·��
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
