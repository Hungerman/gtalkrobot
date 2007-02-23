package com.gtrobot.commandparser;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.utils.CommandProcessorMapping;
import com.gtrobot.utils.CommonUtils;
import com.gtrobot.utils.UserSession;

public class CommadParser {
	protected static final transient Log log = LogFactory
			.getLog(CommadParser.class);

	private static final String COMMAND_PREFIX_1 = "/";

	private static final String COMMAND_PREFIX_2 = ":";

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
		String jid = StringUtils.parseBareAddress(from);
		BaseCommand command = parse(jid, body);
		command.setFrom(from);
		command.setOriginMessage(body);
		return command;
	}

	/**
	 * Parse the command string and contruct comamnd object
	 * 
	 * @param jid
	 * @param body
	 * @return
	 */
	private static BaseCommand parse(String jid, String body) {
		// if (body == null) {
		// log.info("Warn: message's body is NULL!");
		// return null;
		// }
		// Trim the message body to parse the command prefix
		body = body.trim();
		boolean isCommand = false;
		if (body.startsWith(COMMAND_PREFIX_1)
				|| body.startsWith(COMMAND_PREFIX_2)) {
			isCommand = true;
		}

		// Parse the command and parameters
		List commands = CommonUtils.parseCommand(body, isCommand);
		if (commands == null || commands.size() < 1) {
			return CommandProcessorMapping.getInstance()
					.getInvalidCommandInstance();
		}

		BaseCommand command = null;
		if (!isCommand) {
			// Check if the user is in a interactive operations
			Long step = InteractiveProcessor.getStep(jid);
			if (step != null) {
				command = getPreviousCommand(jid);
			} else {
				// Normal broadcast message
				command = CommandProcessorMapping.getInstance()
						.getBroadcastMessageCommandInstance();
			}
		} else {
			String commandName = ((String) commands.get(0)).toLowerCase();
			// Repeat the previous command
			if (commandName.equals(COMMAND_PREFIX_1)
					|| commandName.equals(COMMAND_PREFIX_2)) {
				command = getPreviousCommand(jid);
			} else {

				try {
					// Construct a new command object according to the command
					// info
					command = CommandProcessorMapping.getInstance()
							.getCommandInstance(commandName);
					if (command == null) {
						command = CommandProcessorMapping.getInstance()
								.getInvalidCommandInstance();
					}
				} catch (Exception e) {
					log.error("Exception in parsing command!", e);
					command = CommandProcessorMapping.getInstance()
							.getInvalidCommandInstance();
				}
			}
		}
		command.setArgv(commands);
		command.setUserEntry(jid);
		if (command instanceof ProcessableCommand) {
			((ProcessableCommand) command).parseArgv(commands);
		}
		return command;
	}

	private static BaseCommand getPreviousCommand(String jid) {
		BaseCommand previousCommand = UserSession.retrievePreviousCommand(jid);
		if (previousCommand == null) {
			previousCommand = CommandProcessorMapping.getInstance()
					.getInvalidCommandInstance();
			previousCommand.setErrorMessage(previousCommand
					.getI18NMessage("invalid.command.previous.command.null"));
		} else {
			previousCommand.setErrorMessage(null);
		}
		return previousCommand;
	}
}
