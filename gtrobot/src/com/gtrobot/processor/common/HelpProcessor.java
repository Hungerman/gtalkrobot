package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.processor.AbstractProcessor;

public class HelpProcessor extends AbstractProcessor {

	public void internalProcess(BaseCommand cmd) throws XMPPException {
		String prefix = "";
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(cmd.getI18NMessage("help.welcome"));
		// msgBuf.append(endl);
		// msgBuf.append(cmd.getI18NMessage("help.command.broadcast"));
		msgBuf.append(endl);
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.prompt"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.help"));
		msgBuf.append(endl);
		// msgBuf.append(prefix);
		// msgBuf.append(cmd.getI18NMessage("help.command.publicroom"));
		// msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.echo"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.lang"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.searchuser"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.status"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.privatemessage"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.roomchat"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.studyjpword"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.exit"));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
	}

}
