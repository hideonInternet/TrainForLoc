package com.jxyj.train.userservice;

import java.io.File;
import java.sql.SQLException;

import com.jxyj.train.entity.UserAnsInf;
import com.jxyj.train.userdao.impl.UseFileTackleImpl;

public class UserFileService extends UseFileTackleImpl {

	/**
	 * 删除上次用户上传文件答案
	 * @param userFileId
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteAnsFromDiskByFileId(String userFileId) throws SQLException {
		String sql = "select userFilePath from useruphomework where userFileId=?";
		UserAnsInf pointedAns = null;
		boolean flag=false;
		if (userFileId != null) {
			pointedAns = this.findPointedAns(sql, userFileId);
			String userFilePath = pointedAns.getUserFilePath();
			File file = new File(userFilePath);
			if(file.exists())
				file.delete();
			flag=true;
		}
		return flag;
	}
	

}
