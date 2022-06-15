package com.ky.tools;
import java.io.File;
import java.util.function.Consumer;

// 请修改 sourcePath 和 destPath 两个字符串的内容
// 可以直接将路径复制到 sourcePath 和 destPath 中
public class Main {
	public static void main(String[] args) {
		String sourcePath = "C:\\tmp\\修改";
		String destPath = "C:\\tmp\\修改后";
		String sourceByReg = sourcePath.replaceAll("\\\\", "\\\\\\\\");
		String destByReg = destPath.replaceAll("\\\\", "\\\\\\\\");
		Files.search(
				new File(sourcePath),
				new Consumer<File>() {
					@Override
					public void accept(File t) {
						if (t.getName().contains(".cpp")) { // 匹配项修改编码方式
							Files.codingConverter(
								t,
								"GB2312",
								new File(t.getAbsolutePath().replaceFirst(sourceByReg, destByReg)),
								"UTF-8"
							);
						} else { // 不匹配项直接复制，不需要进行任何修改
							Files.copy(
								t,
								new File(t.getAbsolutePath().replaceFirst(sourceByReg, destByReg))
							);
						}
					}
				});
		System.out.println("程序运行完毕");
	}
}
