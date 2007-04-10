package com.gtrobot.processor.word;

import java.text.DateFormat;
import java.util.Date;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.word.InteractiveSampleCommand;
import com.gtrobot.processor.InteractiveProcessor;

public class InteractiveProcessorSample extends InteractiveProcessor {

	public InteractiveProcessorSample() {
		super("-InteractiveProcessorSample");
	}

	protected void interactiveBeforeProcess_0(InteractiveSampleCommand cmd)
			throws XMPPException {
		// Check the command and set the error message
	}

	protected void interactiveProcess_0(InteractiveSampleCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		// UserSearchFilter userSearchFilter = (UserSearchFilter) UserSession
		// .getSession(cmd.getUserEntry().getJid(), sessionKey);

		msgBuf.append(getStep());
		msgBuf.append("-step: ");
		msgBuf.append(" this step, set the current date into session.");
		msgBuf.append(endl);

		setSession(new Date());

		sendBackMessage(cmd, msgBuf.toString());
	}

	protected void interactiveProcess_1(InteractiveSampleCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		// UserSearchFilter userSearchFilter = (UserSearchFilter) UserSession
		// .getSession(cmd.getUserEntry().getJid(), sessionKey);

		Date date = (Date) getSession();
		msgBuf.append(getStep());
		msgBuf.append("-step");
		msgBuf.append(": the previous step set date: ");
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, cmd
				.getUserEntry().getLocale());
		msgBuf.append(df.format(date));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
	}
}
