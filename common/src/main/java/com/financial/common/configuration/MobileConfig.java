package com.financial.common.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.DeviceWebArgumentResolver;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceWebArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

@Configuration
public class MobileConfig implements WebMvcConfigurer {

	// 添加参数解析器
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(deviceHandlerMethodArgumentResolver());
		resolvers.add(deviceWebArgumentResolverAdapter());
		resolvers.add(sitePreferenceWebArgumentResolverAdapter());
	}

	// 添加拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sitePreferenceHandlerInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(deviceResolverHandlerInterceptor()).addPathPatterns("/**");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
//		registry
	}

	/********************* 视图解析器 ********************/
//	@Bean
//	public LiteDeviceDelegatingViewResolver liteDeviceDelegatingViewResolver() {
//		LiteDeviceDelegatingViewResolver liteDeviceDelegatingViewResolver = new LiteDeviceDelegatingViewResolver(
//				internalResourceViewResolver()) {
//			@Override
//			public int getOrder() {
//				return Ordered.HIGHEST_PRECEDENCE;
//			}
//
//			@Override
//			protected String getDeviceViewNameInternal(String viewName) {
//				RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
//				Assert.isInstanceOf(ServletRequestAttributes.class, attrs);
//				HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
//				Device device = DeviceUtils.getCurrentDevice(request);
//				SitePreference sitePreference = SitePreferenceUtils.getCurrentSitePreference(request);
//				String resolvedViewName = viewName;
//				if (viewName.indexOf("?") == -1) {
//					if (ResolverUtils.isNormal(device, sitePreference))
//						resolvedViewName = getNormalPrefix() + viewName + getNormalSuffix();
//					else if (ResolverUtils.isMobile(device, sitePreference))
//						resolvedViewName = getMobilePrefix() + viewName + getMobileSuffix();
//					else if (ResolverUtils.isTablet(device, sitePreference))
//						resolvedViewName = getTabletPrefix() + viewName + getTabletSuffix();
//				} else {
//					String prefix = viewName.substring(0, viewName.lastIndexOf("?"));
//					String suffix = viewName.substring(viewName.lastIndexOf("?"));
//					if (ResolverUtils.isNormal(device, sitePreference))
//						resolvedViewName = getNormalPrefix() + prefix + getNormalSuffix() + suffix;
//					else if (ResolverUtils.isMobile(device, sitePreference))
//						resolvedViewName = getMobilePrefix() + prefix + getMobileSuffix() + suffix;
//					else if (ResolverUtils.isTablet(device, sitePreference))
//						resolvedViewName = getTabletPrefix() + prefix + getTabletSuffix() + suffix;
//				}
//
//				// MOBILE-63 "redirect:/" and "forward:/" can result in the view name containing
//				if (resolvedViewName.endsWith("//")) {
//					return resolvedViewName.substring(0, resolvedViewName.length() - 1);
//				}
//				return resolvedViewName;
//			}
//		};
//		liteDeviceDelegatingViewResolver.setEnableFallback(true);
//		liteDeviceDelegatingViewResolver.setMobilePrefix("/mobile");
//		liteDeviceDelegatingViewResolver.setNormalPrefix("/");
//		liteDeviceDelegatingViewResolver.setTabletPrefix("");// 平板前缀
//		return liteDeviceDelegatingViewResolver;
//	}

//	@Bean
//	public InternalResourceViewResolver internalResourceViewResolver() {
//		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
//		internalResourceViewResolver.setSuffix(".html");
//		return internalResourceViewResolver;
//	}

	/********************* 参数解析器 ********************/
	@Bean
	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
		return new DeviceHandlerMethodArgumentResolver();
	}

	@Bean
	public ServletWebArgumentResolverAdapter deviceWebArgumentResolverAdapter() {
		return new ServletWebArgumentResolverAdapter(deviceWebArgumentResolver());
	}

	@Bean
	public ServletWebArgumentResolverAdapter sitePreferenceWebArgumentResolverAdapter() {
		return new ServletWebArgumentResolverAdapter(sitePreferenceWebArgumentResolver());
	}

	@Bean
	public DeviceWebArgumentResolver deviceWebArgumentResolver() {
		return new DeviceWebArgumentResolver();
	}

	@Bean
	public SitePreferenceWebArgumentResolver sitePreferenceWebArgumentResolver() {
		return new SitePreferenceWebArgumentResolver();
	}

	/********************* 拦截器 ********************/
	@Bean
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
		return new DeviceResolverHandlerInterceptor();
	}

	@Bean
	public SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor() {
		return new SitePreferenceHandlerInterceptor();
	}

}
