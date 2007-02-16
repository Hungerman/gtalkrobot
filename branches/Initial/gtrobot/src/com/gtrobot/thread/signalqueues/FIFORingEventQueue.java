package com.gtrobot.thread.signalqueues;

public class FIFORingEventQueue implements EventQueue {
	public static int QUEUE_MAX_SIZE = 10000;

	public static int QUEUE_MIN_SIZE = 10;

	private Object[] internalQueue;

	private int head = 0;

	private int tail = 0;

	private int queueMaxSize;

	private int size;

	public FIFORingEventQueue(int queueMaxSize) {
		super();

		this.queueMaxSize = queueMaxSize;
		if (this.queueMaxSize < QUEUE_MIN_SIZE) {
			this.queueMaxSize = QUEUE_MIN_SIZE;
		} else if (this.queueMaxSize > QUEUE_MAX_SIZE) {
			this.queueMaxSize = QUEUE_MAX_SIZE;
		}

		clear();
	}

	public boolean push(Object event) {
		if (isFull())
			return false;
		internalQueue[tail] = event;
		incrementLast();
		return true;
		// if (size() >= internalQueue.length) {
		// if (last >= internalQueue.length) {
		// resizeArray();
		// }
	}

	public Object pop() {
		if (isEmpty())
			return null;

		Object event = internalQueue[head];
		internalQueue[head] = null;
		incrementFirst();
		return event;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == this.queueMaxSize;
	}

	public int size() {
		return size;
	}

	// private void resizeArray() {
	// Object[] newQueue = new Object[internalQueue.length * 2];
	//
	// for (int i = 0; i < internalQueue.length; i++) {
	// newQueue[internalQueue.length + i - 1] = internalQueue[i];
	// }
	// first = internalQueue.length + first - 1;
	// last = internalQueue.length + last - 1;
	// internalQueue = newQueue;
	// }

	private void incrementFirst() {
		head++;
		size--;
		if (head >= internalQueue.length)
			head = 0;
	}

	private void incrementLast() {
		tail++;
		size++;
		if (tail >= internalQueue.length)
			tail = 0;
	}

	public void clear() {
		size = 0;
		internalQueue = new Object[this.queueMaxSize];
	}

	public void stop() {
	}
}
