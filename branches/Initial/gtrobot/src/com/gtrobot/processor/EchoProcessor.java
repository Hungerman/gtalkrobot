package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.EchoCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;


public class EchoProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abstractCommand)
			throws CommandMatchedException {
		if (!(abstractCommand instanceof EchoCommand)) {
			throw new CommandMatchedException(abstractCommand, this);
		}
		super.beforeProcess(abstractCommand);
	}

	protected void internalProcess(AbstractCommand abstractCommand)
			throws XMPPException {
		EchoCommand command = (EchoCommand) abstractCommand;
		StringBuffer messageBuffer = new StringBuffer();

		UserEntry userEntry = command.getUserEntry();
		String error = command.getErrorMessage();
		if (error != null) {
			messageBuffer.append("Command format error: ").append(error)
					.append("\n").append("Should be:\n    /echo [on|off]\n")
					.append("\nYour command: \n    ").append(
							command.getOriginMessage()).append("\n");
			sendBackMessage(abstractCommand, messageBuffer.toString());
			return;
		}

		if (command.isOperationON()) {
			userEntry.setEchoable(true);
			messageBuffer.append("Echo ON!\n");
		} else {
			userEntry.setEchoable(false);
			messageBuffer.append("Echo OFF!\n");
		}
		sendBackMessage(abstractCommand, messageBuffer.toString());
	}

}
