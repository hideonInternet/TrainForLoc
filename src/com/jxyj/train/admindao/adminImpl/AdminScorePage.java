package com.jxyj.train.admindao.adminImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jxyj.train.adminservice.AdminFileService;
import com.jxyj.train.entity.TempScore;
import com.jxyj.train.entity.UserScore;
import com.jxyj.train.utils.JDBCUtils;
import com.jxyj.train.utils.PageBean;

public class AdminScorePage {

	public boolean getAll(PageBean<UserScore> pb, String w, int id) {
		// TODO Auto-generated method stub
		int curPage = pb.getCurrentPage();
		int begin = (curPage - 1) * pb.getPageCount();
		int pageCount = pb.getPageCount();
		QueryRunner qr = JDBCUtils.getQueryRunner();
		List<UserScore> list = null;
		String sql;

		if ("userId".equals(w)) {
			pb.setTotalCount(this.getTotalCountByUserId(id));
			sql = "select USERID, FILEID, STDCONTENT,SCORE, GRADE, OPERATOR FROM SCORE INNER JOIN "
					+ "STANDARD ON SCORE.STDID=STANDARD.STDID WHERE USERID=?";
			try {
				list = qr.query(sql, new BeanListHandler<>(UserScore.class), id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("fileId".equals(w)) {
			pb.setTotalCount(this.getTotalCountByFileId(id));
			sql = "select USERID, FILEID, STDCONTENT,SCORE, GRADE, OPERATOR FROM SCORE INNER JOIN "
					+ "STANDARD ON SCORE.STDID=STANDARD.STDID WHERE FILEID=?";
			try {
				list = qr.query(sql, new BeanListHandler<>(UserScore.class), id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (list == null || list.isEmpty()) {
			return false;
		} else {
			Map<String, List<TempScore>> map = this.setTempScore(list);
			pb.setPageData(list);
			pb.setObj(map);
			return true;
		}

	}

	public int getTotalCountByUserId(int userId) {
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) FROM SCORE INNER JOIN STANDARD ON SCORE.STDID=STANDARD.STDID WHERE USERID=? ";
		QueryRunner qr = JDBCUtils.getQueryRunner();
		Long count = null;
		try {
			count = qr.query(sql, new ScalarHandler<Long>(), userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (count == null)
			count = 0L;
		return count.intValue();
	}

	public int getTotalCountByFileId(int fileId) {
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) FROM SCORE INNER JOIN STANDARD ON SCORE.STDID=STANDARD.STDID WHERE FILEID=?";
		QueryRunner qr = JDBCUtils.getQueryRunner();
		Long count = null;
		try {
			count = qr.query(sql, new ScalarHandler<Long>(), fileId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (count == null)
			count = 0L;
		return count.intValue();
	}

	public Map<String, List<TempScore>> setTempScore(List<UserScore> list) {
		String fileName = null;
		int grade;
		int score;
		String tempFileName=null;
		String operator = null;
		String stdContent = null;
		List<TempScore> tempList =new ArrayList<>();
		Set<String> set=new HashSet<>();
		AdminFileService adminFileService=new AdminFileService();

		Map<String, List<TempScore>> map = new TreeMap<>();
		
		for(UserScore userScore:list){
			try {
				fileName=adminFileService.getFileNameById(userScore.getFileId()+"");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			grade=userScore.getGrade();
			score=userScore.getScore();
			operator=userScore.getOperator();
			stdContent=userScore.getStdContent();
			
			if(!set.contains(fileName)){
				if(set.size()!=0){
					map.put(tempFileName, tempList);
				}			
				set.add(fileName);
				tempFileName=fileName;
				tempList=new ArrayList<>();
			}
			
			TempScore tempScore=new TempScore(score, stdContent, grade, operator);
			tempList.add(tempScore);			
		}
		map.put(tempFileName, tempList);
		return map;
	}

}
