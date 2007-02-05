package test.processer;

import org.jivesoftware.smack.XMPPException;

import test.command.AbstractCommand;

public class ErrorProcessor extends AbstractProcessor {

	protected void internalProcess(AbstractCommand abstractCommand)
			throws XMPPException {
		StringBuffer messageBuffer = new StringBuffer();
		messageBuffer.append("ErrorProcessor: ").append(
				abstractCommand.getErrorMessage());
		sendBackMessage(abstractCommand, messageBuffer.toString());
	}

}
