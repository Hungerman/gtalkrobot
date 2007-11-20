package com.gtrobot.thread.signalqueues;

/**
 * 实现了一个FIFO的Queue类的实例。这里不用考虑同步和等待的问题，在进行Push和Pop的时候，外层调用已经保证了对应的操作已经是可行的。
 * 
 * @author Joey
 * 
 */
public class FIFORingEventQueue implements Queue {
    public static int QUEUE_MAX_SIZE = 10000;

    public static int QUEUE_MIN_SIZE = 10;

    private Object[] internalQueue;

    private int head = 0;

    private int tail = 0;

    private int queueMaxSize;

    private int size;

    public FIFORingEventQueue(final int queueMaxSize) {
        super();

        this.queueMaxSize = queueMaxSize;
        if (this.queueMaxSize < FIFORingEventQueue.QUEUE_MIN_SIZE) {
            this.queueMaxSize = FIFORingEventQueue.QUEUE_MIN_SIZE;
        } else if (this.queueMaxSize > FIFORingEventQueue.QUEUE_MAX_SIZE) {
            this.queueMaxSize = FIFORingEventQueue.QUEUE_MAX_SIZE;
        }

        this.clear();
    }

    public boolean push(final Object event) {
        if (this.isFull()) {
            return false;
        }
        this.internalQueue[this.tail] = event;
        this.incrementLast();
        return true;
        // if (size() >= internalQueue.length) {
        // if (last >= internalQueue.length) {
        // resizeArray();
        // }
    }

    public Object pop() {
        if (this.isEmpty()) {
            return null;
        }

        final Object event = this.internalQueue[this.head];
        this.internalQueue[this.head] = null;
        this.incrementFirst();
        return event;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == this.queueMaxSize;
    }

    public int size() {
        return this.size;
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
        this.head++;
        this.size--;
        if (this.head >= this.internalQueue.length) {
            this.head = 0;
        }
    }

    private void incrementLast() {
        this.tail++;
        this.size++;
        if (this.tail >= this.internalQueue.length) {
            this.tail = 0;
        }
    }

    public void clear() {
        this.size = 0;
        this.internalQueue = new Object[this.queueMaxSize];
    }

    public void stop() {
    }
}
