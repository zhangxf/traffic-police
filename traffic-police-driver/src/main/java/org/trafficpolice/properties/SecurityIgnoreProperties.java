package org.trafficpolice.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhangxiaofei
 * @createdOn 2018年1月30日 上午11:06:46
 */
@Configuration
//@EnableConfigurationProperties({SecurityIgnoreProperties.class})
@EnableConfigurationProperties
@ConfigurationProperties
@PropertySource("classpath:security-ignore.properties")
public class SecurityIgnoreProperties {
	
	private Map<String, String> pattern;

	public Map<String, String> getPattern() {
		return pattern;
	}

	public void setPattern(Map<String, String> pattern) {
		this.pattern = pattern;
	}

}
