package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Author: yunCrush
 * Date: 2022/3/7 13:25
 * Description:
 */
public class StaticResourceUtil {
	/**
	 * 获取静态资源⽂件的绝对路径
	 *
	 * @param path
	 * @return
	 */
	public static String getAbsolutePath(String path) {
		String absolutePath =
				StaticResourceUtil.class.getResource("/").getPath();
		return absolutePath.replaceAll("\\\\", "/") + path;
	}

	/**
	 * 读取静态资源⽂件输⼊流，通过输出流输出
	 */
	public static void outputStaticResource(InputStream inputStream,
											OutputStream outputStream) throws IOException {
		int count = 0;
		while (count == 0) {
			count = inputStream.available();
		}
		int resourceSize = count;
		// 输出http请求头,然后再输出具体内容
		outputStream.write(HttpProtocolUtil.getHttpHeader200(resourceSize).getBytes());
		// 读取内容输出
		long written = 0;// 已经读取的内容⻓度
		int byteSize = 1024; // 计划每次缓冲的⻓度
		byte[] bytes = new byte[byteSize];
		while (written < resourceSize) {
			// 说明剩余未读取⼤⼩不⾜⼀个1024⻓度，那就按真实⻓度处理
			if (written + byteSize > resourceSize) {
				// 剩余的⽂件内容長度
				byteSize = (int) (resourceSize - written);
				bytes = new byte[byteSize];
			}
			inputStream.read(bytes);
			outputStream.write(bytes);
			outputStream.flush();
			written += byteSize;
		}
	}
}
