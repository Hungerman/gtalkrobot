package com.gtrobot.commandparser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.processor.Processor;
import com.gtrobot.thread.CommandProcessor;
import com.gtrobot.utils.CommandProcessorMapping;
import com.gtrobot.utils.CommonUtils;
import com.gtrobot.utils.UserSession;

public class CommadParser {
	protected static final transient Log log = LogFactory
			.getLog(CommadParser.class);

	public static final String COMMAND_PREFIX_1 = "/";

	public static final String COMMAND_PREFIX_2 = ":";

	public static final String EXIT_COMMAND = "x";

	public static final String BACK_MENU_COMMAND = "b";

	/**
	 * Parse the message and return a command object
	 * 
	 * @param message
	 * @return
	 */
	public static BaseCommand parser(Message message) {
		if (log.isDebugEnabled()) {
			log.debug("Message       from: " + message.getFrom());
			log.debug("                to: " + message.getTo());
			log.debug("          threadId: " + message.getThread());
			log.debug("          packetId: " + message.getPacketID());
			log.debug("              type: " + message.getType().toString());
			Iterator propertyNames = message.getPropertyNames();
			while (propertyNames.hasNext()) {
				String name = (String) propertyNames.next();
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
	private static BaseCommand parse(String from, String body) {
		// if (body == null) {
		// log.info("Warn: message's body is NULL!");
		// return null;
		// }
		// Trim the message body to parse the command prefix
		String jid = StringUtils.parseBareAddress(from);
		String commandStr = body.trim();

		boolean isGlobalCommand = false;
		if (commandStr.startsWith(COMMAND_PREFIX_1)
				|| commandStr.startsWith(COMMAND_PREFIX_2)) {
			isGlobalCommand = true;
		}

		// Parse the command and parameters
		List commands = CommonUtils.parseCommand(commandStr, isGlobalCommand);
		if (commands == null || commands.size() < 1) {
			return CommandProcessorMapping.getInstance()
					.getInvalidCommandInstance();
		}

		BaseCommand command = null;
		String previousCommandType = GlobalContext.getInstance().getUser(jid)
				.getCommandType();
		BaseCommand previousCommand = getPreviousCommand(jid);
		if (!isGlobalCommand) {
			// Check if the user is in a interactive operations
			if (previousCommandType != null) {
				command = previousCommand;
			}
		} else {
			String commandName = ((String) commands.get(0)).toLowerCase();
			// Repeat the previous command
			try {
				// Construct a new command object according to the command
				// info
				command = CommandProcessorMapping.getInstance()
						.getCommandInstance(commandName);
				if (command == null) {
					if (commandName.equals(COMMAND_PREFIX_1)
							|| commandName.equals(COMMAND_PREFIX_2)) {
						command = previousCommand;
					} else if ((commandName.equals(EXIT_COMMAND) || commandName
							.equals(BACK_MENU_COMMAND))
							&& (previousCommandType != null)) {
						command = previousCommand;
					}
				}
			} catch (Exception e) {
				log.error("Exception in parsing command!", e);
			}
		}

		if (command == null) {
			command = CommandProcessorMapping.getInstance()
					.getInvalidCommandInstance();
			if (previousCommandType != null && previousCommand == null) {
				GlobalContext.getInstance().getUser(jid).setCommandType(null);
				command.setErrorMessage("Long time no actions.");
			}
		}

		command.setFrom(from);
		command.setOriginMessage(body);
		command.setArgv(commands);
		command.setUserEntry(jid);
		if (command instanceof ProcessableCommand) {
			((ProcessableCommand) command).parseArgv(commands);
		}
		Processor processor = CommandProcessorMapping.getInstance()
				.getProcessor(command.getCommandType());
		if (processor instanceof InteractiveProcessor) {
			command.getUserEntry().setCommandType(command.getCommandType());
			if (previousCommandType != null
					&& !command.getCommandType().equals(previousCommandType)
					&& previousCommand != null) {
				ArrayList argv = new ArrayList();
				argv.add(EXIT_COMMAND);
				previousCommand.setArgv(argv);
				CommandProcessor.getInstance().triggerCommand(previousCommand);
			}
		}
		return command;
	}

	private static BaseCommand getPreviousCommand(String jid) {
		BaseCommand previousCommand = UserSession.retrievePreviousCommand(jid);
		if (previousCommand != null) {
			previousCommand.setErrorMessage(null);
		}
		return previousCommand;
	}
}
