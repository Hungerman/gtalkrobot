package test.processer;

import java.util.List;

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
		List argv = command.getArgv();
		if (argv == null || argv.size() < 1) {
			messageBuffer.append("Command is NULL!");
		} else {
			messageBuffer.append("[ ");
			for (int i = 0; i < argv.size(); i++) {
				messageBuffer.append((String)argv.get(i)).append(' ');
			}
			messageBuffer.append("] ");
		}
		messageBuffer
				.append("is not recognized as a command!");
		sendBackMessage(abstractCommand, messageBuffer.toString());
	}

}
