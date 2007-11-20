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
import com.gtrobot.thread.signalqueues.Queue;

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

    public GTRobotWorkerThread(final String name, final Queue threadPool) {
        super(name, threadPool);
    }

    @Override
    protected void internalProcess() {
        final BaseCommand command = (BaseCommand) this.getProcessingData();

        final Processor processor = command.getProcessor();
        if (processor == null) {
            GTRobotWorkerThread.log.error("Command's processor is null! "
                    + command.getCommandType() + " : "
                    + command.getClass().getName());
            return;
        }
        final SessionFactory sessionFactory = (SessionFactory) GTRobotContextHelper
                .getBean("sessionFactory");
        GTRobotWorkerThread.log
                .debug("Opening Hibernate Session in GTRobotWorkerThread");
        final Session session = this.getSession(sessionFactory);
        TransactionSynchronizationManager.bindResource(sessionFactory,
                new SessionHolder(session));
        try {
            // 执行具体的业务操作
            processor.process(command);
        } catch (final Exception e) {
            GTRobotWorkerThread.log.error(
                    "Exception when processing in processor: "
                            + processor.toString(), e);
        } finally {
            TransactionSynchronizationManager.unbindResource(sessionFactory);
            GTRobotWorkerThread.log
                    .debug("Closing single Hibernate Session in GTRobotWorkerThread");
            try {
                this.closeSession(session, sessionFactory);
            } catch (final RuntimeException ex) {
                GTRobotWorkerThread.log
                        .error(
                                "Unexpected exception on closing Hibernate Session",
                                ex);
            }
        }
    }

    protected Session getSession(final SessionFactory sessionFactory)
            throws DataAccessResourceFailureException {
        final Session session = SessionFactoryUtils.getSession(sessionFactory,
                true);
        session.setFlushMode(FlushMode.MANUAL);
        return session;
    }

    protected void closeSession(final Session session,
            final SessionFactory sessionFactory) {
        session.flush();
        SessionFactoryUtils.releaseSession(session, sessionFactory);
    }
}