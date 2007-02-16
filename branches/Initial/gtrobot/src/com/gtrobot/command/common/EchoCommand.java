package com.gtrobot.command.common;

import java.util.List;

import com.gtrobot.command.ProcessableCommand;

public class EchoCommand extends ProcessableCommand {
	private static final String ON = "on";

	private static final String OFF = "off";

	private String operation;

	public EchoCommand(String jid, List argv) {
		super(jid, argv);
	}

	protected void parseArgv(List argv) {
		if (argv.size() != 2) {
			setErrorMessage(getI18NMessage("echo.error.parameter"));
			return;
		}
		operation = ((String) argv.get(1)).trim().toLowerCase();
		if (!(ON.endsWith(operation) || OFF.equals(operation))) {
			setErrorMessage(getI18NMessage("echo.error.parameter"));
		}

		super.parseArgv(argv);
	}

	public boolean isOperationON() {
		return ON.equals(operation);
	}
}
