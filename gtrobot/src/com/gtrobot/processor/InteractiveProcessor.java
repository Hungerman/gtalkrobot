package com.gtrobot.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.engine.GTRobotDispatcher;
import com.gtrobot.exception.CommandMatchedException;
import com.gtrobot.utils.UserSessionUtil;

public class InteractiveProcessor extends AbstractProcessor {
	public static final String INTERACTIVE_PROCESSOR_SESSION_KEY = "_ipSessionKey";

	protected static final int CONTINUE = 0;

	protected static final int REPEAT_THIS_STEP = -1;

	protected static final int END_STEPS = -9;

	protected static final int STEP_TO_MENU = 10;

	protected static final int EXIT_STEP_NUMBER = 9999;

	protected static final int BAD_ANSWER = -1;

	protected static final int YES = 0;

	protected static final int NO = 1;

	// private static final String INTERACTIVE_PROCESS_PROMPT =
	// "interactiveProcessPrompt";

	private static final String INTERACTIVE_PROCESS = "interactiveProcess";

	public static final String STEP_SESSION_KEY = "-step";

	public static final String TEMP_SESSION_KEY = "-temp";

	protected Map<String, Method> methodTable = new Hashtable<String, Method>();

	protected Map<String, Integer> commandToStepMappings = new Hashtable<String, Integer>();

	public InteractiveProcessor() {
		super();
		initMathodTable(this.getClass());
		initCommandToStepMappings();
	}

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		// Check whether the command is exit
		String commandName = (String) abCmd.getArgv().get(0);
		if (GTRobotDispatcher.EXIT_COMMAND.equals(commandName)) {
			jumpToStep(EXIT_STEP_NUMBER);
		}
		if (GTRobotDispatcher.BACK_MENU_COMMAND.equals(commandName)) {
			jumpToStep(STEP_TO_MENU);
		}

		while (true) {
			int result = executeMethod(INTERACTIVE_PROCESS, getStep(), abCmd);
			switch (result) {
			case REPEAT_THIS_STEP:
				UserSessionUtil.storePreviousCommand(abCmd);
				return;
			case CONTINUE: {
				nextStep();
				UserSessionUtil.storePreviousCommand(abCmd);
				return;
			}
			case END_STEPS:
				clearInteractiveSessionHolder();
				UserSessionUtil.removePreviousCommand(abCmd);
				return;
			default:
				if (result > 0) {
					jumpToStep(result);
				} else {
					log.error("Invalid returned step number: " + result);
				}
				continue;
			}
		}
	}

	private int executeMethod(String methodPrefix, int step, BaseCommand abCmd)
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

	protected int interactiveProcess_0(BaseCommand cmd) throws XMPPException {
		return STEP_TO_MENU;
	}

	protected int interactiveProcess_9999(BaseCommand cmd)
			throws XMPPException, CommandMatchedException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		msgBuf.append(cmd.getI18NMessage("InteractiveProcessor.exit."
				+ cmd.getCommandType()));
		sendBackMessage(cmd, msgBuf.toString());

		GTRobotContextHelper.getHelpProcessor().internalProcess(cmd);
		return END_STEPS;
	}

	/**
	 * Menu
	 */
	protected int interactiveProcess_10(BaseCommand cmd) throws XMPPException {
		List<String> menuInfo = null;
		menuInfo = prepareMenuInfo();

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(seperator);
		msgBuf.append(endl);
		for (Iterator<String> it = menuInfo.iterator(); it.hasNext();) {
			msgBuf.append(cmd.getI18NMessage(it.next()));
			msgBuf.append(endl);
		}
		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected List<String> prepareMenuInfo() {
		List<String> menuInfo = new ArrayList<String>();
		return menuInfo;
	}

	protected int interactiveProcess_11(BaseCommand cmd) throws XMPPException {
		String opt = cmd.getOriginMessage().trim().toLowerCase();
		Integer step = commandToStepMappings.get(opt);
		if (step == null) {
			StringBuffer msgBuf = new StringBuffer();
			msgBuf.append(endl);
			msgBuf.append(cmd.getI18NMessage("invalid.command"));
			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_MENU;
		} else {
			return step.intValue();
		}
	}

	private InteractiveSessionHolder getInteractiveSessionHolder() {
		String jid = getUserEntryHolder().getJid();
		InteractiveSessionHolder ish = (InteractiveSessionHolder) UserSessionUtil
				.getSession(jid, INTERACTIVE_PROCESSOR_SESSION_KEY);
		if (ish == null) {
			ish = new InteractiveSessionHolder();
			UserSessionUtil.putSession(jid, INTERACTIVE_PROCESSOR_SESSION_KEY,
					ish);
		}
		return ish;
	}

	protected void clearInteractiveSessionHolder() {
		String jid = getUserEntryHolder().getJid();
		UserSessionUtil.removeSession(jid, INTERACTIVE_PROCESSOR_SESSION_KEY);
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

	protected void jumpToStep(int step) {
		getInteractiveSessionHolder().setStep(step);
	}

	protected void nextStep() {
		getInteractiveSessionHolder().setStep(getStep() + 1);
	}

	protected int getStep() {
		return getInteractiveSessionHolder().getStep();
	}

	protected void sendBackMessage(BaseCommand abCmd, String message)
			throws XMPPException {
		if (log.isDebugEnabled()) {
			StringBuffer msgBuf = new StringBuffer();
			msgBuf.append("step(");
			msgBuf.append(getStep());
			msgBuf.append(")# ");

			msgBuf.append(message);

			super.sendBackMessage(abCmd, msgBuf.toString());
		} else {
			super.sendBackMessage(abCmd, message);
		}
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

	protected void initCommandToStepMappings() {
		addCommandToStepMapping("b", STEP_TO_MENU);
		addCommandToStepMapping("x", EXIT_STEP_NUMBER);
	}

	protected void addCommandToStepMapping(String cmd, int step) {
		commandToStepMappings.put(cmd, new Integer(step));
	}
}
