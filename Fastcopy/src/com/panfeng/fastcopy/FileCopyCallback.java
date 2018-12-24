package com.panfeng.fastcopy;

import java.io.Closeable;
import java.io.File;

public interface FileCopyCallback {
	/**
	 * ɾ�����ļ�֮ǰ
	 * @param oldFile
	 */
	public void afterDeleteOldFile(File oldFile);
	/**
	 * ɾ�����ļ�֮��
	 * @param oldFile
	 */
	public void beforeDeleteOldFile(File oldFile);
	/**
	 * �������ļ�֮ǰ
	 * @param newfile
	 */
	public void afterCreateNewFile(File newfile);
	/**
	 * �������ļ�֮��
	 * @param newfile
	 */
	public void beforeCreateNewFile(File newfile);
	/**
	 * �����ļ�֮ǰ
	 * @param srcFile
	 * @param dstfile
	 */
	public void afterCopyFile(File srcFile,File dstfile);
	/**
	 * �����ļ�֮��
	 * @param srcFile
	 * @param dstfile
	 */
	public void beforeCopyFile(File srcFile,File dstfile);
	/**
	 * �����쳣
	 * @param e
	 */
	public void onException(Exception e);
	/**
	 * ����������
	 */
	public void onCloseOver();
	/**
	 * �����������쳣
	 * @param e
	 * @param streams
	 */
	public void onCloseException(Exception e,Closeable...streams);
}
