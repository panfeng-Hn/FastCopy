package com.panfeng.fastcopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class CopyFile implements Runnable {

	private File src;
	private String dstDirectory;
	private String fileName;
	private FileCopyCallback callback;
	
	public CopyFile(File src, String dstDirectory, String fileName,FileCopyCallback callback) {
		super();
		this.src = src;
		this.dstDirectory = dstDirectory;
		this.fileName = fileName;
		this.callback = callback;
	}
	

	@Override
	public void run() {
		FileInputStream fin=null;
		FileOutputStream fos=null;
		FileChannel inch=null;
		FileChannel osch=null;
		try {
			File f=new File(dstDirectory);
			f=new File(dstDirectory,fileName);
			if(f.exists()) {
				callback.beforeDeleteOldFile(f);
				f.delete();
				callback.afterDeleteOldFile(f);
			}
			callback.beforeCreateNewFile(f);
			f.createNewFile();
			callback.afterCreateNewFile(f);
			fin=new FileInputStream(src);
			fos=new FileOutputStream(f);
			inch = fin.getChannel();
			osch = fos.getChannel();
			callback.beforeCopyFile(src, f);
			inch.transferTo(0, inch.size(), osch);
			callback.afterCopyFile(src, f);
		} catch (IOException e) {
			callback.onException(e);
		}finally{
			try {
				if(fin!=null) {
					fin.close();
				}
				if(fos!=null) {
					fos.close();
				}
				if(inch!=null) {
					inch.close();
				}
				if(osch!=null) {
					osch.close();
				}
				callback.onCloseOver();
			} catch (IOException e) {
				callback.onCloseException(e, fin,fos,inch,osch);
			}
		}
	}

}
