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
import com.gtrobot.exception.CommandMatchedException;
import com.gtrobot.utils.CommonUtils;
import com.gtrobot.utils.UserSessionUtil;

public class InteractiveProcessor extends AbstractProcessor {
	public static final String INTERACTIVE_PROCESSOR_SESSION_KEY = "_ipSessionKey";

	protected static final int CONTINUE = 0;

	protected static final int REPEAT_THIS_STEP = -1;

	protected static final int END_BY_INTERNAL_EXCEPTION = -9999;

	protected static final int STEP_TO_MENU = 10;

	protected static final int STEP_TO_EXIT_MAIN_MENU = 9999;

	protected static final int BAD_ANSWER = -1;

	protected static final int YES = 0;

	protected static final int NO = 1;

	// private static final String INTERACTIVE_PROCESS_PROMPT =
	// "interactiveProcessPrompt";

	private static final String INTERACTIVE_SURFIX = "interactive";

	private static final String INTERACTIVE_PROCESS = "interactiveProcess";

	private static final String INTERACTIVE_ONLINE_PROCESS = "interactiveOnlineProcess";

	private static final String INTERACTIVE_ONLINE_HELPER = "interactiveOnlineHelper";

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
		// String commandName = (String) abCmd.getArgv().get(0);
		// if (GTRobotDispatcher.EXIT_COMMAND.equals(commandName)) {
		// jumpToStep(STEP_TO_EXIT_MAIN_MENU);
		// }
		// if (GTRobotDispatcher.BACK_MENU_COMMAND.equals(commandName)) {
		// jumpToStep(STEP_TO_MENU);
		// }

		while (true) {
			int result = executeOnlineProcess(abCmd);
			if (result >= CONTINUE) {
				if (result > CONTINUE) {
					jumpToStep(result);
				}
				result = executeProcess(abCmd);
			} else {
				abCmd.setOriginMessage(null);
			}
			switch (result) {
			case REPEAT_THIS_STEP:
				UserSessionUtil.storePreviousCommand(abCmd);
				return;
			case CONTINUE: {
				nextStep();
				UserSessionUtil.storePreviousCommand(abCmd);
				return;
			}
			case STEP_TO_EXIT_MAIN_MENU:
				clearInteractiveSessionHolder();
				UserSessionUtil.removePreviousCommand(abCmd);
				return;
			default:
				if (result > CONTINUE) {
					jumpToStep(result);
				} else {
					log.error("Invalid returned step number: " + result);
				}
				continue;
			}
		}
	}

	protected int interactiveOnlineProcess(BaseCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		List<String> cmds = CommonUtils.parseSimpleCommand(cmd
				.getOriginMessage());
		String cmdMsg = cmds.get(0);

		if (".".equalsIgnoreCase(cmdMsg)) {
			msgBuf = executeStepOnlineHelp(cmd);

			sendBackMessage(cmd, msgBuf.toString());
			return REPEAT_THIS_STEP;
		}
		if (".b".equalsIgnoreCase(cmdMsg)) {
			return STEP_TO_MENU;
		}
		if (".x".equalsIgnoreCase(cmdMsg)) {
			return STEP_TO_EXIT_MAIN_MENU;
		}
		return CONTINUE;
	}

	protected StringBuffer interactiveOnlineHelper(BaseCommand cmd) {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("Online command help:").append(endl);
		msgBuf.append("> .  Show this online help.").append(endl);
		msgBuf.append("> .b Back to menu.").append(endl);
		msgBuf.append("> .x Back to main menu.").append(endl);

		return msgBuf;
	}

	protected void initCommandToStepMappings() {
	}

	protected int interactiveProcess(BaseCommand cmd) throws XMPPException {
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
		return STEP_TO_EXIT_MAIN_MENU;
	}

	/**
	 * Menu
	 */
	protected int interactiveProcess_10(BaseCommand cmd) throws XMPPException {
		List<String> menuInfo = new ArrayList<String>();
		prepareMenuInfo(menuInfo);

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

	protected void prepareMenuInfo(List<String> menuInfo) {
	}

	protected int interactiveProcess_11(BaseCommand cmd) throws XMPPException {
		List<String> cmds = CommonUtils.parseSimpleCommand(cmd
				.getOriginMessage());
		String cmdMsg = cmds.get(0);

		Integer step = commandToStepMappings.get(cmdMsg);
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
			if (method.getName().startsWith(INTERACTIVE_SURFIX)) {
				method.setAccessible(true);
				methodTable.put(method.getName(), method);
			}
		}
	}

	protected void addCommandToStepMapping(String cmd, int step) {
		commandToStepMappings.put(cmd, new Integer(step));
	}

	private StringBuffer executeStepOnlineHelp(BaseCommand cmd)
			throws XMPPException {
		// Excute to get the default online helper
		Object result = executeMethod(cmd, INTERACTIVE_ONLINE_HELPER, -1, false);
		StringBuffer buf = (StringBuffer) result;

		result = executeMethod(cmd, INTERACTIVE_ONLINE_HELPER, getStep(), false);

		if (result != null && result instanceof StringBuffer) {
			buf.append(result);
			return buf;
		}
		return buf;
	}

	private int executeOnlineProcess(BaseCommand cmd) throws XMPPException {
		// Excute the default online process
		Object result = executeMethod(cmd, INTERACTIVE_ONLINE_PROCESS, -1,
				false);
		int intResult = ((Integer) result).intValue();
		if (intResult != CONTINUE) {
			return intResult;
		}

		result = executeMethod(cmd, INTERACTIVE_ONLINE_PROCESS, getStep(),
				false);
		if (result == null) {
			return STEP_TO_EXIT_MAIN_MENU;
		} else {
			intResult = ((Integer) result).intValue();
			if (intResult == END_BY_INTERNAL_EXCEPTION)
				return CONTINUE;
			return intResult;
		}
	}

	private int executeProcess(BaseCommand cmd) throws XMPPException {
		Object result = executeMethod(cmd, INTERACTIVE_PROCESS, getStep(), true);
		if (result == null) {
			return STEP_TO_EXIT_MAIN_MENU;
		} else {
			return ((Integer) result).intValue();
		}
	}

	private Object executeMethod(BaseCommand cmd, String methodPrefix,
			int step, boolean defaultProcessEnable) throws XMPPException {
		Object result = null;
		String methodName = methodPrefix;
		if (step != -1) {
			methodName = methodName + "_" + step;
		}
		try {
			Method method = (Method) methodTable.get(methodName);
			if (method == null) {
				if (defaultProcessEnable) {
					log.warn("Invoking the default method! Not found: "
							+ this.getClass().getName() + "." + methodName);
					method = (Method) methodTable.get(methodPrefix);
				} else {
					return END_BY_INTERNAL_EXCEPTION;
				}
			}
			log.debug("Invoking: " + method.getName());
			result = method.invoke(this, new Object[] { cmd });
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			if (targetException instanceof XMPPException)
				throw (XMPPException) targetException;
			else
				log.error("Exception in executeMethod: " + methodName, e);
		} catch (Exception e) {
			log.error("Exception in executeMethod: " + methodName, e);
		}
		return result;
	}
}
