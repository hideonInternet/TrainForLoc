package com.jxyj.train.admindao;

import com.jxyj.train.utils.PageBean;

public interface Page<T> {
	/**
	 * �������ݲ�ѯ���ķ�ҳ���ݷ�װ��PageBean����
	 */
	public void getAll(PageBean<T> pb);
	
	/**
	 * ��ѯ�ܼ�¼��
	 */
	public int getTotalCount();
}
