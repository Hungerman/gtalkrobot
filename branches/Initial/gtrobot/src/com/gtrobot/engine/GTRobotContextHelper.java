package com.gtrobot.engine;

import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gtrobot.processor.common.HelpProcessor;
import com.gtrobot.service.common.UserEntryService;
import com.gtrobot.thread.WorkerDispatcher;

/**
 * 初始化Spring的Context。<br>
 * 提供快速方便的接口访问所有在SpringContext中的对象。<br>
 * 如果要访问对象不存在（或者没有定义,或者生成的时候发生错误），则返回null。
 * 
 * 
 * @author sunyuxin
 * 
 */
public class GTRobotContextHelper {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotContextHelper.class);

	private static ApplicationContext appContext;

	/** GoogleTalkConnection定数 */
	private static final String GOOGLE_TALK_CONNECTION = "googleTalkConnection";

	/** Session Cache定数 */
	private static final String SESSION_CACHE = "sessionCache";

	/** Chat Cache定数 */
	private static final String CHAT_CACHE = "sessionCache";

	/** WorkerDispatcher定数 */
	private static final String WORKER_DISPATCHER = "workerDispatcher";

	/** GTRobotDispatcher定数 */
	private static final String GTROBOT_DISPATCHER = "gTRobotDispatcher";

	/** UserEntryService定数 */
	private static final String USERENTRY_SERVICE = "userEntryService";

	/** helpProcessor定数 */
	private static final String HELP_PROCESSOR = "helpProcessor";

	/**
	 * 初始化Spring的Context。
	 */
	public static ApplicationContext initApplicationContext() {
		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext(
					"classpath*:*-context.xml");
		}
		return appContext;
	}

	public static ApplicationContext getApplicationContext() {
		return initApplicationContext();
	}

	/**
	 * Beanの取得
	 * 
	 * @param beanName
	 * @return Object
	 */
	public static Object getBean(String beanName) {
		if (!getApplicationContext().containsBean(beanName)) {
			log.warn("Can't find the bean in application appContext: "
					+ beanName);
			return null;
		}
		return getApplicationContext().getBean(beanName);
	}

	public static GoogleTalkConnection getGoogleTalkConnection() {
		return (GoogleTalkConnection) getBean(GOOGLE_TALK_CONNECTION);
	}

	public static Cache getSessionCache() {
		return (Cache) getBean(SESSION_CACHE);
	}

	public static Cache getChatCache() {
		return (Cache) getBean(CHAT_CACHE);
	}

	public static WorkerDispatcher getWorkerDispatcher() {
		return (WorkerDispatcher) getBean(WORKER_DISPATCHER);
	}

	public static GTRobotDispatcher getGTRobotDispatcher() {
		return (GTRobotDispatcher) getBean(GTROBOT_DISPATCHER);
	}

	public static UserEntryService getUserEntryService() {
		return (UserEntryService) getBean(USERENTRY_SERVICE);
	}

	public static HelpProcessor getHelpProcessor() {
		return (HelpProcessor) getBean(HELP_PROCESSOR);
	}
}
