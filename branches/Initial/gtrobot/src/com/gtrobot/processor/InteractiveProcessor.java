package com.gtrobot.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.utils.UserSession;

public class InteractiveProcessor extends AbstractProcessor {
	private static final String INTERACTIVE_BEFORE_PROCESS = "interactiveBeforeProcess_";

	private static final String INTERACTIVE_PROCESS = "interactiveProcess_";

	public static final String STEP_SESSION_KEY = "-step";

	protected long steps = 1;

	protected String sessionKey;

	public InteractiveProcessor(long steps, String sessionKey) {
		super();
		this.steps = steps;
		this.sessionKey = sessionKey;
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		String jid = abCmd.getUserEntry().getJid();

		Long step = getStep(jid);
		if (step == null) {
			step = new Long(0);
			updateStep(jid, step);
		}

		executeMethod(INTERACTIVE_BEFORE_PROCESS, step, abCmd);
		if (abCmd.getErrorMessage() == null) {
			executeMethod(INTERACTIVE_PROCESS, step, abCmd);
		}

		step = new Long(step.longValue() + 1);
		if (step.longValue() >= steps) {
			removeStep(jid);
			removeSession(jid);
		} else {
			updateStep(jid, step);
		}
	}

	private void executeMethod(String methodPrefix, Long step,
			AbstractCommand abCmd) throws XMPPException {
		Class clazz = this.getClass();
		String methodName = methodPrefix + step;
		try {
			Method method = clazz.getDeclaredMethod(methodName,
					new Class[] { abCmd.getClass() });
			method.setAccessible(true);
			method.invoke(this, new Object[] { abCmd });
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			if (targetException instanceof XMPPException)
				throw (XMPPException) targetException;
			else
				log.error("Exception in executeMethod: " + methodName, e);
		} catch (NoSuchMethodException e) {
		} catch (Exception e) {
			log.error("Exception in executeMethod: " + methodName, e);
		}
	}

	protected Object getSession(String jid) {
		if (sessionKey == null)
			return null;
		return UserSession.getSession(jid, sessionKey);
	}

	protected void setSession(String jid, Object obj) {
		if (sessionKey == null)
			return;
		UserSession.putSession(jid, sessionKey, obj);
	}

	protected void removeSession(String jid) {
		UserSession.removeSession(jid, sessionKey);
		if (sessionKey == null)
			return;
	}

	public static void updateStep(String jid, Long step) {
		UserSession.putSession(jid, STEP_SESSION_KEY, step);
	}

	public static void removeStep(String jid) {
		UserSession.removeSession(jid, STEP_SESSION_KEY);
	}

	public static Long getStep(String jid) {
		return (Long) UserSession.getSession(jid, STEP_SESSION_KEY);
	}

	public static void formartMessageHeader(AbstractCommand cmd,
			StringBuffer msgBuf) throws XMPPException {
		String jid = cmd.getUserEntry().getJid();
		Long step = getStep(jid);

		msgBuf.append("step(");
		msgBuf.append(step);
		msgBuf.append(")# ");
	}
}
