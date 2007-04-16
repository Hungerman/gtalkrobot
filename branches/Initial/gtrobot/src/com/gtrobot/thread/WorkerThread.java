package com.gtrobot.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.thread.signalqueues.EventQueue;

public class WorkerThread implements Runnable {
	protected static final transient Log log = LogFactory
			.getLog(WorkerThread.class);

	private Object processingData;

	private EventQueue parentThreadPool;

	private Thread workerThread;

	private boolean isRunning = true;

	public WorkerThread(String name, EventQueue threadPool) {
		parentThreadPool = threadPool;
		workerThread = new Thread(this, name);
		workerThread.setDaemon(false);
		workerThread.start();
	}

	/**
	 * run ()
	 * 
	 * Wait for an command to process.<br>
	 */
	public void run() {
		while (isRunning) {
			if (log.isDebugEnabled()) {
				log.debug("WorkerThread is waiting for a new command comming.");
			}
			process();

			if (log.isDebugEnabled()) {
				log.debug("WorkerThread just finished a command process.");
			}
		}
	}

	private synchronized void process() {
		if (processingData == null && isRunning) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		try {
			if (processingData == null || !isRunning)
				return;
			internalProcess();
		} catch (Exception e) {
			log.error("Error while WorkerThread.process!", e);
		}
		// Reset the parameters and push self back to ThreadPool
		processingData = null;
		parentThreadPool.push(this);
	}

	protected void internalProcess() {
		if (log.isDebugEnabled()) {
			log.debug("WorkerThread just finished a dummy process.");
		}
	}

	public Object getProcessingData() {
		return processingData;
	}

	public void setProcessingData(Object processingData) {
		this.processingData = processingData;
	}
}