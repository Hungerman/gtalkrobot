package com.gtrobot.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.thread.signalqueues.FIFORingEventQueue;
import com.gtrobot.thread.signalqueues.Queue;
import com.gtrobot.thread.signalqueues.QueueProxy;

/**
 * ThreadPool的处理Dispatcher类。当接收新的Event的时候，从ThreadPool中选择Idle的Worker进行激活，使其对Event进行具体的处理。
 * 
 * @author Joey
 * 
 */
public class WorkerDispatcher extends SpinObjectPool {
	protected static final transient Log log = LogFactory
			.getLog(WorkerDispatcher.class);

	private int threadNumber = 3;

	private Queue threadPool;

	public WorkerDispatcher(String name, int threadNumber) {
		super(name, true, new FIFORingEventQueue(20));

		// Initialize the thread pool
		this.threadNumber = threadNumber;
		threadPool = new QueueProxy(new FIFORingEventQueue(threadNumber));
		for (int i = 0; i < threadNumber; i++) {
			threadPool.push(getWorkerThread(i, threadPool));
		}
	}

	protected WorkerThread getWorkerThread(int i, Queue threadPool) {
		return new WorkerThread("WorkerThread: " + i, threadPool);
	}

	/**
	 * Invoked when an comamnd should be triggered into Queue.<br>
	 * The new coming comamnd will be pushed into Queue.
	 * 
	 * @param comamnd
	 */
	public void triggerEvent(Object event) {
		if (log.isDebugEnabled()) {
			log.debug("WorkerDispatcher is triggering a new event...");
		}
		trigger(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gtrobot.thread.SpinThreadPool#process(java.lang.Object)
	 */
	protected void process(Object event) {
		try {
			if (log.isDebugEnabled()) {
				log
						.debug("CommandProcessor is waiting for a idle worker thread...");
			}
			// Block and wait for a idle thread
			WorkerThread workerThread = (WorkerThread) threadPool.pop();
			synchronized (workerThread) {
				// Set the worker thread parameters
				workerThread.setProcessingData(event);
				if (log.isDebugEnabled()) {
					log
							.debug("CommandProcessor is notify the worker thread to start...");
				}
				workerThread.notifyAll();
			}
		} catch (Exception e) {
			log.error("Processing error!", e);
		}
		return;
	}

	public int getThreadNumber() {
		return threadNumber;
	}
}