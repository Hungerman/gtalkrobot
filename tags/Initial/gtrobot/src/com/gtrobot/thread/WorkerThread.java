package com.gtrobot.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.processor.Processor;
import com.gtrobot.thread.signalqueues.EventQueue;

public class WorkerThread implements Runnable {
	protected static final transient Log log = LogFactory
			.getLog(WorkerThread.class);

	private Processor processor;

	private BaseCommand command;

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
		if (command == null && isRunning) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}

		internalProcess();

		// Reset the parameters and push self back to ThreadPool
		processor = null;
		command = null;
		parentThreadPool.push(this);
	}

	private void internalProcess() {
		if (command == null || !isRunning)
			return;
		try {
			processor.process(command);
		} catch (Exception e) {
			log.error("Error while WorkerThread.process!", e);
		}
	}

	public void setCommand(BaseCommand command) {
		this.command = command;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

}