package com.panfeng.fastcopy;

import java.io.StringWriter;

public class Service {

	
	public static String getHelp() {
		StringWriter wr=new StringWriter();
		wr.write("�÷���\nFastCopy src  dst [��ѡ����] ");
		wr.write('\n');
		wr.write(" (����)src��ԴĿ¼��������Ŀ¼���ɶ�");
		wr.write('\n');
		wr.write(" (����)dst��Ŀ��Ŀ¼��������Ŀ¼����д");
		wr.write('\n');
		wr.write(" [��ѡ����]��");
		wr.write('\n');
		wr.write("	-record �ļ����Ƽ�¼�ļ���λ�ã�Ĭ����Ŀ��Ŀ¼");
		wr.write('\n');
		wr.write("	-threadnum ͬʱ��������߳�������Ĭ��Ϊcpu������");
		wr.write('\n');
		wr.write("	-pageDirNum ÿ����ҳ�ļ�������ļ�������Ĭ��Ϊ5000");
		return wr.getBuffer().toString();
	}
	
	
	/**
	 * ��ӡ����̨����
	 */
	public static void PrintHelp() {
		System.out.println(getHelp());
	}
	
	public static void main(String[] args) {
		PrintHelp();
	}
	
}
