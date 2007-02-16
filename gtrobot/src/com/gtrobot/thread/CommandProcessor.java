package com.gtrobot.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.processor.Processor;
import com.gtrobot.thread.signalqueues.EventQueue;
import com.gtrobot.thread.signalqueues.EventQueueProxy;
import com.gtrobot.thread.signalqueues.FIFORingEventQueue;
import com.gtrobot.utils.MessageBundle;
import com.gtrobot.utils.ParameterTable;

public class CommandProcessor extends SpinObjectPool {
	protected static final transient Log log = LogFactory
			.getLog(CommandProcessor.class);

	private static CommandProcessor instance = new CommandProcessor(
			"CommandProcessor");

	private int threadNumber = 3;

	private EventQueue threadPool;

	public static CommandProcessor getInstance() {
		return instance;
	}

	private CommandProcessor(String name) {
		super(name, true, new FIFORingEventQueue(20));

		// Initialize the thread pool
		threadPool = new EventQueueProxy(new FIFORingEventQueue(threadNumber));
		for (int i = 0; i < threadNumber; i++) {
			threadPool.push(new WorkerThread("Command processor: " + i,
					threadPool));
		}
	}

	/**
	 * Invoked when an comamnd should be triggered into EventQueue.<br>
	 * The new coming comamnd will be pushed into EventQueue.
	 * 
	 * @param comamnd
	 */
	public void triggerCommand(AbstractCommand cmd) {
		if (log.isDebugEnabled()) {
			log.debug("CommandProcessor is trigger a command...");
		}
		trigger(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gtrobot.thread.SpinThreadPool#process(java.lang.Object)
	 */
	protected void process(Object event) {
		AbstractCommand cmd = (AbstractCommand) event;

		String comamndClassName = cmd.getClass().getName();
		Processor processor = (Processor) ParameterTable
				.getCommadProcessorsMappings().get(comamndClassName);

		if (processor == null) {
			processor = (Processor) ParameterTable
					.getCommadProcessorsMappings().get("errorProcessor");
			String sysError = MessageBundle.getInstance().getMessage(
					"system.error.processor.null");
			log.warn(sysError + "with command: " + cmd.getClass().getName()
					+ " origin message: " + cmd.getOriginMessage());
			cmd.setErrorMessage(sysError);
		}

		try {
			if (log.isDebugEnabled()) {
				log
						.debug("CommandProcessor is waiting for a idle worker thread...");
			}
			// Block and wait for a idle thread
			WorkerThread workerThread = (WorkerThread) threadPool.pop();
			synchronized (workerThread) {
				// Set the worker thread parameters
				workerThread.setProcessor(processor);
				workerThread.setCommand(cmd);
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
}