package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.common.PrivateMessageCommand;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.processor.AbstractProcessor;

public class PrivateMessageProcessor extends AbstractProcessor {

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		PrivateMessageCommand cmd = (PrivateMessageCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();

		UserEntry targetUserEntry = GlobalContext.getInstance().getUser(
				cmd.getTargetJid());
		if (targetUserEntry == null) {
			msgBuf.append(cmd
					.getI18NMessage("privatemessage.error.targetusernotfound"));
			msgBuf.append(cmd.getTargetJid());
			msgBuf.append(endl);
			msgBuf.append(cmd.getI18NMessage("error.origin.prompt"));
			msgBuf.append(cmd.getOriginMessage());
			msgBuf.append(endl);

			sendBackMessage(abCmd, msgBuf.toString());
		} else {
			UserEntry sender = cmd.getUserEntry();
			msgBuf.append("[");
			msgBuf.append(StringUtils.parseName(sender.getJid()));
			msgBuf.append("]");
			msgBuf.append(sender.getNickName());
			msgBuf.append(" *pm> ");
			msgBuf.append(cmd.getMessageContent());

			sendMessage(msgBuf.toString(), targetUserEntry);
		}
	}

}
