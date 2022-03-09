package server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: yunCrush
 * Date: 2022/3/7 10:35
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
	// 请求⽅式，⽐如GET/POST
	private String method;
	// 例如 /,/index.html
	private String url;
	// 输⼊流，其他属性从输⼊流中解析出来
	private InputStream inputStream;
	// 构造器，输⼊流传⼊

	public Request(InputStream inputStream) throws IOException {
		this.inputStream = inputStream;
		// 从输⼊流中获取请求信息
		int count = 0;
		while (count == 0) {
			// 请求到了，但是数据并没有发送过来
			count = inputStream.available();
		}
		byte[] bytes = new byte[count];
		inputStream.read(bytes);
		String inputStr = new String(bytes);
		// 获取第⼀⾏请求头信息 // GET / HTTP/1.1
		String firstLineStr = inputStr.split("\\n")[0];
		String[] strings = firstLineStr.split(" ");
		this.method = strings[0];
		this.url = strings[1];
		System.out.println("============>method: " + method);
		System.out.println("==============>url: " + url);
	}
}
