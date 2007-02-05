package test.processer;

import org.jivesoftware.smack.XMPPException;

import test.command.AbstractCommand;
import test.command.AwayCommand;
import test.context.UserEntry;
import test.exception.CommandMatchedException;

public class AwayProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abstractCommand)
			throws CommandMatchedException {
		if (!(abstractCommand instanceof AwayCommand)) {
			throw new CommandMatchedException(abstractCommand, this);
		}
		super.beforeProcess(abstractCommand);
	}

	protected void internalProcess(AbstractCommand abstractCommand)
			throws XMPPException {
		// AwayCommand command = (AwayCommand) abstractCommand;

		UserEntry userEntry = abstractCommand.getUserEntry();
		userEntry.setChattable(false);

		StringBuffer messageBuffer = new StringBuffer();
		messageBuffer
				.append(
						"You have set your status with [away], you will not recieve the messages!\n")
				.append(" Please use [/chat] back to chatting.\n");

		sendBackMessage(abstractCommand, messageBuffer.toString());
	}

}
