package com.panfeng.fastcopy;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyConfig {

	private static ExecutorService fixedThreadPool;

	// 源目录
	private static String srcDir;
	// 目标目录
	private static String dstDir;

	// 同时运行最大线程数量，建议为cpu核心数
	private static int RUN_THREAD = 8;

	// 当前已经复制完成的文件数量，记录日志用
	private static long run_number = 0;

	// 记录文件的位置,方便断点续传
	private static String recordFile;
	// 每个分页文件夹最大文件数量，
	private static int pageDirNum = 5000;
	// 配置加载情况
	private static boolean configLoadFlag = false;

	private static boolean isReadRecordFile = false;

	public static boolean isReadRecordFile() {
		return isReadRecordFile;
	}

	
	
	public static ExecutorService getFixedThreadPool() {
		return fixedThreadPool;
	}



	public static void setFixedThreadPool(ExecutorService fixedThreadPool) {
		CopyConfig.fixedThreadPool = fixedThreadPool;
	}



	public static void setReadRecordFile(boolean isReadRecordFile) {
		CopyConfig.isReadRecordFile = isReadRecordFile;
	}

	public static String getSrcDir() {
		return srcDir;
	}

	public static void setSrcDir(String srcDir) {
		CopyConfig.srcDir = srcDir;
	}

	public static String getDstDir() {
		return dstDir;
	}

	public static void setDstDir(String dstDir) {
		CopyConfig.dstDir = dstDir;
	}

	public static int getRUN_THREAD() {
		return RUN_THREAD;
	}

	public static void setRUN_THREAD(int rUN_THREAD) {
		RUN_THREAD = rUN_THREAD;
	}

	public static long getRun_number() {
		return run_number;
	}

	public synchronized static void addRunNumber() {
		run_number++;
	}

	public static String getRecordFile() {
		return recordFile;
	}

	public static void setRecordFile(String recordFile) {
		CopyConfig.recordFile = recordFile;
	}

	public static int getPageDirNum() {
		return pageDirNum;
	}

	public static void setPageDirNum(int pageDirNum) {
		CopyConfig.pageDirNum = pageDirNum;
	}

	public static void loadConfigFromCommond(final String[] args) {
		if (configLoadFlag) {
			return;
		}

		// 处理源目录和目标目录
		try {
			srcDir = args[1];
		} catch (IndexOutOfBoundsException e) {
			Service.PrintHelp();
		}
		try {
			dstDir = args[2];
		} catch (IndexOutOfBoundsException e) {
			Service.PrintHelp();
		}
		// 处理其他参数
		if (args.length < 3) {
			return;
		}

		try {
			String arg = args[3];
			handleOtherParam(arg, 3, args);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(args[3] + "的值无效！");
		} catch (NumberFormatException e) {
			System.out.println(args[3] + "的值必须为有效数字！");
		}

		if (args.length < 4) {
			return;
		}
		try {
			String arg = args[4];
			handleOtherParam(arg, 4, args);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(args[4] + "的值无效！");
		} catch (NumberFormatException e) {
			System.out.println(args[4] + "的值必须为有效数字！");
		}
		if (args.length < 5) {
			return;
		}

		try {
			String arg = args[5];
			handleOtherParam(arg, 5, args);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(args[5] + "的值无效！");
		} catch (NumberFormatException e) {
			System.out.println(args[5] + "的值必须为有效数字！");
		}
		//记录文件
		try {
			File record=null;
			if(recordFile==null) {
				record=new File(getDstDir());
			}else {
				record=new File(recordFile);
			}
			if(record.exists()) {
				isReadRecordFile=true;
			}else {
				record.createNewFile();
			}
		} catch (Exception e) {
			isReadRecordFile=false;
		}
		configLoadFlag = true;
	}

	private static void handleOtherParam(String argsName, int argsIndex, final String[] args) {
		switch (argsName) {
		case "-record":
			recordFile = args[argsIndex + 1];
			break;
		case "-threadnum":
			RUN_THREAD = Integer.parseInt(args[argsIndex + 1]);
			break;
		case "-pageDirNum":
			pageDirNum = Integer.parseInt(args[argsIndex + 1]);
			break;
		}
	}

	// 初始化
	public static void Init() {
		try {
			String srcDir = CopyConfig.getSrcDir();
			File src = new File(srcDir);
			if (src.isDirectory() || src.canRead()) {
				System.out.println("错误：源目录不是一个有效的文件夹路径。或源目录不可读！");
			}

			String dstDir = CopyConfig.getDstDir();
			File dst = new File(dstDir);
			if (!dst.isDirectory() && !dst.exists()) {
				System.out.println("检测到目标目录不存在，正在创建");
				if (!dst.mkdirs()) {
					System.out.println("文件夹创建失败！");
					return;
				}
				System.out.println("已创建文件夹：" + dst.getAbsolutePath());
			}
			// 是否读取记录文件。断点续传的时候用
			String recordFile = CopyConfig.getRecordFile();
			File record = new File(recordFile);
			if (record.exists()) {
				CopyConfig.setReadRecordFile(true);
			} else {
				record.createNewFile();
			}
			CopyConfig.fixedThreadPool = Executors.newFixedThreadPool(CopyConfig.getRUN_THREAD());
			System.out.println("初始化成功！");
		} catch (IOException e) {
			System.out.println("初始化失败！");
		}
	}

}
