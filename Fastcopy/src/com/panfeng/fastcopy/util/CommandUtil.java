package com.panfeng.fastcopy.util;

import java.io.IOException;

public class CommandUtil {
	/**
	 * 通过move命令复制文件。只支持windows
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
	 * 通过mv命令执行文件。只支持Linux
	 * 
	 * @param srcfile 源文件。如果isDir为true。这里是源目录
	 * @param dstFile 目标文件。入托isDir为true。这里是目标目录
	 * @param isDir 是否为目录模式
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
	 * 判断是否为windows
	 * @return
	 */
	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		return os.toLowerCase().startsWith("win");
	}

	/**
	 * 运行指定的命令
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
