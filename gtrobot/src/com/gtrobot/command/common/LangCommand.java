package com.gtrobot.command.common;

import java.util.List;
import java.util.Locale;

import com.gtrobot.command.ErrorType;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.utils.CommonUtils;

/**
 * 设置Locale的命令对象。
 * 
 * @author Joey
 * 
 */
public class LangCommand extends ProcessableCommand {
	private String operation;

	private Locale locale;

	public void parseArgv(List argv) {
		if (argv.size() != 2) {
			setError(ErrorType.wrongParameter);
			return;
		}
		operation = ((String) argv.get(1)).trim().toLowerCase();
		locale = CommonUtils.parseLocale(operation);
		if (locale == null) {
			setError(ErrorType.wrongParameter);
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
