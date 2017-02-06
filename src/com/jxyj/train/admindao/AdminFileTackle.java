package com.jxyj.train.admindao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jxyj.train.entity.AnswerInf;
import com.jxyj.train.entity.FileInf;
import com.jxyj.train.entity.UserAnsInf;

public interface AdminFileTackle {
	/**
	 * 查询所有已经上传作业
	 * @return
	 * @throws SQLException
	 */
	List<FileInf> findAllFile() throws SQLException;
	/**
	 * 查询指定的作业list集合
	 * @param sql 自定义sql语句
	 * @param objs 需要的参数，若无则为null
	 * @return 返回查询到的结果集，若返回一个null，则代表有可能指定条件下没有符合结果或者sql语句为空
	 * @throws SQLException
	 */
	List<FileInf> findPointedFile(String sql,Object... objs) throws SQLException;
	
	/**
	 * 上传作业,请确保fileInf不为null,否则会一直返回false;
	 * @param fileInf 作业信息
	 * @return true 代表上传成功， false代表上传失败
	 * @throws SQLException 
	 */
	boolean uploadFile(FileInf fileInf) throws SQLException;
	
	/**
	 * 一次性上传所有作业
	 * @param fileInfs 所有作业的集合
	 * @return true 代表上传成功， false代表上传失败
	 * @throws SQLException
	 */
	boolean uploadAllFile(List<FileInf> fileInfs) throws SQLException;
	
	/**
	 * 删除作业 {注意：在删除作业的时候，应当先删除该作业的标准答案和用户上传的答案，否则
	 * 会报错}
	 * @param fileInf 作业信息
	 * @return true 代表删除成功， false代表删除失败
	 * @throws SQLException 
	 */
	boolean deleteFile(FileInf fileInf) throws SQLException;
	/**
	 * 下载指定作业的所有用户上传答案
	 * @param fileInf 指定作业信息
	 * @return 返回指定的用户上传作业，若fileInf为null,则结果集也会null
	 * @throws SQLException 
	 */
	Map<String, UserAnsInf> downloadAllUserAnswer(FileInf fileInf) throws SQLException;
	
	/**
	 * 上传指定作业的标准答案
	 * @param answerInf
	 * @throws SQLException 
	 * @return true 代表上传成功， false代表上传失败
	 */
	boolean uploadAnswer(AnswerInf answerInf) throws SQLException;
	
	/**
	 * 删除指定作业的标准答案。
	 * @param answerInf
	 * @return true 代表删除成功， false代表删除失败
	 * @throws SQLException 
	 */
	boolean deleteAnswer(AnswerInf answerInf) throws SQLException;
	
	/**
	 * 根据需要删除指定的用户上传答案，
	 * @param sql 指定的删除语句
	 * @param objs 所需要的参数
	 * @return true 代表删除成功， false代表删除失败
	 * @throws SQLException
	 */
	boolean deleteUserAnswer(String sql,Object[] objs) throws SQLException;
	
	/**
	 * 下载指定的作业
	 * @param fileInf
	 * @return 返回该作业实例,若为null则代表要么找不到对应文件，要么传入的fileInf为null
	 * @throws SQLException
	 */
	FileInf downloadFile(FileInf fileInf) throws SQLException;
	
	/**
	 * 下载标准答案
	 * @param fileId
	 * @return
	 * @throws SQLException
	 */
	AnswerInf  downloadStdAns(String fileId) throws SQLException;
	
}
