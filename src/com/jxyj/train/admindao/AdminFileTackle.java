package com.jxyj.train.admindao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jxyj.train.entity.AnswerInf;
import com.jxyj.train.entity.FileInf;
import com.jxyj.train.entity.UserAnsInf;

public interface AdminFileTackle {
	/**
	 * ��ѯ�����Ѿ��ϴ���ҵ
	 * @return
	 * @throws SQLException
	 */
	List<FileInf> findAllFile() throws SQLException;
	/**
	 * ��ѯָ������ҵlist����
	 * @param sql �Զ���sql���
	 * @param objs ��Ҫ�Ĳ�����������Ϊnull
	 * @return ���ز�ѯ���Ľ������������һ��null��������п���ָ��������û�з��Ͻ������sql���Ϊ��
	 * @throws SQLException
	 */
	List<FileInf> findPointedFile(String sql,Object... objs) throws SQLException;
	
	/**
	 * �ϴ���ҵ,��ȷ��fileInf��Ϊnull,�����һֱ����false;
	 * @param fileInf ��ҵ��Ϣ
	 * @return true �����ϴ��ɹ��� false�����ϴ�ʧ��
	 * @throws SQLException 
	 */
	boolean uploadFile(FileInf fileInf) throws SQLException;
	
	/**
	 * һ�����ϴ�������ҵ
	 * @param fileInfs ������ҵ�ļ���
	 * @return true �����ϴ��ɹ��� false�����ϴ�ʧ��
	 * @throws SQLException
	 */
	boolean uploadAllFile(List<FileInf> fileInfs) throws SQLException;
	
	/**
	 * ɾ����ҵ {ע�⣺��ɾ����ҵ��ʱ��Ӧ����ɾ������ҵ�ı�׼�𰸺��û��ϴ��Ĵ𰸣�����
	 * �ᱨ��}
	 * @param fileInf ��ҵ��Ϣ
	 * @return true ����ɾ���ɹ��� false����ɾ��ʧ��
	 * @throws SQLException 
	 */
	boolean deleteFile(FileInf fileInf) throws SQLException;
	/**
	 * ����ָ����ҵ�������û��ϴ���
	 * @param fileInf ָ����ҵ��Ϣ
	 * @return ����ָ�����û��ϴ���ҵ����fileInfΪnull,������Ҳ��null
	 * @throws SQLException 
	 */
	Map<String, UserAnsInf> downloadAllUserAnswer(FileInf fileInf) throws SQLException;
	
	/**
	 * �ϴ�ָ����ҵ�ı�׼��
	 * @param answerInf
	 * @throws SQLException 
	 * @return true �����ϴ��ɹ��� false�����ϴ�ʧ��
	 */
	boolean uploadAnswer(AnswerInf answerInf) throws SQLException;
	
	/**
	 * ɾ��ָ����ҵ�ı�׼�𰸡�
	 * @param answerInf
	 * @return true ����ɾ���ɹ��� false����ɾ��ʧ��
	 * @throws SQLException 
	 */
	boolean deleteAnswer(AnswerInf answerInf) throws SQLException;
	
	/**
	 * ������Ҫɾ��ָ�����û��ϴ��𰸣�
	 * @param sql ָ����ɾ�����
	 * @param objs ����Ҫ�Ĳ���
	 * @return true ����ɾ���ɹ��� false����ɾ��ʧ��
	 * @throws SQLException
	 */
	boolean deleteUserAnswer(String sql,Object[] objs) throws SQLException;
	
	/**
	 * ����ָ������ҵ
	 * @param fileInf
	 * @return ���ظ���ҵʵ��,��Ϊnull�����Ҫô�Ҳ�����Ӧ�ļ���Ҫô�����fileInfΪnull
	 * @throws SQLException
	 */
	FileInf downloadFile(FileInf fileInf) throws SQLException;
	
	/**
	 * ���ر�׼��
	 * @param fileId
	 * @return
	 * @throws SQLException
	 */
	AnswerInf  downloadStdAns(String fileId) throws SQLException;
	
}
