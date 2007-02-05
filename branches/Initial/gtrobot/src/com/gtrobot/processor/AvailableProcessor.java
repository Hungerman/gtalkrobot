package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.AvailableCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;


public class AvailableProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abstractCommand)
			throws CommandMatchedException {
		if (!(abstractCommand instanceof AvailableCommand)) {
			throw new CommandMatchedException(abstractCommand, this);
		}
		super.beforeProcess(abstractCommand);
	}

	protected void internalProcess(AbstractCommand abstractCommand)
			throws XMPPException {
		// AvailableCommand command = (AvailableCommand) abstractCommand;

		UserEntry userEntry = abstractCommand.getUserEntry();
		userEntry.setChattable(true);

		StringBuffer messageBuffer = new StringBuffer();
		messageBuffer
				.append("Welcome back to chatting!\n")
				.append(
						" If you do not want chatting for a while, please use [/nochat] to stop chatting.\n");

		sendBackMessage(abstractCommand, messageBuffer.toString());
	}

}
