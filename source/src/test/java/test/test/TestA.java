package test.test;

import ext.spring.bean.Autowire;
import ext.spring.bean.Component;

@Component("testA")
public class TestA {

	@Autowire
	TestB testB;
}
