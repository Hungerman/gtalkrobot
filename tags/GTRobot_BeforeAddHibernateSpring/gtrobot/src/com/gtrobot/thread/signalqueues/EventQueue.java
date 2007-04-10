package com.gtrobot.thread.signalqueues;

public interface EventQueue {
	public abstract boolean push(Object event);

	public abstract Object pop();

	public abstract boolean isEmpty();

	public abstract int size();

	public abstract boolean isFull();

	public abstract void clear();

	public abstract void stop();
}