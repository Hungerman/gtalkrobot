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

    public QueueProxy(final Queue queue) {
        super();
        this.internalQueue = queue;
    }

    public synchronized boolean push(final Object event) {
        while (this.internalQueue.isFull() && this.isRunning) {
            try {
                this.wait();
            } catch (final InterruptedException e) {
            }
        }
        if (this.isRunning) {
            return this.internalQueue.push(event);
        }
        return false;
    }

    public synchronized Object pop() {
        while (this.internalQueue.isEmpty() && this.isRunning) {
            try {
                this.wait();
            } catch (final InterruptedException e) {
            }
        }
        if (this.isRunning) {
            return this.internalQueue.pop();
        }
        return null;
    }

    public synchronized void stop() {
        this.isRunning = false;
    }

    public synchronized boolean isEmpty() {
        return this.internalQueue.isEmpty();
    }

    public synchronized boolean isFull() {
        return this.internalQueue.isFull();
    }

    public synchronized int size() {
        return this.internalQueue.size();
    }

    public synchronized void clear() {
        this.internalQueue.clear();
    }
}
