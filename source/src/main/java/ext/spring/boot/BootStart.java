package ext.spring.boot;

import javax.servlet.http.HttpServlet;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.ContainerBase;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.valves.AccessLogValve;

import ext.spring.mvc.DispatcherServlet;

public class BootStart {

	public static class BootTomcat {

		Tomcat tomcat;

		public BootTomcat() {
			AccessLogValve accessLogValve = new AccessLogValve();
			accessLogValve.setDirectory("logs");
			accessLogValve.setPattern("%a %t \"%r\" %s %b \"%{Referer}i\" \"%{User-Agent}i\" ");

			this.tomcat = new Tomcat();
			tomcat.setHostname("www.test1.com");
			((ContainerBase) tomcat.getEngine()).addValve(accessLogValve);

			StandardWrapper wrapper = new StandardWrapper();
			wrapper.setServletClass(DispatcherServlet.class.getName());
			wrapper.setName("DispatcherServlet");

			addServlet(tomcat.addContext("/menu", "F:/files"), "/", DefaultServlet.class).addInitParameter("debug",
					"11");

			addServlet(tomcat.addContext("/", null), "/", DispatcherServlet.class)
					.addInitParameter(DispatcherServlet.SCAN_PACKAGE_NAME, "ext.test");

			// ----------------------------------------------------------------//
			// ----------------------------------------------------------------//
			Host host = addHost(tomcat.getService().getContainer(), "www.test2.com");
			addServlet(tomcat.addContext(host, "/", "D:/logs"), "/", DefaultServlet.class).addInitParameter("debug",
					"11");

		}

		private Host addHost(Engine engine, String name) {
			Host host = new StandardHost();
			host.setName(name);

			engine.addChild(host);

			return host;
		}

		private Wrapper addServlet(Context context, String pattern, Class<? extends HttpServlet> clazz) {
			if (context == null)
				throw new NullPointerException("context is null");
			Wrapper wrapper = new StandardWrapper();
			wrapper.setServletClass(clazz.getName());
			wrapper.setName(clazz.getName());
			context.addChild(wrapper);
			context.addServletMappingDecoded(pattern, clazz.getName());

			return wrapper;
		}

		public void start() throws LifecycleException {
			tomcat.start();
			tomcat.getServer().await();
		}
	}




}
