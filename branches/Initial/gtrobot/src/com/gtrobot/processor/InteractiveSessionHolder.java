package com.gtrobot.processor;

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