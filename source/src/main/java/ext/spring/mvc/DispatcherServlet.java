package ext.spring.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import ext.spring.bean.DefaultBeanFactory;
import ext.spring.mvc.RquestMappingHandler.Handler;

@WebServlet(urlPatterns = { "/*" }, initParams = {
		@WebInitParam(name = DispatcherServlet.SCAN_PACKAGE_NAME, value = "ext.test") })
public class DispatcherServlet extends HttpServlet {

	public final static String SCAN_PACKAGE_NAME = "scanPackageName";

	DefaultBeanFactory beanFactory = new DefaultBeanFactory();
	RquestMappingHandler mappingInfo;
	//
	private static final long serialVersionUID = -6957766680041494290L;

	public DispatcherServlet() {

		System.out.println("wo");
	}

	@Override
	public void init() throws ServletException {

		String scanPckageName = this.getInitParameter(SCAN_PACKAGE_NAME);
		this.beanFactory.scan(scanPckageName);
		mappingInfo = new RquestMappingHandler(beanFactory);

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.service(req, resp);
		// 查找handler
		// 执行handler
		// 解析视图
		PrintWriter writer = resp.getWriter();
		Handler handler = mappingInfo.getHandler(req.getRequestURI());
		if (handler == null) {
			writer.write(String.format("Not find path %s", req.getRequestURI()));
			return;
		}
		Object object = handler.invoke(req, resp);

		if (object == null) {
			return;
		}
		writer.write(JSON.toJSONString(object));
		writer.flush();
		writer.close();

	}
}
