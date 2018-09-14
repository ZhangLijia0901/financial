package com.financial.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.financial.common.Constants;

public class CharacterEncodingFilter extends BaseFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(Constants.CHARSET_NAME);
		response.setCharacterEncoding(Constants.CHARSET_NAME);
		response.setContentType("text/html;charset=utf-8");

		chain.doFilter(request, response);
	}

}
