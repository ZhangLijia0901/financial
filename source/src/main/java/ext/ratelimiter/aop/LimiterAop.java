package ext.ratelimiter.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import ext.ratelimiter.annotation.RateLimiter;

/**
 * 
 * 令牌桶限流注解实现
 * 
 * @author: 张礼佳
 * @date: 2018年7月30日 下午3:10:25
 */
@Aspect // 定义切面
@Component
public class LimiterAop {

	Map<String, Object> limiters;
	Class<?> limitClass;

	public LimiterAop() {

		try {
			limitClass = Class.forName("com.google.common.util.concurrent.RateLimiter");
			limiters = new ConcurrentHashMap<>();
		} catch (ClassNotFoundException e) {

			limitClass = null;
		}
	}

	// 定义切入点
	@Pointcut(value = "execution(public * boot.app.*.*(..))")
	public void limiterAop() {

	}

	@Around("limiterAop()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		if (limitClass == null)
			return proceedingJoinPoint.proceed();

		Method method = getMethod(proceedingJoinPoint);
		if (method == null) {
			return null;
		}
		RateLimiter limiter = method.getDeclaredAnnotation(RateLimiter.class);
		if (limiter == null)
			limiter = method.getDeclaringClass().getDeclaredAnnotation(RateLimiter.class);

		if (limiter != null) {
			if (!tryAcquire(method.toString(), limiter))// 服务降级
				throw new RuntimeException("ERROR： 未获取到令牌");
//				fallback();

		}

		// 获取类|方法上存在 @RateLimiter
		// 存在注解-获取注解参数-创建令牌桶
		// 获取令牌

		return proceedingJoinPoint.proceed();
	}

//	private  fallback() {
//
//	}

	private boolean tryAcquire(String name, RateLimiter limiter) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object limiterObj = limiters.get(name);

		if (limiterObj == null) {
			limiterObj = limitClass.getDeclaredMethod("create", double.class).invoke(null, limiter.value());
			limiters.put(name, limiterObj);
		}

		return (boolean) limitClass.getDeclaredMethod("tryAcquire", long.class, TimeUnit.class).invoke(limiterObj,
				limiter.timeout(), limiter.timeUnit());
	}

	private Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
		Signature signature = proceedingJoinPoint.getSignature();
		if (signature instanceof MethodSignature)
			return ((MethodSignature) signature).getMethod();// 获取执行方法
		return null;
	}

}
