package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.common.LangCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.processor.AbstractProcessor;

public class LangProcessor extends AbstractProcessor {

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
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
