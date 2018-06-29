package ext.spring.mvc;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import ext.spring.bean.BeanFactory;

public class RquestMappingHandler {

	private BeanFactory beanFactory;

	private ConcurrentHashMap<String, RequestMappingInfo> requestMappings = new ConcurrentHashMap<>();

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
			
			
		});
	}

	boolean isHandler(Object handler) {
		if (BeanFactory.getAnnotation(handler.getClass(), Controller.class) != null
				|| BeanFactory.getAnnotation(handler.getClass(), RequestMapping.class) != null)
			return true;

		return false;

	}

	public static class RequestMappingInfo {

		private Object bean;
		private Method method;

		public RequestMappingInfo(Object bean, Method method) {
			this.bean = bean;
			this.method = method;
		}

	}

}
