package server;

import java.io.IOException;

/**
 * Author: yunCrush
 * Date: 2022/3/7 14:08
 * Description:
 */
public class MyServlet extends HttpServlet {
	@Override
	public void doGet(Request request, Response response) {
		try {
			// get请求逻辑
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String content = "<h1>MyServlet get</h1>";
		try {
			response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes()
					.length) + content));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(Request request, Response response) {
		String content = "<h1>MyServlet post</h1>";
		try {
			response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes()
					.length) + content));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws Exception {

	}

	@Override
	public void destory() throws Exception {

	}
}
