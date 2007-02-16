package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.processor.AbstractProcessor;

public class ErrorProcessor extends AbstractProcessor {

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(abCmd.getI18NMessage("error.prompt"));
		msgBuf.append(abCmd.getErrorMessage());

		sendBackMessage(abCmd, msgBuf.toString());
	}

}
