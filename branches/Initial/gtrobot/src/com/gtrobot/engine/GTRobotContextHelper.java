package com.gtrobot.engine;

import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gtrobot.command.common.MailSenderCommand;
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

    /** mailSenderCommand定数 */
    private static final String MAIL_SENDER_COMMAND = "mailSenderCommand";

    /**
     * 初始化Spring的Context。
     */
    public static ApplicationContext initApplicationContext() {
        if (GTRobotContextHelper.appContext == null) {
            GTRobotContextHelper.appContext = new ClassPathXmlApplicationContext(
                    "classpath*:*-context.xml");
        }
        return GTRobotContextHelper.appContext;
    }

    public static ApplicationContext getApplicationContext() {
        return GTRobotContextHelper.initApplicationContext();
    }

    /**
     * Beanの取得
     * 
     * @param beanName
     * @return Object
     */
    public static Object getBean(final String beanName) {
        if (!GTRobotContextHelper.getApplicationContext()
                .containsBean(beanName)) {
            GTRobotContextHelper.log
                    .warn("Can't find the bean in application appContext: "
                            + beanName);
            return null;
        }
        return GTRobotContextHelper.getApplicationContext().getBean(beanName);
    }

    public static GoogleTalkConnection getGoogleTalkConnection() {
        return (GoogleTalkConnection) GTRobotContextHelper
                .getBean(GTRobotContextHelper.GOOGLE_TALK_CONNECTION);
    }

    public static Cache getSessionCache() {
        return (Cache) GTRobotContextHelper
                .getBean(GTRobotContextHelper.SESSION_CACHE);
    }

    public static Cache getChatCache() {
        return (Cache) GTRobotContextHelper
                .getBean(GTRobotContextHelper.CHAT_CACHE);
    }

    public static WorkerDispatcher getWorkerDispatcher() {
        return (WorkerDispatcher) GTRobotContextHelper
                .getBean(GTRobotContextHelper.WORKER_DISPATCHER);
    }

    public static GTRobotDispatcher getGTRobotDispatcher() {
        return (GTRobotDispatcher) GTRobotContextHelper
                .getBean(GTRobotContextHelper.GTROBOT_DISPATCHER);
    }

    public static UserEntryService getUserEntryService() {
        return (UserEntryService) GTRobotContextHelper
                .getBean(GTRobotContextHelper.USERENTRY_SERVICE);
    }

    public static HelpProcessor getHelpProcessor() {
        return (HelpProcessor) GTRobotContextHelper
                .getBean(GTRobotContextHelper.HELP_PROCESSOR);
    }

    public static MailSenderCommand getMailSenderCommand() {
        return (MailSenderCommand) GTRobotContextHelper
                .getBean(GTRobotContextHelper.MAIL_SENDER_COMMAND);
    }
}
