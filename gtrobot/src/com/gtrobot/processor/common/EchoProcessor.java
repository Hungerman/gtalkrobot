package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.SwitchCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.AbstractProcessor;

public class EchoProcessor extends AbstractProcessor {

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		SwitchCommand cmd = (SwitchCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();

		UserEntry userEntry = cmd.getUserEntry();

		if (cmd.isOperationON() != userEntry.isEchoable()) {
			userEntry.setEchoable(cmd.isOperationON());
			GTRobotContextHelper.getUserEntryService().saveUserEntry(userEntry);
		}
		if (cmd.isOperationON()) {
			msgBuf.append(cmd.getI18NMessage("echo.success.on"));
		} else {
			msgBuf.append(cmd.getI18NMessage("echo.success.off"));
		}
		sendBackMessage(abCmd, msgBuf.toString());
	}

}
