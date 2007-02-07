package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.BroadcastMessageCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;

public class BroadcastMessageProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof BroadcastMessageCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}

		super.beforeProcess(abCmd);
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		BroadcastMessageCommand cmd = (BroadcastMessageCommand) abCmd;

		UserEntry userEntry = abCmd.getUserEntry();

		if (!userEntry.isChattable()) {
			StringBuffer msgBuf = new StringBuffer();

			msgBuf.append(cmd.getI18NMessage("broadcast.error.title"));
			msgBuf.append(endl);
			msgBuf.append(cmd.getI18NMessage("broadcast.error.prompt"));
			msgBuf.append(endl);
			msgBuf.append(cmd.getMessageContent());

			sendBackMessage(cmd, msgBuf.toString());
		} else {
			broadcastMessage(cmd, cmd.getMessageContent());
		}
	}

}
