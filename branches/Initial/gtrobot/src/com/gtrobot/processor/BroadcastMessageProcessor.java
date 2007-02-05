package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.BroadcastMessageCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;


public class BroadcastMessageProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abstractCommand)
			throws CommandMatchedException {
		if (!(abstractCommand instanceof BroadcastMessageCommand)) {
			throw new CommandMatchedException(abstractCommand, this);
		}

		super.beforeProcess(abstractCommand);
	}

	protected void internalProcess(AbstractCommand abstractCommand)
			throws XMPPException {
		BroadcastMessageCommand command = (BroadcastMessageCommand) abstractCommand;

		UserEntry userEntry = abstractCommand.getUserEntry();

		if (!userEntry.isChattable()) {
			StringBuffer messageBuffer = new StringBuffer();
			messageBuffer
					.append(
							"Sorry, your message was not deliveried as you are [away] of chatting now!\n")
					.append(" Please use [/chat] back to chatting.\n").append(
							"\nYour Message:\n\t").append(
							command.getMessageContent());
		} else {
			broadcastMessage(command, command.getMessageContent());
		}
	}

}
