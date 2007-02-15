package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.StatusCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;

public class StatusProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof StatusCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}
		super.beforeProcess(abCmd);
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		StatusCommand cmd = (StatusCommand) abCmd;
		UserEntry userEntry = cmd.getUserEntry();

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(cmd.getI18NMessage("status.welcome"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("help.item.jid"));
		msgBuf.append(userEntry.getJid());
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("help.item.nickname"));
		msgBuf.append(userEntry.getNickName());
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("help.item.chattable"));
		msgBuf.append(userEntry.isChattable());
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("help.item.echoable"));
		msgBuf.append(userEntry.isEchoable());
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("help.item.lang"));
		msgBuf.append(userEntry.getLocale().getDisplayLanguage(
				userEntry.getLocale()));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
	}

}
