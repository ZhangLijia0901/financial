package ext.spring.bean;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import ext.scan.util.ClassUtils;

public class DefaultBeanFactory implements BeanFactory {

	private BeanFactory beanFactory;

	public DefaultBeanFactory() {
		this.beanFactory = new BeanFactoryImpl();
	}

	@Override
	public Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}

	@Override
	public <T> T getBean(String beanName, Class<T> clazz) {
		return this.beanFactory.getBean(beanName, clazz);
	}

	@Override
	public void registerBean(String beanName, Object obj) {
		this.beanFactory.registerBean(beanName, obj);
	}

	@Override
	public void addClass(String beanName, Class<?> clazz) {
		this.beanFactory.addClass(beanName, clazz);
	}

	@Override
	public Set<String> getBeanNames() {
		return this.beanFactory.getBeanNames();
	}

	@Override
	public List<BeanProcessor> getBeanProcessors() {
		return this.beanFactory.getBeanProcessors();
	}

	@Override
	public void addBeanProcessor(BeanProcessor beanProcessor) {
		this.beanFactory.addBeanProcessor(beanProcessor);
	}

	public void scan(String... packageNames) {
		for (String packageName : packageNames) {
			this.scan(packageName);
		}

		// 初始化bean
		this.getBeanNames().forEach((beanName) -> {
			Object bean = this.getBean(beanName);
			this.initBean(beanName, bean);
		});

	}

	private void initBean(String beanName, Object bean) {
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			Autowire autowire = BeanFactory.getAnnotation(field, Autowire.class);
			if (autowire == null)
				continue;

			String depBeanName = autowire.vlaue();
			if (depBeanName == null || depBeanName.length() == 0)
				depBeanName = field.getName();

			Object depBean = this.getBean(depBeanName);
			this.initBean(depBeanName, depBean);

			field.setAccessible(true);
			try {
				field.set(bean, depBean);
			} catch (Exception e) {
				throw new RuntimeException("属性设置失败");
			}
		}

		this.getBeanProcessors().forEach((beanProcessor) -> {
			beanProcessor.processor(beanName, bean);
		});
	}

	private void scan(String packageName) {
		List<Class<?>> classes = ClassUtils.getClasses(packageName);

		classes.forEach((clazz) -> {
			Component component = BeanFactory.getAnnotation(clazz, Component.class);
			if (component == null)
				return;

			String beanName = component.value();
			if (beanName == null || beanName.length() == 0)
				beanName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);

			this.addClass(beanName, clazz);
		});

	}

}
