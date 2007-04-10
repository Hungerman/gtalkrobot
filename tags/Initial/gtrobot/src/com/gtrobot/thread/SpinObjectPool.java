package com.gtrobot.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.thread.signalqueues.EventQueue;
import com.gtrobot.thread.signalqueues.EventQueueProxy;

/**
 * SpinObjectPool is inspired by the SpinLock of werx.<br>
 * Base class for thread safety and flexibility.<br>
 * One of instance of this class includes a EventQueueProxy. <br>
 * EventQueueProxy is a management interface of a actual EventQueue.<br>
 * <br>
 * trigger(event) will push the object into EventQueue.<br>
 * run() will wait event comming to EventQueue and pop it.<br>
 * process(event) will deal the deatail with the event.<br>
 * 
 * @author sunyuxin
 */

public abstract class SpinObjectPool implements Runnable {
	protected static final transient Log log = LogFactory
			.getLog(SpinObjectPool.class);

	private EventQueue eventQueue;

	private Thread spinThread;

	private boolean isRunning = true;

	public SpinObjectPool(String name, boolean isDaemon, EventQueue eventQueue) {
		this.eventQueue = new EventQueueProxy(eventQueue);
		spinThread = new Thread(this, name);
		spinThread.setDaemon(isDaemon);
		spinThread.start();
	}

	/**
	 * run ()
	 * 
	 * Wait for an event coming from EventQueue.<br>
	 * When an event came, pop the event and process it.<br>
	 */
	public void run() {
		while (isRunning) {
			if (log.isDebugEnabled()) {
				log.debug("SpinObjectPool is waiting to pop a event.");
			}
			// Block to wait for an event coming.
			Object event = eventQueue.pop();
			// Deal the event
			process(event);
			if (log.isDebugEnabled()) {
				log.debug("SpinObjectPool just processed an event.");
			}
		}
	}

	/**
	 * Deal the logic with the event.
	 * 
	 * @param event
	 */
	protected abstract void process(Object event);

	/**
	 * Invoked when an evnet should be triggered into EventQueue.<br>
	 * The new coming event will be pushed into EventQueue.
	 * 
	 * @param event
	 */
	protected void trigger(Object event) {
		synchronized (eventQueue) {
			// Check and block while the EventQueue is full
			eventQueue.push(event);
			eventQueue.notifyAll();
		}
	}

	public final void stop() {
		synchronized (eventQueue) {
			isRunning = false;
			eventQueue.stop();
			// ReflectionBus.broadcast(new
			// NotifyStopProcessSignal(spinThread.getName()));
			eventQueue.notifyAll();
		}
	}
}