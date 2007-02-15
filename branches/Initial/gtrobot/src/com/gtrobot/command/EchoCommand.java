package com.gtrobot.command;

import java.util.List;

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
