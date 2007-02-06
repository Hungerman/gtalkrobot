package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.HelpCommand;
import com.gtrobot.exception.CommandMatchedException;

public class HelpProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof HelpCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}
		super.beforeProcess(abCmd);
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		HelpCommand cmd = (HelpCommand) abCmd;

		String prefix = "#> ";
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(cmd.getI18NMessage("help.welcome"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("help.prompt"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.help"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.away"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.available"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.echo"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.lang"));
		msgBuf.append(endl);
		msgBuf.append(prefix);
		msgBuf.append(cmd.getI18NMessage("help.command.searchuser"));
		msgBuf.append(endl);
		sendBackMessage(cmd, msgBuf.toString());
	}

}
