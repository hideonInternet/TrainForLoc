package com.jxyj.train.entity;

public class TempScore {
	
	private int score;//µÃ·Ö
	private String stdContent;
	private int grade;//Âú·Ö
	private String operator;
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
	public TempScore(int score, String stdContent, int grade, String operator) {
		super();
		this.score = score;
		this.stdContent = stdContent;
		this.grade = grade;
		this.operator = operator;
	}
	public TempScore() {
		super();
	}
	@Override
	public String toString() {
		return "TempScore [score=" + score + ", stdContent=" + stdContent + ", grade=" + grade + ", operator="
				+ operator + "]";
	}
	
	
}
