package ext.spring.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ext.spring.bean.BeanFactory;

public class RquestMappingHandler {

	private BeanFactory beanFactory;

	private ConcurrentHashMap<String, Handler> handlers = new ConcurrentHashMap<>();

	public RquestMappingHandler(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
		init();
	}

	private void init() {
		beanFactory.getBeanNames().forEach((beanName) -> {
			Object bean = beanFactory.getBean(beanName);
			if (!isHandler(bean))
				return;

			RequestMapping requestMapping = BeanFactory.getAnnotation(bean.getClass(), RequestMapping.class);
			String clsMapping = null;
			if (requestMapping != null)
				clsMapping = requestMapping.value();

			Method[] methods = bean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				RequestMapping methodMapping = BeanFactory.getAnnotation(method, RequestMapping.class);
				if (methodMapping == null)
					continue;

				String mapping = "";
				if (clsMapping != null)
					mapping = clsMapping.startsWith("/") ? clsMapping.endsWith("/") ? clsMapping.substring(1)
							: clsMapping.substring(1, clsMapping.length() - 1) : clsMapping;
				mapping += methodMapping.value().startsWith("/") ? methodMapping.value() : "/" + methodMapping.value();
				Handler mappingInfo = new Handler(bean, method);

				handlers.put(mapping, mappingInfo);
			}

		});
	}

	boolean isHandler(Object handler) {
		if (BeanFactory.getAnnotation(handler.getClass(), Controller.class) != null
				|| BeanFactory.getAnnotation(handler.getClass(), RequestMapping.class) != null)
			return true;

		return false;

	}

	Handler getHandler(String uri) {
		return handlers.get(uri.startsWith("/") ? uri.substring(1) : uri);

	}

	public static class Handler {

		private Object bean;
		private Method method;
		private Class<?>[] paramTypes;

		public Handler(Object bean, Method method) {
			this.bean = bean;
			this.method = method;

			this.paramTypes = method.getParameterTypes();
		}

		public Object invoke(HttpServletRequest req, HttpServletResponse resp) {
			try {
				Object[] params = param(req, resp);
				if (params != null)
					return method.invoke(bean, params);
				return method.invoke(bean);
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			}

			return "Controller 调用失败";
		}

		private Object[] param(HttpServletRequest req, HttpServletResponse resp) {
			if (paramTypes == null || paramTypes.length == 0)
				return null;
			Object[] objs = new Object[paramTypes.length];

			for (int i = 0; i < paramTypes.length; i++) {
				Class<?> cls = paramTypes[i];
				if (cls.isAssignableFrom(HttpServletRequest.class))
					objs[i] = req;

				if (cls.isAssignableFrom(HttpServletResponse.class))
					objs[i] = resp;

				if (cls.isAssignableFrom(PrintWriter.class))
					try {
						objs[i] = resp.getWriter();
					} catch (IOException e) {
						objs[i] = null;
					}

			}

			return objs;
		}

	}

}
