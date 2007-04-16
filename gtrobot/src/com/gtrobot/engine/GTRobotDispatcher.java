package com.gtrobot.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.processor.Processor;
import com.gtrobot.thread.WorkerDispatcher;
import com.gtrobot.utils.CommonUtils;
import com.gtrobot.utils.UserSessionUtil;

public class GTRobotDispatcher {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotDispatcher.class);

	public static final String COMMAND_PREFIX_1 = "/";

	public static final String COMMAND_PREFIX_2 = ":";

	public static final String EXIT_COMMAND = "x";

	public static final String BACK_MENU_COMMAND = "b";

	private String defaultCommand = null;

	private Map<String, String> commandMappings = new HashMap<String, String>();

	public void setDefaultCommand(String defaultCommand) {
		this.defaultCommand = defaultCommand;
	}

	@SuppressWarnings("unchecked")
	public void setCommandMappings(Properties mappings) {
		commandMappings
				.putAll((Map<? extends String, ? extends String>) mappings);
	}

	public void parseAndDispatch(Message message) {
		BaseCommand commnad = parse(message);

		WorkerDispatcher workerDispatcher = GTRobotContextHelper
				.getWorkerDispatcher();
		workerDispatcher.triggerEvent(commnad);
	}

	/**
	 * Parse the message and return a command object
	 * 
	 * @param message
	 * @return
	 */
	private BaseCommand parse(Message message) {
		if (log.isDebugEnabled()) {
			log.debug("Message       from: " + message.getFrom());
			log.debug("                to: " + message.getTo());
			log.debug("          threadId: " + message.getThread());
			log.debug("          packetId: " + message.getPacketID());
			log.debug("              type: " + message.getType().toString());
			Iterator<String> propertyNames = message.getPropertyNames()
					.iterator();
			while (propertyNames.hasNext()) {
				String name = propertyNames.next();
				log.debug("          property: " + name + " : "
						+ message.getProperty(name));
			}
			log.debug("             class: " + message.getClass().getName());
			log.debug("             error: " + message.getError());
			log.debug("           subject: " + message.getSubject());
			log.debug("              body: " + message.getBody());
		}

		String from = message.getFrom();
		String body = message.getBody();
		log.info("Message from <" + from + ">: " + body);
		BaseCommand command = parse(from, body);
		return command;
	}

	/**
	 * Parse the command string and contruct comamnd object
	 * 
	 * @param jid
	 * @param commandStr
	 * @return
	 */
	private BaseCommand parse(String from, String body) {
		// Trim the message body to parse the command prefix
		String jid = StringUtils.parseBareAddress(from);
		String commandStr = body.trim();

		boolean isImmediateCommand = false;
		if (commandStr.startsWith(COMMAND_PREFIX_1)
				|| commandStr.startsWith(COMMAND_PREFIX_2)) {
			isImmediateCommand = true;
		}

		// Parse the command and parameters
		List<String> commands = CommonUtils.parseCommand(commandStr,
				isImmediateCommand);
		if (commands == null || commands.size() < 1) {
			return getDefaultCommand();
		}

		BaseCommand command = null;
		BaseCommand previousCommand = getPreviousCommand(jid);
		if (!isImmediateCommand) {
			command = previousCommand;
		} else {
			String commandType = ((String) commands.get(0)).toLowerCase();
			// Repeat the previous command
			try {
				// Construct a new command object according to the command
				// info
				command = getCommandByType(commandType);
				if (command == null) {
					if (commandType.equals(COMMAND_PREFIX_1)
							|| commandType.equals(COMMAND_PREFIX_2)) {
						command = previousCommand;
					} else if ((commandType.equals(EXIT_COMMAND) || commandType
							.equals(BACK_MENU_COMMAND))) {
						command = previousCommand;
					}
				} else {
					Processor processor = command.getProcessor();
					// When you are in an interactive operations, if you want to
					// enter a new
					// interactive operation, please exit the current operation
					// first.
					if (processor instanceof InteractiveProcessor
							&& previousCommand != null) {
						if (!command.getCommandType().equals(
								previousCommand.getCommandType())) {
							command
									.setErrorMessage("Before you enter a new interactive operation, please step out of current one.");
						} else {
							command.setErrorMessage("You have been here.");
						}
						command.setErrorType(BaseCommand.ErrorType.abnormal);
					}
				}
			} catch (Exception e) {
				log.error("Exception in parsing command!", e);
			}
		}

		if (command == null) {
			command = getDefaultCommand();
		}

		command.setFrom(from);
		command.setOriginMessage(body);
		command.setArgv(commands);
		command.setUserEntry(jid);
		if (command instanceof ProcessableCommand) {
			((ProcessableCommand) command).parseArgv(commands);
		}

		return command;
	}

	private BaseCommand getPreviousCommand(String jid) {
		BaseCommand previousCommand = UserSessionUtil
				.retrievePreviousCommand(jid);
		if (previousCommand != null) {
			previousCommand.setErrorMessage(null);
		}
		return previousCommand;
	}

	private BaseCommand getCommandByType(String commandType) {
		if (commandType == null) {
			return null;
		}
		String commandBeanName = commandMappings.get(commandType);
		if (commandBeanName == null) {
			return null;
		}
		BaseCommand command = getCommandByBeanName(commandBeanName);
		return command;
	}

	private BaseCommand getCommandByBeanName(String commandBeanName) {
		return (BaseCommand) GTRobotContextHelper.getBean(commandBeanName);
	}

	private BaseCommand getDefaultCommand() {
		return getCommandByBeanName(defaultCommand);
	}
}
