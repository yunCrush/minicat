package server;

import lombok.Data;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: yunCrush
 * Date:2022/3/7 0:07
 * Description: minicat 主类
 *  完成Minicat 3.0版本
 *  需求：可以请求动态资源（Servlet）
 */
@Data
public class Bootstrap {
	//定义socket监听的端⼝号
	private int port = 8080;
	private Map<String, HttpServlet> servletMap = new HashMap<>();

	public void start() throws Exception {
		// 加载解析相关的配置，web.xml
		loadServlet();
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("=======>Minicat start on port: " + port);
		while (true) {
			Socket socket = serverSocket.accept();
			InputStream inputStream = socket.getInputStream();
			// 封装Request对象和Response对象
			Request request = new Request(inputStream);
			Response response = new Response(socket.getOutputStream());
			// 静态资源处理
			if (servletMap.get(request.getUrl()) == null) {
				response.outputHtml(request.getUrl());
			} else {
				// 动态资源servlet请求
				HttpServlet httpServlet =
						servletMap.get(request.getUrl());
				httpServlet.service(request, response);
			}
			socket.close();
		}
	}

	/**
	 * 加载解析web.xml，初始化Servlet
	 */
	private void loadServlet() {
		System.out.println("開始加载servlet");
		InputStream resourceAsStream =
				this.getClass().getClassLoader().getResourceAsStream("web.xml");
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(resourceAsStream);
			Element rootElement = document.getRootElement();
			List<Element> selectNodes =
					rootElement.selectNodes("//servlet");
			for (int i = 0; i < selectNodes.size(); i++) {
				Element element = selectNodes.get(i);
				// <servlet-name>lagou</servlet-name>
				Element servletnameElement = (Element)
						element.selectSingleNode("servlet-name");
				String servletName = servletnameElement.getStringValue();
				// <servlet-class>server.LagouServlet</servlet-class>
				Element servletclassElement = (Element)
						element.selectSingleNode("servlet-class");
				String servletClass =
						servletclassElement.getStringValue();
				// 根据servlet-name的值找到url-pattern
				Element servletMapping = (Element)
						rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" +
								servletName + "']");
				// /lagou
				String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
				servletMap.put(urlPattern, (HttpServlet)
						Class.forName(servletClass).newInstance());
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
		try {
			System.out.println("============>minicat start:");
			bootstrap.start();
		} catch (Exception e) {
			e.printStackTrace();
			// log.info("IO exception：{}",e);
		}
	}
}
