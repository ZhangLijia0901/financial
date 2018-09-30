package com.financial.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@WebFilter(urlPatterns = { "/*" })
public class XSSFilter extends BaseFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new XSSHttpServletRequestWrapper((HttpServletRequest) request), response);
	}

}

@Slf4j
class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private HttpServletRequest request;

	public XSSHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public String getParameter(String name) {
		String val = request.getParameter(name);

		if (StringUtils.isEmpty(val))
			return val;

		@SuppressWarnings("deprecation")
		String newVal = StringEscapeUtils.escapeHtml4(val);
		log.info("修改前—: [{}], 修改后—: [{}]", val, newVal);

		return newVal;
	}

}
