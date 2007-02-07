package com.gtrobot.command;

import java.util.List;

public class ProcessableCommand extends AbstractCommand {

	public ProcessableCommand(String jid, List argv) {
		super(jid);

		parseArgv(argv);
	}

	protected void parseArgv(List argv) {
		return;
	}
}
