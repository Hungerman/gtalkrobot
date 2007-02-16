package com.gtrobot.processor.word;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.word.AddWordEntryCommand;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.utils.UserSession;

public class AddWordEntryProcessor extends InteractiveProcessor {

	public AddWordEntryProcessor() {
		super(2);
	}

	protected void interactiveBeforeProcess_0(AddWordEntryCommand cmd)
			throws XMPPException {
		// Check the command and set the error message
	}

	protected void interactiveProcess_0(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		String sessionKey = "-wordEntry";
		// UserSearchFilter userSearchFilter = (UserSearchFilter) UserSession
		// .getSession(cmd.getUserEntry().getJid(), sessionKey);

		msgBuf.append(UserSession.getSession(cmd.getUserEntry().getJid(),
				"-step")
				+ "-step");
		msgBuf.append(endl);
		sendBackMessage(cmd, msgBuf.toString());
	}

	protected void interactiveBeforeProcess_1(AddWordEntryCommand cmd)
			throws XMPPException {
		// Check the command and set the error message
	}

	protected void interactiveProcess_1(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		String sessionKey = "-wordEntry";
		// UserSearchFilter userSearchFilter = (UserSearchFilter) UserSession
		// .getSession(cmd.getUserEntry().getJid(), sessionKey);

		msgBuf.append(UserSession.getSession(cmd.getUserEntry().getJid(),
				"-step")
				+ "-step");
		msgBuf.append(endl);
		sendBackMessage(cmd, msgBuf.toString());
	}
}
