package com.gtrobot.command.common;

import java.util.List;
import java.util.Locale;

import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.utils.CommonUtils;

public class LangCommand extends ProcessableCommand {
	private String operation;

	private Locale locale;

	public void parseArgv(List argv) {
		if (argv.size() != 2) {
			setErrorMessage(getI18NMessage("error.parameter"));
			return;
		}
		operation = ((String) argv.get(1)).trim().toLowerCase();
		locale = CommonUtils.parseLocale(operation);
		if (locale == null) {
			setErrorMessage(getI18NMessage("error.parameter"));
		}

		super.parseArgv(argv);
	}

	public Locale getOperationLocale() {
		return new Locale(operation);
	}

	public String getOperation() {
		return operation;
	}
}
