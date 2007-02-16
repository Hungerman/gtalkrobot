package com.gtrobot.command.common;

import java.util.List;

import com.gtrobot.command.ProcessableCommand;

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
