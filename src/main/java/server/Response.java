package server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: yunCrush
 * Date: 2022/3/7 13:22
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	private OutputStream outputStream;

	// 使⽤输出流输出指定字符串
	public void output(String content) throws IOException {
		outputStream.write(content.getBytes());
	}

	/**
	 * @param path url，随后要根据url来获取到静态资源的绝对路径，进⼀步根据绝对路径
	 *             读取该静态资源⽂件，最终通过
	 *             输出流输出
	 *             /-----> classes
	 */
	public void outputHtml(String path) throws IOException {
		// 获取静态资源⽂件的绝对路径
		String absoluteResourcePath =
				StaticResourceUtil.getAbsolutePath(path);
		// 输⼊静态资源⽂件
		File file = new File(absoluteResourcePath);
		if (file.exists() && file.isFile()) {
			// 读取静态资源⽂件，输出静态资源
			StaticResourceUtil.outputStaticResource(new
					FileInputStream(file), outputStream);
		} else {
			// 输出404
			output(HttpProtocolUtil.getHttpHeader404());
		}
	}
}
