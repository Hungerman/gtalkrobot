package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.LangCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;

public class LangProcessor extends AbstractProcessor {

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof LangCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}
		StringBuffer msgBuf = new StringBuffer();

		String error = abCmd.getErrorMessage();
		if (error != null) {
			msgBuf.append(abCmd.getI18NMessage("lang.error.title"));
			msgBuf.append(error);
			msgBuf.append(endl);
			msgBuf.append(abCmd.getI18NMessage("lang.command.format"));
			msgBuf.append(endl);
			msgBuf.append(abCmd.getI18NMessage("lang.origin.prompt"));
			msgBuf.append(abCmd.getOriginMessage());
			msgBuf.append(endl);
			sendBackMessage(abCmd, msgBuf.toString());
		}
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		LangCommand cmd = (LangCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();

		UserEntry userEntry = cmd.getUserEntry();
		userEntry.setLocale(cmd.getOperationLocale());

		msgBuf.append(cmd.getI18NMessage("lang.success"));
		msgBuf.append(userEntry.getLocale().getDisplayLanguage(
				userEntry.getLocale()));
		msgBuf.append(endl);

		sendBackMessage(abCmd, msgBuf.toString());
	}

}
