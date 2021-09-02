package com.ky.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.function.Consumer;

public class Files {
	/**
	 * 搜索dir目录下的所有文件、目录（文件、目录）
	 * @param dir
	 * @param operation 将搜索出来的东西执行某种操作
	 */
	public static void search(File dir, Consumer<File> operation) {
		if (dir == null || operation == null) return;
		if (!dir.exists() || dir.isFile()) return;
		
		File[] subFiles = dir.listFiles();
		for (File sf : subFiles) {
			operation.accept(sf);
			if (sf.isDirectory()) search(sf, operation);
		}
	}
	
	/**
	 *	创建父目录 
	 * @param file
	 */
	private static void mkparents(File file) {
		File parent = file.getParentFile();
		if (parent == null) return;
		parent.mkdirs();
	}
	
	/**
	 * 仅能复制文件（文件）
	 * @param src
	 * @param dest
	 */
	public static void copy(File src, File dest) {
		if (src == null || dest == null) return;
		if (!src.exists() || dest.exists()) return;
		if (src.isDirectory()) return; 
		mkparents(dest);
		
		int len;
		try (InputStream is = new BufferedInputStream(new FileInputStream(src)); 
			OutputStream os = new BufferedOutputStream(new FileOutputStream(dest))
		) {
			byte[] data = new byte[8192];
			while ((len = is.read(data)) != -1) {
				os.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  修改文本的编码格式（文件）
	 *  支持的编码方式：Java支持的，都可以
	 *  常用的编码方式：UTF-8、GB2312、GBK、ASCII、ISO-8859-1
	 * @param src 源文件
	 * @param srcCoding 
	 * @param dest 目标文件
	 * @param destCoding 
	 */
	public static 
	void codingConverter(File src, String srcCoding,
						File dest, String destCoding)
	{
		if (src == null || dest == null 
		 || srcCoding == null || destCoding == null) return;
		if (!src.exists() || dest.exists()) return;
		if (src.isDirectory()) return;
		mkparents(dest);
 		
		try (
				// 将文件中的内容以srcCoding编码方式输入到内存中
				FileInputStream fis = new FileInputStream(src);
				InputStreamReader isr = new InputStreamReader(fis, srcCoding);
				BufferedReader br = new BufferedReader(isr);
				
				// 将内存中的内容以destCoding编码输出到文件中
				FileOutputStream fos = new FileOutputStream(dest);
				OutputStreamWriter osw = new OutputStreamWriter(fos, destCoding);
				BufferedWriter bw = new BufferedWriter(osw);
		) {
			char[] chars = new char[8192];
			int len;
			try {
				while ((len = br.read(chars)) != -1) {
					bw.write(chars, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
