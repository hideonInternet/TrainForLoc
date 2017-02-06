package com.jxyj.train.entity;

import java.io.Serializable;

public class Score implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1919922778514572496L;
	int scoreId;
	int stdId;
	int score;
	int userId;
	String operator;
	public int getScoreId() {
		return scoreId;
	}
	public void setScoreId(int scoreId) {
		this.scoreId = scoreId;
	}
	public int getStdId() {
		return stdId;
	}
	public void setStdId(int stdId) {
		this.stdId = stdId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Score(int scoreId, int stdId, int score, int userId, String operator) {
		super();
		this.scoreId = scoreId;
		this.stdId = stdId;
		this.score = score;
		this.userId = userId;
		this.operator = operator;
	}
	public Score() {
		super();
	}
	@Override
	public String toString() {
		return "Score [scoreId=" + scoreId + ", stdId=" + stdId + ", score=" + score + ", userId=" + userId
				+ ", operator=" + operator + "]";
	}
	
	
}
