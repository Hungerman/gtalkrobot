package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.PrivateMessageCommand;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;

public class PrivateMessageProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof PrivateMessageCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}
		StringBuffer msgBuf = new StringBuffer();

		String error = abCmd.getErrorMessage();
		if (error != null) {
			msgBuf.append(abCmd.getI18NMessage("privatemessage.error.title"));
			msgBuf.append(error);
			msgBuf.append(endl);
			msgBuf
					.append(abCmd
							.getI18NMessage("privatemessage.command.format"));
			msgBuf.append(endl);
			msgBuf.append(abCmd.getI18NMessage("privatemessage.origin.prompt"));
			msgBuf.append(abCmd.getOriginMessage());
			msgBuf.append(endl);

			sendBackMessage(abCmd, msgBuf.toString());
		}
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		PrivateMessageCommand cmd = (PrivateMessageCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();

		UserEntry targetUserEntry = GlobalContext.getInstance().getUser(
				cmd.getTargetJid());
		if (targetUserEntry == null) {
			msgBuf.append(cmd
					.getI18NMessage("privatemessage.error.targetusernotfound"));
			msgBuf.append(cmd.getTargetJid());
			msgBuf.append(endl);
			msgBuf.append(cmd.getI18NMessage("privatemessage.origin.prompt"));
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
