package com.panfeng.fastcopy.util;

import java.io.IOException;

public class CommandUtil {
	/**
	 * ͨ��move������ļ���ֻ֧��windows
	 * 
	 * @param srcfile
	 * @param dstFile
	 * @return
	 */
	public static boolean copyFileAsMove(String srcfile, String dstFile) {
		String cmd = "cmd /c move " + srcfile + " " + dstFile;
		return runCopyCommand(cmd);
	}

	/**
	 * ͨ��mv����ִ���ļ���ֻ֧��Linux
	 * 
	 * @param srcfile Դ�ļ������isDirΪtrue��������ԴĿ¼
	 * @param dstFile Ŀ���ļ�������isDirΪtrue��������Ŀ��Ŀ¼
	 * @param isDir �Ƿ�ΪĿ¼ģʽ
	 * @return
	 */
	public static boolean copyFileAsMv(String srcfile, String dstFile, boolean isDir) {
		String cmd = null;
		if (isDir) {
			cmd = "sh mv " + srcfile + "/* " + dstFile + "/";
		} else {

			cmd = "sh mv " + srcfile + " " + dstFile + "/";
		}
		return runCopyCommand(cmd);
	}

	/**
	 * �ж��Ƿ�Ϊwindows
	 * @return
	 */
	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		return os.toLowerCase().startsWith("win");
	}

	/**
	 * ����ָ��������
	 * @param command
	 * @return
	 */
	private static boolean runCopyCommand(String command) {
		try {
			Runtime.getRuntime().exec(command);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
