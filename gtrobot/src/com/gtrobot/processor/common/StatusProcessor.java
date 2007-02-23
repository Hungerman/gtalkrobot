package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.processor.AbstractProcessor;

public class StatusProcessor extends AbstractProcessor {

	protected void internalProcess(BaseCommand cmd) throws XMPPException {
		UserEntry userEntry = cmd.getUserEntry();

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(cmd.getI18NMessage("status.welcome"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("status.item.jid"));
		msgBuf.append(userEntry.getJid());
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("status.item.nickname"));
		msgBuf.append(userEntry.getNickName());
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("status.item.chattable"));
		msgBuf.append(userEntry.isChattableInPublicRoom());
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("status.item.echoable"));
		msgBuf.append(userEntry.isEchoable());
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("status.item.lang"));
		msgBuf.append(userEntry.getLocale().getDisplayLanguage(
				userEntry.getLocale()));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
	}
}
