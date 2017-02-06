package com.jxyj.train.adminservice;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jxyj.train.admindao.AdminGradeTackle;
import com.jxyj.train.admindao.adminImpl.AdminGradeTackleImpl;
import com.jxyj.train.entity.FileStd;
import com.jxyj.train.entity.Score;
import com.jxyj.train.entity.Standard;
import com.jxyj.train.entity.UserScore;
import com.jxyj.train.utils.JDBCUtils;

public class AdminGradeService {
	AdminGradeTackle adminGradeTackle = new AdminGradeTackleImpl();

	public boolean addStd(List<Standard> stds,FileStd fileStd){
		return adminGradeTackle.addStd(stds, fileStd);		
	}
	public List<Standard> getAllStds(int fileId) throws SQLException{
		List<Standard> allStds = adminGradeTackle.getAllStds(fileId);
		return allStds;
	}
	public boolean addScores(List<Score> scores){
		return adminGradeTackle.addScores(scores);
	}
	public List<UserScore> getUserScoreByUserId(int userId) throws SQLException{
		/**
		 * 	score表和standard表的联合查询
		 */
		String sql=
				"select USERID, FILEID, STDCONTENT,SCORE, GRADE, OPERATOR FROM SCORE INNER JOIN STANDARD ON SCORE.STDID=STANDARD.STDID WHERE USERID=?";
		QueryRunner qr=JDBCUtils.getQueryRunner();
		List<UserScore> userScoList = 
				qr.query(sql, new BeanListHandler<UserScore>(UserScore.class), userId);
		return userScoList;
		
	}
	public List<UserScore> getUserScoreByFileId(int fileId) throws SQLException{
		String sql="select USERID, FILEID, STDCONTENT,SCORE, GRADE, OPERATOR FROM SCORE INNER JOIN STANDARD ON SCORE.STDID=STANDARD.STDID WHERE FILEID=?";
		QueryRunner qr=JDBCUtils.getQueryRunner();
		List<UserScore> userScoList = qr.query(sql, new BeanListHandler<UserScore>(UserScore.class), fileId);
		return userScoList;
		
	}
}
