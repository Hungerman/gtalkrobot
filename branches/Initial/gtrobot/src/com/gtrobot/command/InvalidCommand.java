package com.gtrobot.command;

import java.util.List;

public class InvalidCommand extends ProcessableCommand {
	private List argv;

	public InvalidCommand(String jid, List argv) {
		super(jid, argv);
	}

	protected void parseArgv(List argv) {
		this.argv = argv;

		super.parseArgv(argv);
	}

	public List getArgv() {
		return argv;
	}
}
