package com.financial.common.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 全局日志
 * 
 * @author: 张礼佳
 * @date: 2018年8月28日 下午2:15:32
 */
@Aspect
@Slf4j
public class GlobalLog {

	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)||@annotation(org.springframework.web.bind.annotation.GetMapping)||@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public Object around(ProceedingJoinPoint point) {
		long startTime = System.currentTimeMillis();// 记录开始执行时间

		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		log.info("=============== 请求Req ===============");
		try {
			// 打印请求内容
			log.info("req-url: {}", request.getRequestURL().toString());
			log.info("req-method: {}", request.getMethod());
			log.info("controller-method: {}", point.getSignature());
			log.info("controller-method-args: {} ", Arrays.toString(point.getArgs()));
		} catch (Exception e) {
			log.error("获取请求内容异常:", e);
		}
		log.info("=============== 请求Req ===============");

		Object result = null;
		try {
			result = point.proceed();
		} catch (Throwable e) {
			log.error("执行控制器方法异常", e);
		}

		log.info("=============== 响应Resp ===============");
		log.info(JSON.toJSONString(result));
		log.info("=============== 响应Resp ===============");

		long endTime = System.currentTimeMillis();// 记录执行结束时间
		log.info("=============== 耗时consume-time: [{}]ms ===============", (endTime - startTime));

		return result;
	}

}
