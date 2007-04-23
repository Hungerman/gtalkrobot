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
		step = 0;
	}

	public Object getSession() {
		return session;
	}

	public int getStep() {
		return step;
	}

	public Object getTempSession() {
		return tempSession;
	}

	public void setSession(Object session) {
		this.session = session;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void setTempSession(Object tempSession) {
		this.tempSession = tempSession;
	}
}