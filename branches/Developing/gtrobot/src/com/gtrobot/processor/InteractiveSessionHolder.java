package com.gtrobot.processor;

/**
 * 交互式业务处理关联的Session情报。Session信息，Step信息，临时Session信息。
 * 
 * @author Joey
 * 
 */
public class InteractiveSessionHolder {
    private Object session;

    private int step;

    private Object tempSession;

    public InteractiveSessionHolder() {
        this.step = 0;
    }

    public Object getSession() {
        return this.session;
    }

    public int getStep() {
        return this.step;
    }

    public Object getTempSession() {
        return this.tempSession;
    }

    public void setSession(final Object session) {
        this.session = session;
    }

    public void setStep(final int step) {
        this.step = step;
    }

    public void setTempSession(final Object tempSession) {
        this.tempSession = tempSession;
    }
}