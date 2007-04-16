package com.gtrobot.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Manager all user information. Mantenant the activeUserList. When user status
 * change event coming, this context will be invoked to update the user's
 * status.
 * 
 * 
 * @author sunyuxin
 * 
 */
public class GTRobotContext {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotContext.class);

	private static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"classpath*:*-context.xml");

	public static ClassPathXmlApplicationContext getContext() {
		return context;
	}
}
