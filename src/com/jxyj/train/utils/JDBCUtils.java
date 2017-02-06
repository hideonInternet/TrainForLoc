package com.jxyj.train.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	private static QueryRunner qr = null;
	// 声明线程共享变量
	public static ThreadLocal<Connection> container = new ThreadLocal<Connection>();

	static {
		qr = new QueryRunner(dataSource);
	}

	// 获取共享变量
	public static ThreadLocal<Connection> getContainer() {
		return container;
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 获取当前线程上的连接 开启事务
	 */
	public static void startTransaction() {
		Connection conn = container.get();// 首先获取当前线程的连接
		if (conn == null) {// 如果连接为空
			conn = getConnection(); // 从连接池中获取连接
			container.set(conn);// 将此连接放在当前线程上
		}
		try {
			conn.setAutoCommit(false);// 开启事务
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 提交事务
	 */
	public static void commit() {
		Connection conn = container.get();// 从当前线程上获取连接
		if (conn != null) {// 如果连接为空，则不做处理
			try {
				conn.commit();// 提交事务
				//conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 回滚事务
	 */
	public static void rollback() {
		Connection conn = container.get();// 检查当前线程是否存在连接
		if (conn != null) {
			try {
				conn.rollback();// 回滚事务
				// container.remove();//如果回滚了，就移除这个连接
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 关闭连接
	 */
	public static void close() {
		Connection conn = container.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				container.remove();// 从当前线程移除连接 切记
			}
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return 数据库连接 连接方式（连接池 c3p0）
	 */
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 得到QR
	 * 
	 * @return
	 */
	public static QueryRunner getQueryRunner() {
		return qr;
	}
	
	
}
