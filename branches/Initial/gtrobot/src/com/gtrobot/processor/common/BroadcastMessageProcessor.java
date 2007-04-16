package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.AbstractProcessor;

public class BroadcastMessageProcessor extends AbstractProcessor {

	protected void internalProcess(BaseCommand cmd) throws XMPPException {

		UserEntry userEntry = cmd.getUserEntry();

		if (!userEntry.isChattable()) {
			StringBuffer msgBuf = new StringBuffer();

			msgBuf.append(cmd.getI18NMessage("broadcast.error.title"));
			msgBuf.append(endl);
			msgBuf.append(cmd.getI18NMessage("broadcast.error.prompt"));
			msgBuf.append(endl);
			msgBuf.append(cmd.getOriginMessage());

			sendBackMessage(cmd, msgBuf.toString());
		} else {
			broadcastMessage(cmd, cmd.getOriginMessage());
		}
	}

}
