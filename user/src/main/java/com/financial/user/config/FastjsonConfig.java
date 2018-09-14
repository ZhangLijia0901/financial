package com.financial.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.financial.common.Constants;

@Configuration
public class FastjsonConfig {

	@Value("${" + Constants.FASTJSON_DATEFORMAT + "}")
	private String dateFormat;

	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat(dateFormat);
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);

		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		return fastJsonHttpMessageConverter;
	}

}
