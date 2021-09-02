import com.ky.tools.Files;
import java.io.File;
import java.util.function.Consumer;

public class Main {
	public static void main(String[] args) {
		Files.search(
				new File("D:\\谭浩强  C语言程序设计"),
				new Consumer<File>() {
					@Override
					public void accept(File t) {
						if (t.getName().contains(".cpp")) { // 匹配项修改编码方式
							Files.codingConverter(
								t,
								"GB2312",
								new File(t.getAbsolutePath().replaceFirst("D:\\\\谭浩强  C语言程序设计", "D:\\dest")),
								"UTF-8"
							);
						} else { // 不匹配项直接复制，不需要进行任何修改
							Files.copy(
								t,
								new File(t.getAbsolutePath().replaceFirst("D:\\\\谭浩强  C语言程序设计", "D:\\dest"))
							);
						}
					}
				});
	}
}
