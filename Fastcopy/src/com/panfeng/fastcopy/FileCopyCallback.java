package com.panfeng.fastcopy;

import java.io.Closeable;
import java.io.File;

public interface FileCopyCallback {
	/**
	 * 删除旧文件之前
	 * @param oldFile
	 */
	public void afterDeleteOldFile(File oldFile);
	/**
	 * 删除旧文件之后
	 * @param oldFile
	 */
	public void beforeDeleteOldFile(File oldFile);
	/**
	 * 创建新文件之前
	 * @param newfile
	 */
	public void afterCreateNewFile(File newfile);
	/**
	 * 创建新文件之后
	 * @param newfile
	 */
	public void beforeCreateNewFile(File newfile);
	/**
	 * 复制文件之前
	 * @param srcFile
	 * @param dstfile
	 */
	public void afterCopyFile(File srcFile,File dstfile);
	/**
	 * 复制文件之后
	 * @param srcFile
	 * @param dstfile
	 */
	public void beforeCopyFile(File srcFile,File dstfile);
	/**
	 * 出现异常
	 * @param e
	 */
	public void onException(Exception e);
	/**
	 * 当关流结束
	 */
	public void onCloseOver();
	/**
	 * 当关流出现异常
	 * @param e
	 * @param streams
	 */
	public void onCloseException(Exception e,Closeable...streams);
}
