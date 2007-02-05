package com.gtrobot.command;

import java.util.List;

public class ProcessableCommand extends AbstractCommand {

	public ProcessableCommand(String user, List argv) {
		super(user);

		parseArgv(argv);
	}

	protected void parseArgv(List argv) {
		return;
	}
}
