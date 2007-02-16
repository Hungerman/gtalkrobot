package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.common.AvailableCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;
import com.gtrobot.processor.AbstractProcessor;

public class AvailableProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof AvailableCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}
		super.beforeProcess(abCmd);
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		AvailableCommand cmd = (AvailableCommand) abCmd;

		UserEntry userEntry = cmd.getUserEntry();
		if (!userEntry.isChattable()) {
			userEntry.setChattable(true);
			ctx.updateUser(userEntry.getJid());
		}

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(cmd.getI18NMessage("available.title"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("available.prompt"));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
	}

}
