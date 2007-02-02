package test.processer;

import org.jivesoftware.smack.XMPPException;

import test.command.AbstractCommand;
import test.command.InvalidCommand;
import test.exception.CommandMatchedException;

public class InvalidCommandProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abstractCommand)
			throws CommandMatchedException {
		if (!(abstractCommand instanceof InvalidCommand)) {
			throw new CommandMatchedException(abstractCommand, this);
		}
		super.beforeProcess(abstractCommand);
	}

	protected void internalProcess(AbstractCommand abstractCommand) throws XMPPException {
		InvalidCommand command = (InvalidCommand) abstractCommand;

		StringBuffer messageBuffer = new StringBuffer();
		String[] argv = command.getArgv();
		if (argv == null || argv.length < 1) {
			messageBuffer.append("Command is NULL!");
		} else {
			messageBuffer.append("[ ");
			for (int i = 0; i < argv.length; i++) {
				messageBuffer.append(argv[i]).append(' ');
			}
			messageBuffer.append("] ");
		}
		messageBuffer
				.append("is not recognized as an internal or external command!");
		sendBackMessage(abstractCommand, messageBuffer.toString());
	}

}
