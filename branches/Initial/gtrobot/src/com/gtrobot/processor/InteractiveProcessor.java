package com.gtrobot.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.word.AddWordEntryCommand;
import com.gtrobot.utils.UserSession;

public class InteractiveProcessor extends AbstractProcessor {
	protected static final int CONTINUE = 0;

	protected static final int RETRY_CURRENT_STEP = -1;

	protected static final int END_STEPS = -2;

	private static final String EXIT_COMMAND = "x";

	private static final long EXIT_STEP_NUMBER = 9998;

	protected static final int BAD_ANSWER = -1;

	protected static final int YES = 0;

	protected static final int NO = 1;

	private static final String INTERACTIVE_PROCESS_PROMPT = "interactiveProcessPrompt_";

	private static final String INTERACTIVE_PROCESS = "interactiveProcess_";

	public static final String STEP_SESSION_KEY = "-step";

	public static final String TEMP_SESSION_KEY = "-temp";

	protected String sessionKey;

	public InteractiveProcessor(String sessionKey) {
		super();
		this.sessionKey = sessionKey;
	}

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		String jid = abCmd.getUserEntry().getJid();

		Long step = getStep(jid);
		if (step == null) {
			step = new Long(0);
			updateStep(jid, step);
		}

		// Check whether the command is exit
		if (abCmd.getOriginMessage().trim().toLowerCase().equals(EXIT_COMMAND)) {
			updateStep(jid, new Long(EXIT_STEP_NUMBER));
		}

		int result = executeMethod(INTERACTIVE_PROCESS, step, abCmd);
		switch (result) {
		case CONTINUE: {
			step = new Long(step.longValue() + 1);
			updateStep(jid, step);
			break;
		}
		case RETRY_CURRENT_STEP:
			break;
		case END_STEPS:
			clear(jid);
			break;
		default:
			if (result > 0) {
				step = new Long(result);
				updateStep(jid, step);
			} else {
				log.error("Invalid returned step number: " + result);
			}
			break;
		}
		// Prompt the next process
		if (result != END_STEPS && abCmd.getErrorMessage() == null) {
			result = executeMethod(INTERACTIVE_PROCESS_PROMPT, step, abCmd);
		}
		return;
	}

	private void clear(String jid) {
		removeTempSession(jid);
		removeStep(jid);
		removeSession(jid);
	}

	private int executeMethod(String methodPrefix, Long step, BaseCommand abCmd)
			throws XMPPException {
		Class clazz = this.getClass();
		String methodName = methodPrefix + step;
		try {
			Method method = clazz.getDeclaredMethod(methodName,
					new Class[] { abCmd.getClass() });
			method.setAccessible(true);
			Integer result = (Integer) method.invoke(this,
					new Object[] { abCmd });
			return result.intValue();
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			if (targetException instanceof XMPPException)
				throw (XMPPException) targetException;
			else
				log.error("Exception in executeMethod: " + methodName, e);
		} catch (NoSuchMethodException e) {
			return CONTINUE;
		} catch (Exception e) {
			log.error("Exception in executeMethod: " + methodName, e);
		}
		return END_STEPS;
	}

	protected int interactiveProcessPrompt_9999(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		formartMessageHeader(cmd, msgBuf);

		msgBuf.append("Are you sure to exit current interactive operation?");
		sendBackMessage(cmd, msgBuf.toString());
		return END_STEPS;
	}

	protected int interactiveProcess_9999(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		formartMessageHeader(cmd, msgBuf);

		msgBuf.append("You have exited from the interactive operation.");
		sendBackMessage(cmd, msgBuf.toString());
		return END_STEPS;
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

	protected Object getTempSession(String jid) {
		if (sessionKey == null)
			return null;
		return UserSession.getSession(jid, TEMP_SESSION_KEY);
	}

	protected void setTempSession(String jid, Object obj) {
		if (sessionKey == null)
			return;
		UserSession.putSession(jid, TEMP_SESSION_KEY, obj);
	}

	protected void removeTempSession(String jid) {
		UserSession.removeSession(jid, TEMP_SESSION_KEY);
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

	public static void formartMessageHeader(BaseCommand cmd, StringBuffer msgBuf) {
		String jid = cmd.getUserEntry().getJid();
		Long step = getStep(jid);

		msgBuf.append("step(");
		msgBuf.append(step);
		msgBuf.append(")# ");
	}

	public static int checkAnswer(BaseCommand cmd) {
		String originMessage = cmd.getOriginMessage();
		originMessage = originMessage.trim().toLowerCase();

		if (originMessage.endsWith("yes") || originMessage.endsWith("y")
				|| originMessage.endsWith("ok")) {
			return YES;
		}
		if (originMessage.endsWith("no") || originMessage.endsWith("not")) {
			return NO;
		}
		return BAD_ANSWER;
	}
}
