package server;

/**
 * Author: yunCrush
 * Date: 2022/3/7 10:33
 * Description: http协议工具类
 */
public class HttpProtocolUtil {
	public static String getHttpHeader200(long contentLength) {
		return "HTTP/1.1 200 OK \n" +
				"Content-Type: text/html \n" +
				"Content-Length: " + contentLength + " \n" +
				"\r\n";
	}
	/**
	 * 为响应码404提供请求头信息(此处也包含了数据内容)
	 *
	 * @return
	 */
	public static String getHttpHeader404() {
		String str404 = "<h1 >404 not found</h1>";
		return "HTTP/1.1 404 NOT Found \n" +
				"Content-Type: text/html \n" +
				"Content-Length: " + str404.getBytes().length + " \n" +
				"\r\n" + str404;
	}
}
