package com.gtrobot.command.common;

import java.util.List;
import java.util.Locale;

import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.utils.CommonUtils;

public class LangCommand extends ProcessableCommand {
	private String operation;

	private Locale locale;

	public LangCommand(String jid, List argv) {
		super(jid, argv);
	}

	protected void parseArgv(List argv) {
		if (argv.size() != 2) {
			setErrorMessage(getI18NMessage("lang.error.parameter"));
			return;
		}
		operation = ((String) argv.get(1)).trim().toLowerCase();
		locale = CommonUtils.parseLocale(operation);
		if (locale == null) {
			setErrorMessage(getI18NMessage("lang.error.parameter"));
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
