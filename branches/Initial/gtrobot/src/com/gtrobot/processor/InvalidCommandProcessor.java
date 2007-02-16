package com.gtrobot.processor;

import java.util.List;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.InvalidCommand;
import com.gtrobot.exception.CommandMatchedException;

public class InvalidCommandProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof InvalidCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}
		super.beforeProcess(abCmd);
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		InvalidCommand cmd = (InvalidCommand) abCmd;

		StringBuffer msgBuf = new StringBuffer();
		List argv = cmd.getArgv();
		if (argv == null || argv.size() < 1) {
			msgBuf.append(cmd.getI18NMessage("invalid.command.null"));
		} else {
			msgBuf.append(cmd.getI18NMessage("invalid.command"));
			msgBuf.append(cmd.getOriginMessage());
		}

		sendBackMessage(cmd, msgBuf.toString());
	}

}
