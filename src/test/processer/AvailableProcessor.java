package test.processer;

import org.jivesoftware.smack.XMPPException;

import test.command.AbstractCommand;
import test.command.AvailableCommand;
import test.context.UserEntry;
import test.exception.CommandMatchedException;

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
