package com.jxyj.train.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.sun.jmx.snmp.Timestamp;

public class FileStd implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1651359483536231813L;
	private int fileId;
	private Set<Standard> stds=new HashSet<>();
	private String operator;
	private Timestamp time;
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
	public Set<Standard> getStds() {
		return stds;
	}
	public void setStds(Set<Standard> stds) {
		this.stds = stds;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public FileStd() {
		super();
	}
	public FileStd(int fileId, HashSet<Standard> stds, String operator, Timestamp time) {
		super();
		this.fileId = fileId;
		this.stds = stds;
		this.operator = operator;
		this.time = time;
	}
	@Override
	public String toString() {
		return "FileStd [fileId=" + fileId + ", stds=" + stds + ", operator=" + operator + ", time=" + time + "]";
	}
	
	
}
