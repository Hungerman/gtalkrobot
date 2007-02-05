package test.processer;

import org.jivesoftware.smack.XMPPException;

import test.command.AbstractCommand;
import test.command.HelpCommand;
import test.exception.CommandMatchedException;

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
		// HelpCommand command = (HelpCommand) abstractCommand;

		StringBuffer messageBuffer = new StringBuffer();
		messageBuffer.append("Welcome to gtalkbot!\n").append("\n").append(
				"Commands:\n").append("\t[ /help ] show this message").append(
				"\t[ /nochat|away ] set to away").append(
				"\t[ /chat|available ] set to available").append(
				"\t[ /echo [on|off] ] set to available");

		sendBackMessage(abstractCommand, messageBuffer.toString());
	}

}
