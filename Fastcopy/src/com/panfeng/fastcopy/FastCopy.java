package com.panfeng.fastcopy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import com.panfeng.fastcopy.callback.DefaultFileCopyCallBack;

public class FastCopy {

	public static void main(String[] args) {
		// 从命令行加载配置
		CopyConfig.loadConfigFromCommond(args);
		// 初始化配置
		CopyConfig.Init();

		RunCopys();
	}
	/**
	 * 运行文件复制进程
	 */
	private static void RunCopys() {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				FileCopyCallback call = new DefaultFileCopyCallBack();
				String srcDir = CopyConfig.getSrcDir();
				File src = new File(srcDir);
				File[] srcFiles=null;
				if(CopyConfig.isReadRecordFile()) {
					srcFiles = getNoCopyFiles();
				}else {
					srcFiles = src.listFiles();
				}
				String lastDir=null;
				CopyConfig.setFileNum(srcFiles.length);
				System.out.println("源目录中共有" + srcFiles.length + "个文件");
				for (int i = 0, k = 1; i < srcFiles.length; i += CopyConfig.getPageDirNum(), k++) {
					File f = new File(CopyConfig.getDstDir(), ".temp_file_copy_" + k);
					lastDir=f.getAbsolutePath();
					if (!f.exists()) {
						System.out.println("创建临时文件夹："+f.getAbsolutePath());
						f.mkdirs();
					}
					for (int j = i; j < i +CopyConfig.getPageDirNum() && j < srcFiles.length; j++) {
						System.out.println("分配复制文件线程"+srcFiles[j].getName());
						CopyConfig.getFixedThreadPool().execute(
								new CopyFile(srcFiles[j], f.getAbsolutePath(), srcFiles[j].getName(), call));
					}
				}
				CopyConfig.setLastDir(lastDir);
				
				System.out.println("分配完成！");
				
			}
		});
		thread.start();
	}
	/*
	 * 	获取所有没有被复制的文件
	 */
	private static File[] getNoCopyFiles() {
		try {
			File file=new File(CopyConfig.getRecordFile());
			if(!file.exists()) {
				return new File(CopyConfig.getSrcDir()).listFiles();
			}else {
				FileReader reader=new FileReader(file);
				BufferedReader br=new BufferedReader(reader);
				List<String> filelist=new ArrayList<>();
				String readLine = br.readLine();
				while(readLine!=null) {
					filelist.add(readLine);
					readLine = br.readLine();
				}
				file.list(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return !filelist.contains(name);
					}
				});
			}
		} catch (FileNotFoundException e) {
			return new File(CopyConfig.getSrcDir()).listFiles();
		} catch (Exception e) {
			return new File(CopyConfig.getSrcDir()).listFiles();
		}
		return new File(CopyConfig.getSrcDir()).listFiles();
	}
}
