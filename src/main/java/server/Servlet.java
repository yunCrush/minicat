package server;

/**
 * Author: yunCrush
 * Date: 2022/3/7 14:03
 * Description:
 */
public interface Servlet {
	void init() throws Exception;

	void destory() throws Exception;

	void service(Request request, Response response) throws Exception;
}
