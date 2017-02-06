package com.jxyj.train.admindao.adminImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jxyj.train.admindao.AdminInformTackle;
import com.jxyj.train.entity.InformInf;
import com.jxyj.train.entity.UserInf;
import com.jxyj.train.utils.JDBCUtils;

public class AdminInformTackleImpl implements AdminInformTackle {
	static QueryRunner qr = JDBCUtils.getQueryRunner();
	@Override
	public boolean announce(InformInf informInf) throws SQLException {
		// TODO Auto-generated method stub
		if(informInf ==null){
			return false;
		}
		String infName = informInf.getInfName();
		String infText = informInf.getInfText();
		String operator = informInf.getOperator();
		String sql="insert into inform(infName,infText,operator) values(?,?,?)";
		qr.update(sql, infName,infText,operator);
		return true;
	}

	@Override
	public boolean deleteInf(InformInf informInf) throws SQLException {
		// TODO Auto-generated method stub
		if(informInf ==null){
			return false;
		}
		String infId=informInf.getInfId();
		String sql="delete from inform where infId=?";
		qr.update(sql, infId);
		return true;
	}

	@Override
	public List<InformInf> findAllInform() throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * form inform";
		List<InformInf> informList = qr.query(sql, new BeanListHandler<InformInf>(InformInf.class));
		return informList;
	}

	@Override
	public List<InformInf> findPointedInform(String sql, Object... objs) throws SQLException {
		// TODO Auto-generated method stub
		List<InformInf> pointedInformList = null;
		if (sql != null) {
			pointedInformList = qr.query(sql, new BeanListHandler<InformInf>(InformInf.class), objs);
		}
		return pointedInformList;
	}

}
