package com.jxyj.train.admindao.adminImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jxyj.train.admindao.Page;
import com.jxyj.train.entity.UserInf;
import com.jxyj.train.utils.JDBCUtils;
import com.jxyj.train.utils.PageBean;


public class AdminUserPage implements Page<UserInf>{

	@Override
	public void getAll(PageBean<UserInf> pb) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				int curPage =pb.getCurrentPage();
				int begin =(curPage-1)*pb.getPageCount();
				int pageCount =pb.getPageCount();
				pb.setTotalCount(this.getTotalCount());
				String sql="select * from user limit ?,?";
				QueryRunner qr=JDBCUtils.getQueryRunner();
				try {
					
				  List<UserInf> list = qr.query(sql, new BeanListHandler<UserInf>(UserInf.class), begin,pageCount);			
				  list.sort(new Comparator<UserInf>() {

					@Override
					public int compare(UserInf o1, UserInf o2) {
						// TODO Auto-generated method stub
						return -o1.compareTo(o2);
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
				String sql="select count(*) from user";
				QueryRunner qr=JDBCUtils.getQueryRunner();
				Long count=null;
				try {
					count= qr.query(sql, new ScalarHandler<Long>());
	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(count==null)
					count=0L;
				return  count.intValue();
			}

}
