package com.panfeng.fastcopy;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyConfig {

	private static ExecutorService fixedThreadPool;

	// ԴĿ¼
	private static String srcDir;
	// Ŀ��Ŀ¼
	private static String dstDir;

	// ͬʱ��������߳�����������Ϊcpu������
	private static int RUN_THREAD = 8;

	// ��ǰ�Ѿ�������ɵ��ļ���������¼��־��
	private static long run_number = 0;

	// ��¼�ļ���λ��,����ϵ�����
	private static String recordFile;
	// ÿ����ҳ�ļ�������ļ�������
	private static int pageDirNum = 5000;
	// ���ü������
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

		// ����ԴĿ¼��Ŀ��Ŀ¼
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
		// ������������
		if (args.length < 3) {
			return;
		}

		try {
			String arg = args[3];
			handleOtherParam(arg, 3, args);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(args[3] + "��ֵ��Ч��");
		} catch (NumberFormatException e) {
			System.out.println(args[3] + "��ֵ����Ϊ��Ч���֣�");
		}

		if (args.length < 4) {
			return;
		}
		try {
			String arg = args[4];
			handleOtherParam(arg, 4, args);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(args[4] + "��ֵ��Ч��");
		} catch (NumberFormatException e) {
			System.out.println(args[4] + "��ֵ����Ϊ��Ч���֣�");
		}
		if (args.length < 5) {
			return;
		}

		try {
			String arg = args[5];
			handleOtherParam(arg, 5, args);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(args[5] + "��ֵ��Ч��");
		} catch (NumberFormatException e) {
			System.out.println(args[5] + "��ֵ����Ϊ��Ч���֣�");
		}
		//��¼�ļ�
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

	// ��ʼ��
	public static void Init() {
		try {
			String srcDir = CopyConfig.getSrcDir();
			File src = new File(srcDir);
			if (src.isDirectory() || src.canRead()) {
				System.out.println("����ԴĿ¼����һ����Ч���ļ���·������ԴĿ¼���ɶ���");
			}

			String dstDir = CopyConfig.getDstDir();
			File dst = new File(dstDir);
			if (!dst.isDirectory() && !dst.exists()) {
				System.out.println("��⵽Ŀ��Ŀ¼�����ڣ����ڴ���");
				if (!dst.mkdirs()) {
					System.out.println("�ļ��д���ʧ�ܣ�");
					return;
				}
				System.out.println("�Ѵ����ļ��У�" + dst.getAbsolutePath());
			}
			// �Ƿ��ȡ��¼�ļ����ϵ�������ʱ����
			String recordFile = CopyConfig.getRecordFile();
			File record = new File(recordFile);
			if (record.exists()) {
				CopyConfig.setReadRecordFile(true);
			} else {
				record.createNewFile();
			}
			CopyConfig.fixedThreadPool = Executors.newFixedThreadPool(CopyConfig.getRUN_THREAD());
			System.out.println("��ʼ���ɹ���");
		} catch (IOException e) {
			System.out.println("��ʼ��ʧ�ܣ�");
		}
	}

}
