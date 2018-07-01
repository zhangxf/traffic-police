package org.trafficpolice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author zhangxiaofei
 * @createdOn 2018年5月28日 下午9:18:17
 */
@ServletComponentScan(basePackages= {"org.trafficpolice.filters", "org.trafficpolice.servlets", "org.trafficpolice.listeners"})
@SpringBootApplication(scanBasePackages="org.trafficpolice")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
