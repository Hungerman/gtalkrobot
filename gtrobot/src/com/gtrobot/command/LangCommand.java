package com.gtrobot.command;

import java.util.List;
import java.util.Locale;

public class LangCommand extends ProcessableCommand {
	private static final String EN = "en";

	private static final String JP = "jp";

	private static final String ZH = "zh";

	private String operation;

	public LangCommand(String jid, List argv) {
		super(jid, argv);
	}

	protected void parseArgv(List argv) {
		if (argv.size() != 2) {
			setErrorMessage(getI18NMessage("lang.error.parameter"));
			return;
		}
		operation = ((String) argv.get(1)).trim().toLowerCase();
		if (!(EN.endsWith(operation) || JP.equals(operation) || ZH
				.equals(operation))) {
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
