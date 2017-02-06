package com.jxyj.train.admindao;

import java.sql.SQLException;
import java.util.List;

import com.jxyj.train.entity.FileStd;
import com.jxyj.train.entity.Score;
import com.jxyj.train.entity.Standard;
import com.jxyj.train.entity.UserScore;

public interface AdminGradeTackle {
	
	/**
	 * 增加评分标准
	 * @param std
	 * @return true 代表添加成功，否则添加失败
	 */
	boolean addStd(List<Standard> stds,FileStd fileStd);
	
	/**
	 * 删除单条评分标准
	 * @param std
	 * @return true 代表删除成功，否则删除失败
	 */
	boolean deleteStd(Standard std);
	
	/**
	 * 根据作业ID查找所有相关标准
	 * @param fileId
	 * @return 查询到的结果集，null代表的非法fileId或者标准集不存在
	 * @throws SQLException 
	 */
	List<Standard> getAllStds(int fileId) throws SQLException;
	
	/**
	 * 添加操作记录
	 * @param fileStd
	 * @return
	 * @throws SQLException 
	 */
	boolean addFileStd(FileStd fileStd) throws SQLException;
	
	/**
	 * 添加成绩
	 * @param scores
	 * @return
	 * @throws SQLException
	 */
	boolean addScores(List<Score> scores);
	
	/**
	 * 获取成绩
	 * @return
	 * @throws SQLException
	 */
	List<UserScore> getScores(String sql,Object[] objs) throws SQLException;
}
