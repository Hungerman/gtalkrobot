package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.common.AwayCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;
import com.gtrobot.processor.AbstractProcessor;

public class AwayProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof AwayCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}
		super.beforeProcess(abCmd);
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		AwayCommand cmd = (AwayCommand) abCmd;

		UserEntry userEntry = abCmd.getUserEntry();
		if (userEntry.isChattable()) {
			userEntry.setChattable(false);
			ctx.updateUser(userEntry.getJid());
		}

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(cmd.getI18NMessage("away.title"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("away.prompt"));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
	}

}
