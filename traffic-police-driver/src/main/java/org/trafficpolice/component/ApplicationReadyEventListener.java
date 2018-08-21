package org.trafficpolice.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangxiaofei
 * 2018年8月19日下午5:42:37
 */
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationReadyEventListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		logger.info("############【驾驶人手机端】【启动完成】############");
	}

}
