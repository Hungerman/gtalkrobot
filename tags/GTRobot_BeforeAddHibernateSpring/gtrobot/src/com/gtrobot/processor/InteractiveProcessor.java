package com.gtrobot.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.commandparser.CommadParser;
import com.gtrobot.utils.UserSession;

public class InteractiveProcessor extends AbstractProcessor {
	protected static final int CONTINUE = 0;

	protected static final int END_STEPS = -1;

	protected static final int STEP_TO_MENU = 100;

	protected static final long EXIT_STEP_NUMBER = 9999;

	protected static final int BAD_ANSWER = -1;

	protected static final int YES = 0;

	protected static final int NO = 1;

	// private static final String INTERACTIVE_PROCESS_PROMPT =
	// "interactiveProcessPrompt";

	private static final String INTERACTIVE_PROCESS = "interactiveProcess";

	public static final String STEP_SESSION_KEY = "-step";

	public static final String TEMP_SESSION_KEY = "-temp";

	protected Map methodTable;

	protected String sessionKey;

	public InteractiveProcessor(String sessionKey) {
		super();
		if (sessionKey == null) {
			sessionKey = this.getClass().getName();
		}
		this.sessionKey = sessionKey;
		this.methodTable = new Hashtable();
		initMathodTable(this.getClass());
	}

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		// Check whether the command is exit
		String commandName = (String) abCmd.getArgv().get(0);
		if (CommadParser.EXIT_COMMAND.equals(commandName)) {
			jumpToStep(EXIT_STEP_NUMBER);
		}
		if (CommadParser.BACK_MENU_COMMAND.equals(commandName)) {
			jumpToStep(STEP_TO_MENU);
		}

		while (true) {
			int result = executeMethod(INTERACTIVE_PROCESS, getStep(), abCmd);
			switch (result) {
			case CONTINUE: {
				nextStep();
				getUserEntryHolder().setCommandType(abCmd.getCommandType());
				UserSession.storePreviousCommand(abCmd);
				return;
			}
			case END_STEPS:
				clearInteractiveSessionHolder();
				getUserEntryHolder().setCommandType(null);
				UserSession.removePreviousCommand(abCmd);
				return;
			default:
				if (result > 0) {
					jumpToStep(result);
				} else {
					log.error("Invalid returned step number: " + result);
				}
				break;
			}
		}
		// Prompt the next process
		// if (result != END_STEPS && abCmd.getErrorMessage() == null) {
		// result = executeMethod(INTERACTIVE_PROCESS_PROMPT, getStep(), abCmd);
		// if (result != CONTINUE) {
		// jumpToStep(result);
		// result = executeMethod(INTERACTIVE_PROCESS_PROMPT, getStep(),
		// abCmd);
		// }
		// }
		// return;
	}

	private int executeMethod(String methodPrefix, long step, BaseCommand abCmd)
			throws XMPPException {
		Class clazz = this.getClass();
		String methodName = methodPrefix + "_" + step;
		try {
			Method method = (Method) methodTable.get(methodName);
			if (method == null) {
				log.warn("Skipped! Not found: " + clazz.getName() + "."
						+ methodName);
				return CONTINUE;
			} else {
				Integer result = (Integer) method.invoke(this,
						new Object[] { abCmd });
				return result.intValue();
			}
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			if (targetException instanceof XMPPException)
				throw (XMPPException) targetException;
			else
				log.error("Exception in executeMethod: " + methodName, e);
		} catch (Exception e) {
			log.error("Exception in executeMethod: " + methodName, e);
		}
		return END_STEPS;
	}

	// protected int interactiveProcessPrompt_9999(BaseCommand cmd)
	// throws XMPPException {
	// StringBuffer msgBuf = new StringBuffer();
	// formartMessageHeader(cmd, msgBuf);
	//
	// msgBuf.append("Are you sure to exit current interactive operation?");
	// sendBackMessage(cmd, msgBuf.toString());
	// return CONTINUE;
	// }

	protected int interactiveProcess_0(BaseCommand cmd) throws XMPPException {
		return STEP_TO_MENU;
	}

	protected int interactiveProcess_9999(BaseCommand cmd) throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
//		formartMessageHeader(cmd, msgBuf);

		msgBuf.append(cmd.getI18NMessage("InteractiveProcessor.exit." + cmd.getCommandType()));
		sendBackMessage(cmd, msgBuf.toString());
		return END_STEPS;
	}

	private InteractiveSessionHolder getInteractiveSessionHolder() {
		String jid = getUserEntryHolder().getJid();
		InteractiveSessionHolder ish = (InteractiveSessionHolder) UserSession
				.getSession(jid, sessionKey);
		if (ish == null) {
			ish = new InteractiveSessionHolder();
			UserSession.putSession(jid, sessionKey, ish);
		}
		return ish;
	}

	protected void clearInteractiveSessionHolder() {
		String jid = getUserEntryHolder().getJid();
		UserSession.removeSession(jid, sessionKey);
	}

	protected Object getSession() {
		return getInteractiveSessionHolder().getSession();
	}

	protected void setSession(Object obj) {
		getInteractiveSessionHolder().setSession(obj);
	}

	protected Object getTempSession() {
		return getInteractiveSessionHolder().getTempSession();
	}

	protected void setTempSession(Object obj) {
		getInteractiveSessionHolder().setTempSession(obj);
	}

	protected void jumpToStep(long step) {
		getInteractiveSessionHolder().setStep(step);
	}

	protected void nextStep() {
		getInteractiveSessionHolder().setStep(getStep() + 1);
	}

	protected long getStep() {
		return getInteractiveSessionHolder().getStep();
	}

	protected void formartMessageHeader(BaseCommand cmd, StringBuffer msgBuf) {
		msgBuf.append("step(");
		msgBuf.append(getStep());
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

	private void initMathodTable(Class clazz) {
		if (!clazz.getName().equals(InteractiveProcessor.class.getName())) {
			initMathodTable(clazz.getSuperclass());
		}
		initMathods(clazz);
	}

	private void initMathods(Class clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().startsWith(INTERACTIVE_PROCESS)) {
				method.setAccessible(true);
				methodTable.put(method.getName(), method);
			}
		}
	}
}

class InteractiveSessionHolder {
	private Object session;

	private long step;

	private Object tempSession;

	public InteractiveSessionHolder() {
		step = 0;
	}

	public Object getSession() {
		return session;
	}

	public long getStep() {
		return step;
	}

	public Object getTempSession() {
		return tempSession;
	}

	public void setSession(Object session) {
		this.session = session;
	}

	public void setStep(long step) {
		this.step = step;
	}

	public void setTempSession(Object tempSession) {
		this.tempSession = tempSession;
	}
}
