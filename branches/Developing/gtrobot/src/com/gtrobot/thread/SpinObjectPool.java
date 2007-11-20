package com.gtrobot.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.thread.signalqueues.Queue;
import com.gtrobot.thread.signalqueues.QueueProxy;

/**
 * SpinObjectPool来自werx，实现了线程安全的对象控制类。<br>
 * 
 * 主要实现了如下方面：<br>
 * 1.本身是一个Runable的实现，运行时在一个独立的Thread中进行操作。<br>
 * 2.管理了一个Queue对象池。<br>
 * 3.进行运行的时候检查Queue中的对象，阻塞在pop方法上，当pop返回对象是，调用process接口。<br>
 * 4.提供trigger接口供外界push适当的event对象到queue中。<br>
 * 
 * SpinObjectPool is inspired by the SpinLock of werx.<br>
 * Base class for thread safety and flexibility.<br>
 * One of instance of this class includes a QueueProxy. <br>
 * QueueProxy is a management interface of a actual Queue.<br>
 * <br>
 * trigger(event) will push the object into Queue.<br>
 * run() will wait event comming to Queue and pop it.<br>
 * process(event) will deal the deatail with the event.<br>
 * 
 * @author sunyuxin
 */

public abstract class SpinObjectPool implements Runnable {
    protected static final transient Log log = LogFactory
            .getLog(SpinObjectPool.class);

    private Queue eventQueue;

    private Thread spinThread;

    private boolean isRunning = true;

    public SpinObjectPool(final String name, final boolean isDaemon,
            final Queue eventQueue) {
        this.eventQueue = new QueueProxy(eventQueue);
        this.spinThread = new Thread(this, name);
        this.spinThread.setDaemon(isDaemon);
        this.spinThread.start();
    }

    /**
     * run ()
     * 
     * Wait for an event coming from Queue.<br>
     * When an event came, pop the event and process it.<br>
     */
    public void run() {
        while (this.isRunning) {
            if (SpinObjectPool.log.isDebugEnabled()) {
                SpinObjectPool.log
                        .debug("SpinObjectPool is waiting to pop a event.");
            }
            // Block to wait for an event coming.
            final Object event = this.eventQueue.pop();
            // Deal the event
            this.process(event);
            if (SpinObjectPool.log.isDebugEnabled()) {
                SpinObjectPool.log
                        .debug("SpinObjectPool just processed an event.");
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
     * Invoked when an evnet should be triggered into Queue.<br>
     * The new coming event will be pushed into Queue.
     * 
     * @param event
     */
    protected void trigger(final Object event) {
        synchronized (this.eventQueue) {
            // Check and block while the Queue is full
            this.eventQueue.push(event);
            this.eventQueue.notifyAll();
        }
    }

    public final void stop() {
        synchronized (this.eventQueue) {
            this.isRunning = false;
            this.eventQueue.stop();
            // ReflectionBus.broadcast(new
            // NotifyStopProcessSignal(spinThread.getName()));
            this.eventQueue.notifyAll();
        }
    }
}