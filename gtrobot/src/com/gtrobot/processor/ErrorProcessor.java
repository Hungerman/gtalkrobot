package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;


public class ErrorProcessor extends AbstractProcessor {

	protected void internalProcess(AbstractCommand abstractCommand)
			throws XMPPException {
		StringBuffer messageBuffer = new StringBuffer();
		messageBuffer.append("ErrorProcessor: ").append(
				abstractCommand.getErrorMessage());
		sendBackMessage(abstractCommand, messageBuffer.toString());
	}

}
