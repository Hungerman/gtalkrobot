package com.gtrobot.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.thread.signalqueues.Queue;

/**
 * 实现了Thread Pool中的Worker的实装类。子类需要重载internalProcess来实现具体的业务处理。
 * 
 * @author Joey
 * 
 */
public class WorkerThread implements Runnable {
    protected static final transient Log log = LogFactory
            .getLog(WorkerThread.class);

    private Object processingData;

    private Queue parentThreadPool;

    private Thread workerThread;

    private boolean isRunning = true;

    public WorkerThread(final String name, final Queue threadPool) {
        this.parentThreadPool = threadPool;
        this.workerThread = new Thread(this, name);
        this.workerThread.setDaemon(false);
        this.workerThread.start();
    }

    /**
     * run ()
     * 
     * Wait for an command to process.<br>
     */
    public void run() {
        while (this.isRunning) {
            if (WorkerThread.log.isDebugEnabled()) {
                WorkerThread.log
                        .debug("WorkerThread is waiting for a new command comming.");
            }
            this.process();

            if (WorkerThread.log.isDebugEnabled()) {
                WorkerThread.log
                        .debug("WorkerThread just finished a command process.");
            }
        }
    }

    private synchronized void process() {
        if ((this.processingData == null) && this.isRunning) {
            try {
                this.wait();
            } catch (final InterruptedException e) {
            }
        }
        try {
            if ((this.processingData == null) || !this.isRunning) {
                return;
            }
            this.internalProcess();
        } catch (final Exception e) {
            WorkerThread.log.error("Error while WorkerThread.process!", e);
        }
        // Reset the parameters and push self back to ThreadPool
        this.processingData = null;
        this.parentThreadPool.push(this);
    }

    protected void internalProcess() {
        if (WorkerThread.log.isDebugEnabled()) {
            WorkerThread.log
                    .debug("WorkerThread just finished a dummy process.");
        }
    }

    public Object getProcessingData() {
        return this.processingData;
    }

    public void setProcessingData(final Object processingData) {
        this.processingData = processingData;
    }
}