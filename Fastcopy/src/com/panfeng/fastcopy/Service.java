package com.panfeng.fastcopy;

import java.io.StringWriter;

public class Service {

	
	public static String getHelp() {
		StringWriter wr=new StringWriter();
		wr.write("用法：\nFastCopy src  dst [可选参数] ");
		wr.write('\n');
		wr.write(" (必须)src：源目录，必须是目录并可读");
		wr.write('\n');
		wr.write(" (必须)dst：目标目录，必须是目录并可写");
		wr.write('\n');
		wr.write(" [可选参数]：");
		wr.write('\n');
		wr.write("	-record 文件复制记录文件的位置，默认在目标目录");
		wr.write('\n');
		wr.write("	-threadnum 同时运行最大线程数量，默认为cpu核心数");
		wr.write('\n');
		wr.write("	-pageDirNum 每个分页文件夹最大文件数量，默认为5000");
		return wr.getBuffer().toString();
	}
	
	
	/**
	 * 打印控制台帮助
	 */
	public static void PrintHelp() {
		System.out.println(getHelp());
	}
	
	public static void main(String[] args) {
		PrintHelp();
	}
	
}
