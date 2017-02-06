package com.jxyj.train.adminservice;

import java.sql.SQLException;
import java.util.List;

import com.jxyj.train.admindao.AdminInformTackle;
import com.jxyj.train.admindao.adminImpl.AdminInformTackleImpl;
import com.jxyj.train.entity.InformInf;

public class AdminInformService {
	private AdminInformTackle adminInformTackle = new AdminInformTackleImpl();

	public boolean announce(InformInf informInf) throws SQLException {
		return adminInformTackle.announce(informInf);
	}

	public boolean deleteInf(InformInf informInf) throws SQLException {
		return adminInformTackle.deleteInf(informInf);
	}

	public List<InformInf> findAllInform() throws SQLException {
		List<InformInf> informList = adminInformTackle.findAllInform();
		return informList;
	}

	public List<InformInf> findPointedInform(String sql, Object... objs) throws SQLException {
		List<InformInf> pointedList = adminInformTackle.findPointedInform(sql, objs);
		return pointedList;
	}
}
