package com.jxyj.train.admindao;

import com.jxyj.train.utils.PageBean;

public interface Page<T> {
	/**
	 * 将从数据查询到的分页数据封装到PageBean当中
	 */
	public void getAll(PageBean<T> pb);
	
	/**
	 * 查询总记录数
	 */
	public int getTotalCount();
}
