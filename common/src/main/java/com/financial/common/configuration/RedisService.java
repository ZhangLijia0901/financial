package com.financial.common.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

@Configuration
public class RedisService {

	@Bean
	@ConditionalOnBean(name = "stringRedisTemplate")
	public RedisService redisService() {
		return new RedisService();
	}

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public <T> T get(String key, String hashKey, Class<T> clazz) {
		Object object = stringRedisTemplate.opsForHash().get(key, hashKey);
		if (StringUtils.isEmpty(object))
			return null;
		return JSON.parseObject(object.toString(), clazz);
	}

	public void set(String key, String hashKey, Object val) {
		set(key, hashKey, val, 30L);
	}

	public void set(String key, String hashKey, Object val, Long timeout) {
		stringRedisTemplate.opsForHash().put(key, hashKey, JSON.toJSONString(val));
		if (timeout > 0) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.MINUTES);
		}
	}

	public Boolean remvoe(String key) {
		return stringRedisTemplate.delete(key);
	}

	public void set(String key, Object val) {
		stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(val));
	}

	/** 获取redis数据 */
	public <T> T get(String key, Class<T> clazz) {
		String val = stringRedisTemplate.opsForValue().get(key);
		if (StringUtils.isEmpty(val))
			return null;
		return JSON.parseObject(val, clazz);
	}

}
