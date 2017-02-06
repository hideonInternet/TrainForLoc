package com.jxyj.train.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	private static QueryRunner qr = null;
	// �����̹߳������
	public static ThreadLocal<Connection> container = new ThreadLocal<Connection>();

	static {
		qr = new QueryRunner(dataSource);
	}

	// ��ȡ�������
	public static ThreadLocal<Connection> getContainer() {
		return container;
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * ��ȡ��ǰ�߳��ϵ����� ��������
	 */
	public static void startTransaction() {
		Connection conn = container.get();// ���Ȼ�ȡ��ǰ�̵߳�����
		if (conn == null) {// �������Ϊ��
			conn = getConnection(); // �����ӳ��л�ȡ����
			container.set(conn);// �������ӷ��ڵ�ǰ�߳���
		}
		try {
			conn.setAutoCommit(false);// ��������
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * �ύ����
	 */
	public static void commit() {
		Connection conn = container.get();// �ӵ�ǰ�߳��ϻ�ȡ����
		if (conn != null) {// �������Ϊ�գ���������
			try {
				conn.commit();// �ύ����
				//conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * �ع�����
	 */
	public static void rollback() {
		Connection conn = container.get();// ��鵱ǰ�߳��Ƿ��������
		if (conn != null) {
			try {
				conn.rollback();// �ع�����
				// container.remove();//����ع��ˣ����Ƴ��������
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * �ر�����
	 */
	public static void close() {
		Connection conn = container.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				container.remove();// �ӵ�ǰ�߳��Ƴ����� �м�
			}
		}
	}

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return ���ݿ����� ���ӷ�ʽ�����ӳ� c3p0��
	 */
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * �õ�QR
	 * 
	 * @return
	 */
	public static QueryRunner getQueryRunner() {
		return qr;
	}
	
	
}
