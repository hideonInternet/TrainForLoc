package com.jxyj.train.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PageBean<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8326664670666550349L;
	private int currentPage; // 当前页
	private int pageCount; // 查询返回的行数
	private int totalCount; // 总记录数
	private int totalPage; // 总页数
	private List<T> pageData; // 分页查询到的数据
	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalCount%pageCount==0 ?totalCount/pageCount:
			totalCount/pageCount+1;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	@Override
	public String toString() {
		return "PageBean [currentPage=" + currentPage + ", pageCount=" + pageCount + ", totalCount=" + totalCount
				+ ", totalPage=" + totalPage + ", pageData=" + pageData + "]";
	}

	public PageBean(int currentPage, int pageCount, int totalCount, int totalPage, List<T> pageData) {
		super();
		this.currentPage = currentPage;
		this.pageCount = pageCount;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageData = pageData;
	}

	public PageBean() {

	}

}
