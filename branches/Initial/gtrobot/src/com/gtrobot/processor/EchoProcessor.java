package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.EchoCommand;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;

public class EchoProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof EchoCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}
		StringBuffer msgBuf = new StringBuffer();

		String error = abCmd.getErrorMessage();
		if (error != null) {
			msgBuf.append(abCmd.getI18NMessage("echo.error.title"));
			msgBuf.append(error);
			msgBuf.append(endl);
			msgBuf.append(abCmd.getI18NMessage("echo.command.format"));
			msgBuf.append(endl);
			msgBuf.append(abCmd.getI18NMessage("echo.origin.prompt"));
			msgBuf.append(abCmd.getOriginMessage());
			msgBuf.append(endl);

			sendBackMessage(abCmd, msgBuf.toString());
		}
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		EchoCommand cmd = (EchoCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();

		UserEntry userEntry = cmd.getUserEntry();

		if (cmd.isOperationON() != userEntry.isEchoable()) {
			userEntry.setEchoable(cmd.isOperationON());
			GlobalContext.getInstance().saveUser(userEntry);
		}
		if (cmd.isOperationON()) {			
			msgBuf.append(cmd.getI18NMessage("echo.success.on"));
		} else {
			msgBuf.append(cmd.getI18NMessage("echo.success.off"));
		}		
		sendBackMessage(abCmd, msgBuf.toString());
	}

}
