package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;

public class InvalidCommandProcessor extends HelpProcessor {

	public void internalProcess(BaseCommand cmd) throws XMPPException {

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(cmd.getI18NMessage("invalid.command"));
		msgBuf.append(cmd.getOriginMessage());
		msgBuf.append(endl);
		sendBackMessage(cmd, msgBuf.toString());

		super.internalProcess(cmd);
	}

}
