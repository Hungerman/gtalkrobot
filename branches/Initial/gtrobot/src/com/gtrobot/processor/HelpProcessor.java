package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.HelpCommand;
import com.gtrobot.exception.CommandMatchedException;

public class HelpProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abstractCommand)
			throws CommandMatchedException {
		if (!(abstractCommand instanceof HelpCommand)) {
			throw new CommandMatchedException(abstractCommand, this);
		}
		super.beforeProcess(abstractCommand);
	}

	protected void internalProcess(AbstractCommand abstractCommand)
			throws XMPPException {
		HelpCommand command = (HelpCommand) abstractCommand;

		StringBuffer messageBuffer = new StringBuffer();
		messageBuffer.append(endl);
		messageBuffer.append(command.getI18NMessage("help.welcome"));
		messageBuffer.append(endl);
		messageBuffer.append(command.getI18NMessage("help.prompt"));
		messageBuffer.append(endl);
		messageBuffer.append("|--");
		messageBuffer.append(command.getI18NMessage("help.command.help"));
		messageBuffer.append(endl);
		messageBuffer.append("|--");
		messageBuffer.append(command.getI18NMessage("help.command.away"));
		messageBuffer.append(endl);
		messageBuffer.append("|--");
		messageBuffer.append(command.getI18NMessage("help.command.available"));
		messageBuffer.append(endl);
		messageBuffer.append("|--");
		messageBuffer.append(command.getI18NMessage("help.command.echo"));
		messageBuffer.append(endl);
		sendBackMessage(abstractCommand, messageBuffer.toString());
	}

}
