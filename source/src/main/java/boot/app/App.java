package boot.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ext.ratelimiter.annotation.RateLimiter;
import ext.ratelimiter.aop.LimiterAop;

@SpringBootApplication(scanBasePackageClasses = { LimiterAop.class, ExceptionControllerAdvice.class })
@RestController
@RateLimiter(0.5)
public class App {

	@Value("${server.port:8080}")
	private String port;

	static void createThreadTool() {
		ExecutorService service = null;
//		service = Executors.newCachedThreadPool();
//		service = Executors.newFixedThreadPool(3);// 固定创建的线程数
//		service = Executors.newScheduledThreadPool(3); // 可定时线程池, 核心线程数3
//		service = Executors.newSingleThreadExecutor(); // 单线程池
		service = new ThreadPoolExecutor(1, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(3));
		for (int i = 1; i <= 6; i++) {
			final int temp = i;
			service.execute(() -> {
				System.err.println(Thread.currentThread().getName() + ", i=" + temp);
			});
		}
	}

	public static void main(String[] args) throws Exception {
//		SpringApplication.run(App.class, args);

		createThreadTool();

	}

	@GetMapping("/")
	public String index() {
		return "boot: " + port;
	}

//	RateLimiter limiter = RateLimiter.create(1);

//	@GetMapping("/order")
//	public String order() {
//		double acquire = limiter.acquire();
//		System.err.println("获取令牌消耗时间: " + acquire);

//		boolean tryAcquire = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);
//		if (!tryAcquire)
//			System.err.println("获取令牌超时...");
//		else
//			System.err.println("取到令牌");
//
//		return "SUCCESS";
//	}

	@RateLimiter(1)
	@GetMapping("/order")
	public String xx() {
		System.err.println("取到令牌" + System.currentTimeMillis());
		return "SUCCESS";
	}

}
