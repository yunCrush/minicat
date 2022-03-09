package server;


/**
 * Author: yunCrush
 * Date: 2022/3/7 14:05
 * Description:
 */
public abstract class HttpServlet implements Servlet {

	public abstract void doGet(Request request, Response response);

	public abstract void doPost(Request request, Response response);

	@Override
	public void service(Request request, Response response) throws Exception {
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			doGet(request, response);
		} else {
			doPost(request, response);
		}
	}
}
