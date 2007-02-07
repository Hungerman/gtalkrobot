package com.gtrobot.command;

import java.util.List;

public class SearchUserCommand extends ProcessableCommand {
	private String condition;

	public SearchUserCommand(String user, List argv) {
		super(user, argv);
	}

	protected void parseArgv(List argv) {
		if (argv.size() > 2) {
			setErrorMessage(getI18NMessage("searchuser.error.parameter"));
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
