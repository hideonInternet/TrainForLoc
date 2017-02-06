package com.jxyj.train.admindao.adminImpl;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jxyj.train.admindao.Page;
import com.jxyj.train.entity.FileInf;
import com.jxyj.train.entity.UserInf;
import com.jxyj.train.utils.JDBCUtils;
import com.jxyj.train.utils.PageBean;

public class AdminPage implements Page<FileInf> {


	@Override
	public void getAll(PageBean<FileInf> pb) {
		// TODO Auto-generated method stub
		int curPage =pb.getCurrentPage();
		int begin =(curPage-1)*pb.getPageCount();
		int pageCount =pb.getPageCount();
		pb.setTotalCount(this.getTotalCount());
		String sql="select * from uphomework limit ?,?";
		QueryRunner qr=JDBCUtils.getQueryRunner();
		try {
			List<FileInf> list=
					qr.query(sql, new BeanListHandler<FileInf>(FileInf.class), begin,pageCount);
			list.sort(new Comparator<FileInf>() {
				@Override
				public int compare(FileInf o1, FileInf o2) {
					// TODO Auto-generated method stub
					return -o1.getUploadTime().compareTo(o2.getUploadTime());
				}
			});
			pb.setPageData(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		String sql="select count(*) from uphomework";
		QueryRunner qr=JDBCUtils.getQueryRunner();
		Long count=null;
		try {
			count= qr.query(sql, new ScalarHandler<Long>());
			return count.intValue();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count==null)
			return 0;
		
		return count.intValue();
	}

}
