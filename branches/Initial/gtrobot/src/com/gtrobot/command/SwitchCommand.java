package com.gtrobot.command;

import java.util.List;

/**
 * 对于简单的on和off参数类型的命令。
 * 
 * @author Joey
 * 
 */
public class SwitchCommand extends ProcessableCommand {
	private static final String ON = "on";

	private static final String OFF = "off";

	private String operation;

	public void parseArgv(List argv) {
		if (argv.size() != 2) {
			setErrorMessage(getI18NMessage("error.parameter"));
			return;
		}
		operation = ((String) argv.get(1)).trim().toLowerCase();
		if (!(ON.endsWith(operation) || OFF.equals(operation))) {
			setErrorMessage(getI18NMessage("error.parameter"));
		}

		super.parseArgv(argv);
	}

	public boolean isOperationON() {
		return ON.equals(operation);
	}
}
