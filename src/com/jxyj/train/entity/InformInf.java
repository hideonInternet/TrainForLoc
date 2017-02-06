package com.jxyj.train.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class InformInf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8334542542157585884L;
	private String infId;
	private String infName;
	private String infText;
	private String operator;
	private Timestamp infTime;
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public InformInf(String infId, String infName, String infText, Timestamp infTime) {
		super();
		this.infId = infId;
		this.infName = infName;
		this.infText = infText;
		this.infTime = infTime;
	}

	public InformInf() {
		super();
	}

	public String getInfId() {
		return infId;
	}

	public void setInfId(String infId) {
		this.infId = infId;
	}

	public String getInfName() {
		return infName;
	}

	public void setInfName(String infName) {
		this.infName = infName;
	}

	public String getInfText() {
		return infText;
	}

	public void setInfText(String infText) {
		this.infText = infText;
	}

	public Timestamp getInfTime() {
		return infTime;
	}

	public void setInfTime(Timestamp infTime) {
		this.infTime = infTime;
	}
	
	
	
}
