package ext.spring.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface BeanFactory {

	Object getBean(String beanName);

	<T> T getBean(String beanName, Class<T> clazz);

	void registerBean(String beanName, Object obj);

	void addClass(String beanName, Class<?> clazz);

	Set<String> getBeanNames();

	List<BeanProcessor> getBeanProcessors();

	void addBeanProcessor(BeanProcessor beanProcessor);

	public static <T extends Annotation> T getAnnotation(AnnotatedElement annotatedElement, Class<T> annotationType) {
		T ann = annotatedElement.getAnnotation(annotationType);
		if (ann != null)
			return ann;
		for (Annotation annotation : annotatedElement.getAnnotations()) {

			if (annotation.annotationType().getName().startsWith("java.lang.annotation"))
				continue;
			ann = getAnnotation(annotation.annotationType(), annotationType);
			if (ann != null)
				return ann;
		}

		return null;

	}

	public static class NotBeanException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public NotBeanException(String msg) {
			super(msg);
		}
	}

	public static class BeanNameException extends RuntimeException {
		//
		private static final long serialVersionUID = 1L;

		public BeanNameException(String msg) {
			super(msg);
		}

	}

}

class BeanFactoryImpl implements BeanFactory {

	ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>(128);
	ConcurrentHashMap<String, Class<?>> classes = new ConcurrentHashMap<>();
	Set<String> beanNames = new HashSet<>();

	List<BeanProcessor> beanProcessors = new ArrayList<>(5);

	@Override
	public Object getBean(String beanName) {
		Object bean = beans.get(beanName);
		if (bean == null)
			bean = doGetBean(beanName);
		return bean;
	}

	private Object doGetBean(String beanName) {

		if (beans.containsKey(beanName))
			return beans.get(beanName);
		if (!classes.containsKey(beanName))
			throw new NotBeanException(beanName + " 不存在");

		Class<?> clazz = classes.get(beanName);

		Object bean = null;
		try {
			bean = clazz.getConstructor().newInstance();
		} catch (Exception e) {
			throw new NotBeanException(beanName + " 创建实例失败");
		}
		if (bean == null)
			throw new NotBeanException(beanName + " 创建实例失败");

		System.out.println(MessageFormat.format("register beanName: [{0}], class: [{1}]", beanName, clazz));

		synchronized (beans) {
			if (beans.containsKey(beanName))
				return beans.get(beanName);
			this.beans.put(beanName, bean);
			if (bean instanceof BeanProcessor)
				this.beanProcessors.add((BeanProcessor) bean);
		}

		classes.remove(beanName);

		return bean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String beanName, Class<T> clazz) {
		Object bean = getBean(beanName);
		if (clazz.isAssignableFrom(bean.getClass()))
			return (T) bean;
		throw new ClassCastException(bean.getClass() + " 转换 " + clazz + " 异常");
	}

	@Override
	public void registerBean(String beanName, Object bean) {

		synchronized (beans) {
			if (beans.containsKey(beanName))
				throw new BeanNameException(String.format("beanName: [%s] 已经存在", beanName));
			beans.put(beanName, bean);
		}

	}

	@Override
	public void addClass(String beanName, Class<?> clazz) {
		this.classes.put(beanName, clazz);
		this.beanNames.add(beanName);
	}

	@Override
	public Set<String> getBeanNames() {
		return this.beanNames;
	}

	@Override
	public List<BeanProcessor> getBeanProcessors() {
		return this.beanProcessors;
	}

	@Override
	public void addBeanProcessor(BeanProcessor beanProcessor) {
		this.beanProcessors.add(beanProcessor);
	}

}
