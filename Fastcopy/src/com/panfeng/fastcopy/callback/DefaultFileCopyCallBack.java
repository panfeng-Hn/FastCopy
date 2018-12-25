package com.panfeng.fastcopy.callback;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.panfeng.fastcopy.CopyConfig;
import com.panfeng.fastcopy.FileCopyCallback;
import com.panfeng.fastcopy.util.CommandUtil;

public class DefaultFileCopyCallBack implements FileCopyCallback {

	@Override
	public void afterDeleteOldFile(File oldFile) {

	}

	@Override
	public void beforeDeleteOldFile(File oldFile) {

	}

	@Override
	public void afterCreateNewFile(File newfile) {

	}

	@Override
	public void beforeCreateNewFile(File newfile) {

	}

	@Override
	public void afterCopyFile(File srcFile, File dstfile) {
		try {
			// �Ƚ��Ѿ����ص��ļ�������һ
			CopyConfig.addRunNumber();
			File file = new File(CopyConfig.getRecordFile());
			Writer reader = new FileWriter(file);
			// д��¼�ļ�
//			BufferedWriter br = new BufferedWriter(reader);
//			br.write(dstfile.getName());
//			br.flush();
			// �ж��Ƿ�Ҫ����Ŀ¼���ļ��ƶ�����һ��Ŀ¼
			File parentFile = dstfile.getParentFile();
			File[] list = parentFile.listFiles();
			int fileNum = list.length;
			// java �е�renameTo�����ƶ�ͬһ�����̵��ļ�������IO�������������ƶ��ٶȺܿ�
			// ���ڿ��Ǹĳɵ��ñ��� mv move������ֱ�Ӳ���
			int pageSize=CopyConfig.getPageDirNum();
			if(parentFile.getAbsolutePath().equals(CopyConfig.getLastDir())) {
				pageSize=CopyConfig.getFileNum()%CopyConfig.getPageDirNum();
			}
			
			
			if (fileNum >= pageSize) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (File copysFile : list) {
							copysFile.renameTo(new File(CopyConfig.getDstDir(),copysFile.getName()));
							System.out.println(copysFile.getName()+"�ƶ���ɣ�");
						}
						parentFile.delete();
						
//						if (CommandUtil.isWindows()) {
//							for (File listfile : list) {
//								CommandUtil.copyFileAsMove(listfile.getAbsolutePath(), CopyConfig.getDstDir());
//							}
//						}else {
//							CommandUtil.copyFileAsMv(parentFile.getAbsolutePath(), CopyConfig.getDstDir(), true);
//						}
					}
				}).start();
			}
			System.out.println(srcFile.getName() + "�������");
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
	}
	
	

	@Override
	public void beforeCopyFile(File srcFile, File dstfile) {

	}

	@Override
	public void onException(Exception e) {

	}

	@Override
	public void onCloseOver() {

	}

	@Override
	public void onCloseException(Exception e, Closeable... streams) {

	}

}
