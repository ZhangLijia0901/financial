package test.test;

import ext.spring.bean.Autowire;
import ext.spring.bean.Component;

@Component
public class TestB {

	@Autowire
	private TestC testC;

}
