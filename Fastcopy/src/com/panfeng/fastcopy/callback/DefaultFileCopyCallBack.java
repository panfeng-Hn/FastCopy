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
	public synchronized void afterCopyFile(File srcFile, File dstfile) {
		try {
			//先将已经下载的文件数量加一
			CopyConfig.addRunNumber();
			File file=new File(CopyConfig.getRecordFile());
			Writer reader=new FileWriter(file);
			//写记录文件
			BufferedWriter br=new BufferedWriter(reader);
			br.write(dstfile.getName());
			//判断是否要将本目录的文件移动到另一个目录
			File parentFile = dstfile.getParentFile();
			String[] list = parentFile.list();
			int fileNum= list.length;
			//java 中的renameTo方法移动同一个磁盘的文件并不是IO流操作。所以移动速度很快
			//后期考虑改成调用本地 mv  move命令来直接操作
			if(fileNum>=CopyConfig.getPageDirNum()) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (String filename : list) {
							File last=new File(CopyConfig.getDstDir());
							dstfile.renameTo(last);
						}
					}
				}).start();;
				parentFile.deleteOnExit();
			}
			System.out.println(srcFile.getName()+"复制完成");
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
