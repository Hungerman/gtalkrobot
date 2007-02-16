package com.gtrobot.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.utils.UserSession;

public class InteractiveProcessor extends AbstractProcessor {
	private static final String INTERACTIVE_BEFORE_PROCESS = "interactiveBeforeProcess_";

	private static final String INTERACTIVE_PROCESS = "interactiveProcess_";

	protected long steps = 1;

	public InteractiveProcessor(long steps) {
		super();
		this.steps = steps;
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		String jid = abCmd.getUserEntry().getJid();
		String sessionKey = "-step";
		Long step = (Long) UserSession.getSession(jid, sessionKey);
		if (step == null) {
			step = new Long(0);
		}

		executeMethod(INTERACTIVE_BEFORE_PROCESS, step, abCmd);
		if (abCmd.getErrorMessage() == null) {
			executeMethod(INTERACTIVE_PROCESS, step, abCmd);
		}

		step = new Long(step.longValue() + 1);
		if (step.longValue() >= steps) {
			UserSession.removeSession(jid, sessionKey);
		} else {
			UserSession.putSession(jid, sessionKey, step);
		}
	}

	private void executeMethod(String methodPrefix, Long step,
			AbstractCommand abCmd) throws XMPPException {
		Class clazz = this.getClass();
		String methodName = methodPrefix + step;
		try {
			Method method = clazz.getMethod("methodName", new Class[] { abCmd
					.getClass() });
			method.invoke(this, new Object[] { abCmd });
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			if (targetException instanceof XMPPException)
				throw (XMPPException) targetException;
			else
				log.error("Exception in executeMethod: " + methodName, e);
		} catch (Exception e) {
			log.error("Exception in executeMethod: " + methodName, e);
		}
	}
}
