package com.financial.common.configuration.auto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import com.financial.common.aop.GlobalLog;
import com.financial.common.configuration.FastjsonConfig;
import com.financial.common.configuration.MobileConfig;
import com.financial.common.configuration.RedisService;
import com.financial.common.configuration.auto.CustomConfiguration.LogConfigurationImportSelector;
import com.financial.common.configuration.auto.CustomConfiguration.NecessaryImportSelector;

/** 自定义配置 */
@Configuration
@Import({ LogConfigurationImportSelector.class, NecessaryImportSelector.class

})
public class CustomConfiguration {
	private final static String CUSTOM_CONTROLLERLOG_ENABLE = "custom.controllerLog.enable";

	/** 必要的配置导入 */
	public static class NecessaryImportSelector implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			List<String> classList = new ArrayList<>();
			classList.add(MobileConfig.class.getName());// 移动端过滤配置
			classList.add(FastjsonConfig.class.getName());// fastJson配置
			classList.add(RedisService.class.getName());// redis配置

			String[] beanClass = new String[classList.size()];
			classList.toArray(beanClass);
			return beanClass;
		}

	}

	/** 全局日志配置 */
	public static class LogConfigurationImportSelector implements ImportSelector, EnvironmentAware {
		private Environment environment;

		@Override
		public void setEnvironment(Environment environment) {
			this.environment = environment;
		}

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			String enable = environment.getProperty(CUSTOM_CONTROLLERLOG_ENABLE);
			return "true".equals(enable) ? new String[] { GlobalLog.class.getName() } : new String[0];
		}

	}
}
