package com.panfeng.fastcopy;

import java.io.File;
import java.util.Properties;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		
		
//		Properties properties = System.getProperties();
//		Set<Object> keySet = properties.keySet();
//		for (Object object : keySet) {
//			System.out.println(object+"--------->"+properties.getProperty((String)object));
//			
//		}
//		int availableProcessors = Runtime.getRuntime().availableProcessors();
//		System.out.println(Runtime.getRuntime().maxMemory()/1024/1024);
	
		
		File[] listRoots = File.listRoots();
		for (File file : listRoots) {
			System.out.println(file.getFreeSpace()/1024/1024/1024);
		}
		
		
		
		
	}

}
