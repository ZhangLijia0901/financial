package ext.spring.mvc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import ext.spring.bean.BeanFactory;
import ext.spring.bean.DefaultBeanFactory;

@WebServlet(initParams = { @WebInitParam(name = DispatcherServlet.SCAN_PACKAGE_NAME, value = "") })
public class DispatcherServlet extends HttpServlet {

	public final static String SCAN_PACKAGE_NAME = "scanPackageName";

	DefaultBeanFactory beanFactory = new DefaultBeanFactory();
	RquestMappingHandler mappingInfo;
	//
	private static final long serialVersionUID = -6957766680041494290L;

	@Override
	public void init() throws ServletException {
		super.init();

		String scanPckageName = this.getInitParameter(SCAN_PACKAGE_NAME);
		this.beanFactory.scan(scanPckageName);
		mappingInfo = new RquestMappingHandler(beanFactory);

	}
}
