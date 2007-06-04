package com.gtrobot.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.thread.WorkerDispatcher;
import com.gtrobot.thread.WorkerThread;
import com.gtrobot.thread.signalqueues.Queue;

/**
 * 定义GTRobot使用的ThreadWorderDispatcher类。完成任务在ThreadPool中的分发。
 * 
 * @author Joey
 * 
 */
public class GTRobotWorkerDispatcher extends WorkerDispatcher {
    protected static final transient Log log = LogFactory
            .getLog(GTRobotWorkerDispatcher.class);

    public GTRobotWorkerDispatcher(final String name, final int threadNumber) {
        super(name, threadNumber);
    }

    @Override
    protected WorkerThread getWorkerThread(final int i, final Queue threadPool) {
        return new GTRobotWorkerThread("GTRobotWorkerThread: " + i, threadPool);
    }
}