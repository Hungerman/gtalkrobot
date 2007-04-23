package com.gtrobot.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.processor.Processor;
import com.gtrobot.thread.WorkerThread;
import com.gtrobot.thread.signalqueues.EventQueue;

/**
 * 
 * 实现了一个具体的业务Thread操作类的执行接口。<br>
 * 通过Command对象关联的Processor进行实际的业务调用。<br>
 * 
 * 同时这里封装了Hibernate Session的管理处理。
 * 
 * @author Joey
 * 
 */
public class GTRobotWorkerThread extends WorkerThread {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotWorkerThread.class);

	public GTRobotWorkerThread(String name, EventQueue threadPool) {
		super(name, threadPool);
	}

	protected void internalProcess() {
		BaseCommand command = (BaseCommand) getProcessingData();

		Processor processor = command.getProcessor();
		if (processor == null) {
			log.error("Command's processor is null! "
					+ command.getCommandType() + " : "
					+ command.getClass().getName());
			return;
		}
		SessionFactory sessionFactory = (SessionFactory) GTRobotContextHelper
				.getBean("sessionFactory");
		log.debug("Opening Hibernate Session in GTRobotWorkerThread");
		Session session = getSession(sessionFactory);
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(session));
		try {
			// 执行具体的业务操作
			processor.process(command);
		} catch (Exception e) {
			log.error("Exception when processing in processor: "
					+ processor.toString(), e);
		} finally {
			TransactionSynchronizationManager.unbindResource(sessionFactory);
			log
					.debug("Closing single Hibernate Session in GTRobotWorkerThread");
			try {
				closeSession(session, sessionFactory);
			} catch (RuntimeException ex) {
				log.error("Unexpected exception on closing Hibernate Session",
						ex);
			}
		}
	}

	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.MANUAL);
		return session;
	}

	protected void closeSession(Session session, SessionFactory sessionFactory) {
		session.flush();
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}
}