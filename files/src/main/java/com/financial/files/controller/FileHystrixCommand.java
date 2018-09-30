//package com.financial.files.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.financial.common.bean.response.CommonResponse;
//import com.financial.files.service.FileTreeSerice;
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.HystrixCommandGroupKey;
//import com.netflix.hystrix.HystrixCommandKey;
//import com.netflix.hystrix.HystrixCommandProperties;
//import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
//import com.netflix.hystrix.HystrixThreadPoolKey;
//import com.netflix.hystrix.HystrixThreadPoolProperties;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class FileHystrixCommand extends HystrixCommand<String> {
//
//	private FileTreeSerice fileTreeSerice;
//
//	public FileHystrixCommand(FileTreeSerice fileTreeSerice) {
//		super(serter());
//		this.fileTreeSerice = fileTreeSerice;
//	}
//
//	public static Setter serter() {
//
//		HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("files");// 命令服务分组名称
//		HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("file");// 命令服务名称
//		HystrixThreadPoolKey threadPoolKey = HystrixThreadPoolKey.Factory.asKey("file-pool");// 线程池名称
//
//		// 线程池属性配置 线程大小10、线程存活时长15、队列等待的阈值为100, 超过100执行拒绝策略 配置服务熔断
//		HystrixThreadPoolProperties.Setter threadPoolSetter = HystrixThreadPoolProperties.Setter().withCoreSize(10)
//				.withKeepAliveTimeMinutes(15).withQueueSizeRejectionThreshold(100);
//
//		// 命令属性配置 采用线程方式实现服务隔离 禁止超时
//		HystrixCommandProperties.Setter commandSetter = HystrixCommandProperties.Setter()
//				.withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD).withExecutionTimeoutEnabled(false);
//
//		return Setter.withGroupKey(groupKey).andCommandKey(commandKey).andThreadPoolKey(threadPoolKey)
//				.andThreadPoolPropertiesDefaults(threadPoolSetter).andCommandPropertiesDefaults(commandSetter);
//
//	}
//
//	@Override
//	protected String getFallback() {
//		log.error("进入了Hystrix的失败回调函数");
//		return "调用服务失败了";
//	}
//
//	@Override
//	protected String run() throws Exception {
//		long startTime = System.currentTimeMillis();
//
//		CommonResponse commonResponse = fileTreeSerice.getFileListByRootPath("F:/files", "");
//
//		try {
//			Thread.sleep(1500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		long endTime = System.currentTimeMillis();
//		log.info("耗时： [{} ms]", (endTime - startTime));
//
//		return JSON.toJSONString(commonResponse);
//
//	}
//
//}
