package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.processor.AbstractProcessor;

public class InvalidCommandProcessor extends AbstractProcessor {

	protected void internalProcess(BaseCommand cmd) throws XMPPException {

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(cmd.getI18NMessage("invalid.command"));
		msgBuf.append(cmd.getOriginMessage());
		sendBackMessage(cmd, msgBuf.toString());
	}

}
