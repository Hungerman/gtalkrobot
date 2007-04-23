package com.gtrobot.thread.signalqueues;

/**
 * 对Queue的访问进行了同步封装。具体实现类不需要考虑同步的问题。
 * 
 * @author Joey
 * 
 */
public class QueueProxy implements Queue {
	private Queue internalQueue = null;

	private boolean isRunning = true;

	public QueueProxy(Queue queue) {
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
