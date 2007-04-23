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
import com.gtrobot.utils.CommonUtils;
import com.gtrobot.utils.UserSessionUtil;

/**
 * 交互式的业务处理基类。
 * <p>
 * 主要提供四类接口方法。<br>
 * interactiveOnlineHelper： 提供onlineHelper的显示，通常只有当收到"."的时候才会执行<br>
 * interactiveOnlineProcess： 提供Online的处理<br>
 * interactiveProcessPrompt： 提供交互的提示信息，返回等待用户输入。<br>
 * interactiveProcess： 提供交互的处理<br>
 * <p>
 * internalProcess的处理流程<br>
 * 1 调用executeOnlineProcess<br>
 * 1.0 如果cmd.isProcessed，则直接返回CONTINUE。否则继续以下处理。<br>
 * 1.1 调用缺省的基础interactiveOnlineProcess处理流程。<br>
 * 1.1.1
 * 如果用户输入的内容是"."，则调用executeStepOnlineHelp(cmd)显示Online的帮助提示。并且设置cmd已经处理标志为true。<br>
 * 1.2 根据step调用对应的interactiveOnlineProcess_step处理流程。<br>
 * 1.2.0 根据对应的输入，进行对应的处理。<br>
 * 2. 调用interactiveProcessPrompt<br>
 * 2.0 如果cmd.isProcessed，显示提示信息后，返回WAIT_INPUT。<br>
 * 3. 调用interactiveProcess<br>
 * 3.0 根据输入的内容，进行对应的处理。<br>
 * 
 * internalProcess中根据对应返回值的处理规则：<br>
 * 1. CONTINUE： 后续内容继续执行，interactiveOnlineProcess，
 * interactiveOnlineHelper，interactiveProcessPrompt，interactiveProcess都执行完成后，递增Step数，执行后一Step的处理。<br>
 * 2. REPEAT_THIS_STEP：讨论中<br>
 * 3. WAIT_INPUT： 中止当前处理，等待用户输入。<br>
 * 5. >0： 返回的是具体的Step数，直接执行对应的Step处理。<br>
 * 
 * @author Joey
 * 
 */
public class InteractiveProcessor extends AbstractProcessor {
	public static final String INTERACTIVE_PROCESSOR_SESSION_KEY = "_ipSessionKey";

	protected static final int CONTINUE = 0;

	protected static final int REPEAT_THIS_STEP = -1;

	protected static final int WAIT_INPUT = -2;

	protected static final int STEP_TO_MENU = 10;

	protected static final int STEP_TO_EXIT_MAIN_MENU = 9990;

	protected static final int STEP_TO_EXITED = 9991;

	protected static final int BAD_ANSWER = -1;

	protected static final int YES = 0;

	protected static final int NO = 1;

	private static final String INTERACTIVE_SURFIX = "interactive";

	private static final String INTERACTIVE_PROCESS = "interactiveProcess";

	private static final String INTERACTIVE_PROCESS_PROMPT = "interactiveProcessPrompt";

	private static final String INTERACTIVE_ONLINE_PROCESS = "interactiveOnlineProcess";

	private static final String INTERACTIVE_ONLINE_HELPER = "interactiveOnlineHelper";

	public static final String STEP_SESSION_KEY = "-step";

	public static final String TEMP_SESSION_KEY = "-temp";

	protected Map<String, Method> methodTable = new Hashtable<String, Method>();

	protected Map<String, Integer> commandToStepMappings = new Hashtable<String, Integer>();

	public InteractiveProcessor() {
		super();
		initMathodTable(this.getClass());
		initMenuComamndToStepMappings();
	}

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		while (true) {
			int result = executeOnlineProcess(abCmd);
			if (result == CONTINUE && abCmd.isProcessed()) {
				result = executeProcessPrompt(abCmd);
			}
			if (result == CONTINUE) {
				result = executeProcess(abCmd);
			}
			abCmd.setProcessed(true);
			switch (result) {
			case WAIT_INPUT:
			case REPEAT_THIS_STEP:
				UserSessionUtil.storePreviousCommand(abCmd);
				return;
			case CONTINUE: {
				nextStep();
				UserSessionUtil.storePreviousCommand(abCmd);
				continue;
			}
			case STEP_TO_EXITED:
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

	/**
	 * 缺省的Online处理。处理了帮助提示"."，退回到菜单".b"，退回到主菜单".x"三个功能。
	 */
	protected int interactiveOnlineProcess(BaseCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		cmd.setInteractiveCommands(CommonUtils.parseSimpleCommand(cmd
				.getOriginMessage()));
		if (cmd.getInteractiveCommands() == null) {
			return CONTINUE;
		}
		String cmdMsg = cmd.getInteractiveCommands().get(0);
		if (cmd.getInteractiveCommands().size() == 1) {
			if (".".equalsIgnoreCase(cmdMsg)) {
				msgBuf = executeStepOnlineHelp(cmd);

				sendBackMessage(cmd, msgBuf.toString());
				cmd.setProcessed(true);
				return CONTINUE;
			}
			if (".b".equalsIgnoreCase(cmdMsg)) {
				return STEP_TO_MENU;
			}
			if (".x".equalsIgnoreCase(cmdMsg)) {
				return STEP_TO_EXIT_MAIN_MENU;
			}
		}
		return CONTINUE;
	}

	/**
	 * 缺省的Online帮助显示。
	 */
	protected StringBuffer interactiveOnlineHelper(BaseCommand cmd) {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("Online command help:").append(endl);
		msgBuf.append("> .  Show this online help.").append(endl);
		msgBuf.append("> .b Back to menu.").append(endl);
		msgBuf.append("> .x Back to main menu.").append(endl);

		return msgBuf;
	}

	protected int interactiveProcess(BaseCommand cmd) throws XMPPException {
		return STEP_TO_MENU;
	}

	/**
	 * Menu
	 */
	protected int interactiveProcessPrompt_10(BaseCommand cmd)
			throws XMPPException {
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
		return WAIT_INPUT;
	}

	protected int interactiveProcess_10(BaseCommand cmd) throws XMPPException {
		String cmdMsg = cmd.getOriginMessage();

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

	/**
	 * STEP_TO_EXIT_MAIN_MENU
	 */
	protected int interactiveProcess_9990(BaseCommand cmd) throws XMPPException {
		return CONTINUE;
	}

	protected int interactiveProcess_9991(BaseCommand cmd) throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		exitInteractiveProcess(cmd);

		msgBuf.append(cmd.getI18NMessage("InteractiveProcessor.exit."
				+ cmd.getCommandType()));
		sendBackMessage(cmd, msgBuf.toString());

		GTRobotContextHelper.getHelpProcessor().internalProcess(cmd);
		return STEP_TO_EXITED;
	}

	protected void exitInteractiveProcess(BaseCommand cmd) throws XMPPException {
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

	/*
	 * 重载父类的方法，添加Step调试信息。
	 */
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

	/**
	 * 检查输入是否为Yes或者No
	 */
	public static int checkAnswer(BaseCommand cmd) {
		String originMessage = cmd.getOriginMessage();
		originMessage = originMessage.trim().toLowerCase();

		if (originMessage.equalsIgnoreCase("yes")
				|| originMessage.equalsIgnoreCase("y")
				|| originMessage.equalsIgnoreCase("ok")) {
			return YES;
		}
		if (originMessage.equalsIgnoreCase("no")
				|| originMessage.equalsIgnoreCase("not")) {
			return NO;
		}
		return BAD_ANSWER;
	}

	/**
	 * 准备Menu的提示信息
	 * 
	 * @param menuInfo
	 */
	protected void prepareMenuInfo(List<String> menuInfo) {
	}

	/**
	 * 初始化Menu对应Command（数字）对应Step的映射关系。
	 */
	protected void initMenuComamndToStepMappings() {
		// commandToStepMappings.put("10", new Integer(1000));
	}

	protected void addCommandToStepMapping(String cmd, int step) {
		commandToStepMappings.put(cmd, new Integer(step));
	}

	/**
	 * 初始化所有符合Step调用规则的方法。根据INTERACTIVE_SURFIX开头的所有的方法。
	 */
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

	/**
	 * 执行OnlineHelp方法，得到OnlineHelp的提示信息。 首先执行基础的方法，然后根据Step执行对应方法，合成提示信息。
	 */
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

	/**
	 * 执行Online处理。首先执行基础的Online处理，然后根据Step执行对应方法。
	 */
	private int executeOnlineProcess(BaseCommand cmd) throws XMPPException {
		if (cmd.isProcessed()) {
			return CONTINUE;
		}

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
			return ((Integer) result).intValue();
		}
	}

	/**
	 * 根据Step执行对应的操作处理。
	 */
	private int executeProcess(BaseCommand cmd) throws XMPPException {
		Object result = executeMethod(cmd, INTERACTIVE_PROCESS, getStep(), true);
		if (result == null) {
			return STEP_TO_EXIT_MAIN_MENU;
		} else {
			return ((Integer) result).intValue();
		}
	}

	/**
	 * 根据Step执行对应的提示操作处理。
	 */
	private int executeProcessPrompt(BaseCommand cmd) throws XMPPException {
		Object result = executeMethod(cmd, INTERACTIVE_PROCESS_PROMPT,
				getStep(), false);
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
					return CONTINUE;
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
