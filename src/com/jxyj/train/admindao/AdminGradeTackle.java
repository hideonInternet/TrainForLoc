package com.jxyj.train.admindao;

import java.sql.SQLException;
import java.util.List;

import com.jxyj.train.entity.FileStd;
import com.jxyj.train.entity.Score;
import com.jxyj.train.entity.Standard;
import com.jxyj.train.entity.UserScore;

public interface AdminGradeTackle {
	
	/**
	 * �������ֱ�׼
	 * @param std
	 * @return true ������ӳɹ����������ʧ��
	 */
	boolean addStd(List<Standard> stds,FileStd fileStd);
	
	/**
	 * ɾ���������ֱ�׼
	 * @param std
	 * @return true ����ɾ���ɹ�������ɾ��ʧ��
	 */
	boolean deleteStd(Standard std);
	
	/**
	 * ������ҵID����������ر�׼
	 * @param fileId
	 * @return ��ѯ���Ľ������null����ķǷ�fileId���߱�׼��������
	 * @throws SQLException 
	 */
	List<Standard> getAllStds(int fileId) throws SQLException;
	
	/**
	 * ��Ӳ�����¼
	 * @param fileStd
	 * @return
	 * @throws SQLException 
	 */
	boolean addFileStd(FileStd fileStd) throws SQLException;
	
	/**
	 * ��ӳɼ�
	 * @param scores
	 * @return
	 * @throws SQLException
	 */
	boolean addScores(List<Score> scores);
	
	/**
	 * ��ȡ�ɼ�
	 * @return
	 * @throws SQLException
	 */
	List<UserScore> getScores(String sql,Object[] objs) throws SQLException;
}
