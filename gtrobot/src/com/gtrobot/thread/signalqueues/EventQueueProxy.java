package com.gtrobot.thread.signalqueues;

public class EventQueueProxy implements EventQueue {
	private EventQueue internalQueue = null;

	private boolean isRunning = true;

	public EventQueueProxy(EventQueue queue) {
		super();
		internalQueue = queue;
	}

	public synchronized boolean push(Object event) {
		while (internalQueue.isFull() && isRunning) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		if (isRunning) {
			return internalQueue.push(event);
		}
		return false;
	}

	public synchronized Object pop() {
		while (internalQueue.isEmpty() && isRunning) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		if (isRunning) {
			return internalQueue.pop();
		}
		return null;
	}

	public synchronized void stop() {
		this.isRunning = false;
	}

	public synchronized boolean isEmpty() {
		return internalQueue.isEmpty();
	}

	public synchronized boolean isFull() {
		return internalQueue.isFull();
	}

	public synchronized int size() {
		return internalQueue.size();
	}

	public synchronized void clear() {
		internalQueue.clear();
	}
}
