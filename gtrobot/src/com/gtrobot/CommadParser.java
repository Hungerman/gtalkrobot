package com.gtrobot;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.common.BroadcastMessageCommand;
import com.gtrobot.command.common.InvalidCommand;
import com.gtrobot.utils.ParameterTable;
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
	public static AbstractCommand parser(Message message) {
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
		AbstractCommand command = parse(jid, body);
		if (command != null) {
			command.setOriginMessage(body);
			command.setFrom(from);
		}
		return command;
	}

	/**
	 * Parse the command string and contruct comamnd object
	 * 
	 * @param user
	 * @param body
	 * @return
	 */
	private static AbstractCommand parse(String user, String body) {
		if (body == null) {
			log.info("Warn: message's body is NULL!");
			return null;
		}
		// Trim the message body to parse the command prefix
		body = body.trim();
		if (!(body.startsWith(COMMAND_PREFIX_1) || body
				.startsWith(COMMAND_PREFIX_2))) {
			// Normal broadcast message
			return new BroadcastMessageCommand(user, body);
		}

		// Parse the command and parameters
		List commands = parseCommand(body);
		if (commands == null || commands.size() < 1) {
			return new InvalidCommand(user, null);
		}

		String commandName = ((String) commands.get(0)).toLowerCase();
		// Repeat the previous command
		if (commandName.equals(COMMAND_PREFIX_1)
				|| commandName.equals(COMMAND_PREFIX_2)) {
			AbstractCommand previousCommand = UserSession
					.retrievePreviousCommand(user);
			if (previousCommand == null) {
				previousCommand = new InvalidCommand(user, null);
				previousCommand
						.setErrorMessage(previousCommand
								.getI18NMessage("invalid.command.previous.command.null"));
			}else
			{
				previousCommand.setErrorMessage(null);
			}
			return previousCommand;
		}

		Class commandClass = (Class) ParameterTable.getCommadMappings().get(
				commandName);
		if (commandClass == null) {
			return new InvalidCommand(user, null);
		}
		try {
			// Construct a new command object according to the command info
			Constructor constructor = commandClass.getConstructor(new Class[] {
					String.class, List.class });
			return (AbstractCommand) constructor.newInstance(new Object[] {
					user, commands });
		} catch (Exception e) {
			e.printStackTrace();
			return new InvalidCommand(user, null);
		}
	}

	/**
	 * Parse the command message body into String List
	 * 
	 * @param body
	 * @return
	 */
	private static List parseCommand(String body) {
		List results = new ArrayList();
		StringBuffer tempStr = new StringBuffer();
		for (int i = 1; i < body.length(); i++) {
			char cc = body.charAt(i);

			if (cc == ' ' || cc == '\t' || cc == '\n' || cc == '\r') {
				if (tempStr.length() == 0) {
					continue;
				} else {
					results.add(tempStr.toString());
					tempStr = new StringBuffer();
				}
			} else {
				tempStr.append(cc);
			}
		}
		if (tempStr.length() != 0) {
			results.add(tempStr.toString());
		}
		return results;
	}
}
