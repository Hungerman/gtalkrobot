package test.processer;

import org.jivesoftware.smack.XMPPException;

import test.command.AbstractCommand;
import test.command.BroadcastMessageCommand;
import test.exception.CommandMatchedException;

public class BroadcastMessageProcessor extends AbstractProcessor {
	
	protected void beforeProcess(AbstractCommand abstractCommand)
			throws CommandMatchedException {
		if (!(abstractCommand instanceof BroadcastMessageCommand)) {
			throw new CommandMatchedException(abstractCommand, this);
		}
		
		super.beforeProcess(abstractCommand);
	}

	protected void internalProcess(AbstractCommand abstractCommand) throws XMPPException {
		BroadcastMessageCommand command = (BroadcastMessageCommand) abstractCommand;

		broadcastMessage(command, command.getMessageContent());
	}

}
