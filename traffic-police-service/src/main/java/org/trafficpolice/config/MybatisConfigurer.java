package org.trafficpolice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangxiaofei
 * 2018年6月11日上午1:35:02
 */
@Configuration
@MapperScan("org.trafficpolice.dao")
public class MybatisConfigurer {

//	@Bean
//	ConfigurationCustomizer mybatisConfigurationCustomizer() {
//	    return new ConfigurationCustomizer() {
//			@Override
//			public void customize(org.apache.ibatis.session.Configuration configuration) {
//				configuration.setMapUnderscoreToCamelCase(true);
//				configuration.setDefaultFetchSize(100);
//				configuration.setDefaultStatementTimeout(30);
//			}
//	    };
//	}
	
}
