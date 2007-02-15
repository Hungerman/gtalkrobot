package com.gtrobot.command;

import java.util.List;

public class PrivateMessageCommand extends ProcessableCommand {
	private String targetJid;

	public PrivateMessageCommand(String jid, List argv) {
		super(jid, argv);
	}

	protected void parseArgv(List argv) {
		if (argv.size() < 3) {
			setErrorMessage(getI18NMessage("privatemessage.error.parameter"));
			return;
		}
		targetJid = ((String) argv.get(1)).trim().toLowerCase();

		super.parseArgv(argv);
	}

	public String getMessageContent() {
		int pos = getOriginMessage().indexOf(targetJid);

		return getOriginMessage().substring(pos + targetJid.length() + 1).trim();
	}

	public String getTargetJid() {
		String jid = targetJid;
		if (jid.indexOf('@') == -1) {
			jid = jid + "@gmail.com";
		}
		return jid;
	}
}
