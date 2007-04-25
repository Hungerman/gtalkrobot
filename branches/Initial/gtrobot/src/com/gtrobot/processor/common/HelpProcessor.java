package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.utils.UserSessionUtil;

public class HelpProcessor extends AbstractProcessor {

	public void internalProcess(BaseCommand cmd) throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		showHelpInfo(msgBuf);

		String commandType = UserSessionUtil.isInInteractiveOperation(cmd
				.getUserEntry().getJid());
		if (commandType != null) {
			msgBuf.append(endl);

			msgBuf.append(getI18NMessage("status.item.interactive."
					+ commandType));
			msgBuf.append(endl);

			msgBuf.append(getI18NMessage("help.interactive.help"));
			msgBuf.append(endl);
		}

		sendBackMessage(cmd, msgBuf.toString());
	}

	public static void showHelpInfo(StringBuffer msgBuf) {
		msgBuf.append(getI18NMessage("help.welcome",
				new Object[] { getUserEntryHolder().getNickName() }));

		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.global.prompt"));
		msgBuf.append(getI18NMessage("help.immediate.prompt"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.help"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.lang"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.status"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.privatemessage"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.changenickname"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.echo"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.searchuser"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.global.prompt"));
		msgBuf.append(getI18NMessage("help.interactive.prompt"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.roomchat"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.studyword"));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("help.command.wordmanagement"));
		msgBuf.append(endl);
	}

}
