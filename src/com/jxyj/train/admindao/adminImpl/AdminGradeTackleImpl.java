package com.jxyj.train.admindao.adminImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jxyj.train.admindao.AdminGradeTackle;
import com.jxyj.train.entity.FileStd;
import com.jxyj.train.entity.Score;
import com.jxyj.train.entity.Standard;
import com.jxyj.train.entity.UserScore;
import com.jxyj.train.utils.JDBCUtils;

public class AdminGradeTackleImpl implements AdminGradeTackle {
	QueryRunner qr = JDBCUtils.getQueryRunner();
	QueryRunner tranQr = new QueryRunner();
	@Override
	public boolean addStd(List<Standard> stds, FileStd fileStd) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if (stds == null || stds.size() == 0)
			return flag;
		String sql = "insert into STANDARD(STDCONTENT,FILEID,GRADE) VALUES(?,?,?)";
		Object[][] strs = new Object[stds.size()][3];
		for (int i = 0; i < stds.size(); i++) {
			int fileId = stds.get(i).getFileId();
			String grade = stds.get(i).getGrade();
			String stdContent = stds.get(i).getStdContent();
			Object[] obj = { stdContent, fileId, grade };
			strs[i] = obj;
		}
			JDBCUtils.startTransaction();
			Connection conn = JDBCUtils.getContainer().get();
			try {
				//如果记录插入成功，则进行规则插入
				if(addFileStd(fileStd)){
					tranQr.batch(conn, sql, strs);
					JDBCUtils.commit();
					flag=true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JDBCUtils.rollback();
				e.printStackTrace();
			
		}
		return flag;
	}

	@Override
	public boolean deleteStd(Standard std) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Standard> getAllStds(int fileId) throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from STANDARD where FILEID=?";
		List<Standard> stds = qr.query(sql, new BeanListHandler<>(Standard.class), fileId);
		return stds;
	}

	@Override
	public boolean addFileStd(FileStd fileStd) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO FILESTD(FILEID,OPERATOR) VALUES(?,?)";
		int fileId = fileStd.getFileId();
		String operator = fileStd.getOperator();
		qr.insert(sql, new BeanHandler<>(FileStd.class), fileId, operator);
		return true;
	}

	@Override
	public boolean addScores(List<Score> scores){
		// TODO Auto-generated method stub
		boolean flag=false;
		if(scores.isEmpty()){
			return flag;
		}
		String sql="insert into SCORE(USERID,STDID,OPERATOR,SCORE) VALUES(?,?,?,?)";
		Object[][] objs=new Object[scores.size()][4];
		for(int i=0;i<scores.size();i++){
			int userId = scores.get(i).getUserId();
			int score = scores.get(i).getScore();
			int stdId = scores.get(i).getStdId();
			String operator = scores.get(i).getOperator();
			Object[] obj={userId,stdId,operator,score};
			objs[i]=obj;
		}
		try {
			JDBCUtils.startTransaction();
			Connection conn = JDBCUtils.getContainer().get();
			tranQr.batch(conn, sql, objs);
			JDBCUtils.commit();
			flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JDBCUtils.rollback();
		}
		return flag;
	}

	@Override
	public List<UserScore> getScores(String sql,Object[] objs) throws SQLException {
		// TODO Auto-generated method stub
		List<UserScore> scoList = qr.query(sql, new BeanListHandler<UserScore>(UserScore.class), objs);
		return scoList;
		
	}
}
