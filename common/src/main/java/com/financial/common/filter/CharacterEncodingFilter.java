package com.financial.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.financial.common.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CharacterEncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("初始化[{}] 过滤器", this.getClass().getName());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(Constants.CHARSET_NAME);
		response.setCharacterEncoding(Constants.CHARSET_NAME);

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		log.info("销毁 [{}] 过滤器", this.getClass().getName());
	}

}
