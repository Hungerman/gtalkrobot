package com.gtrobot.command.word;

import java.util.List;

import com.gtrobot.command.ProcessableCommand;

public class SearchWordCommand extends ProcessableCommand {
	private String condition;

	public void parseArgv(List argv) {
		if (argv.size() > 2) {
			setErrorMessage(getI18NMessage("error.parameter"));
			return;
		}
		if (argv.size() == 2) {
			condition = ((String) argv.get(1)).trim();
		}

		super.parseArgv(argv);
	}

	public String getCondition() {
		return condition;
	}
}