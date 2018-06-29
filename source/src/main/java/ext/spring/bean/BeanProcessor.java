package ext.spring.bean;

public interface BeanProcessor {

	Object processor(String beanName, Object bean);
}
