package com.financial.common.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("初始化Filter: {}", this.getClass().getName());
	}

	@Override
	public void destroy() {
		log.info("销毁Filter: {}", this.getClass().getName());
	}

}
