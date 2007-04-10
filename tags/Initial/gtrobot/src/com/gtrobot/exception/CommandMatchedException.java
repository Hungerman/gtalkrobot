package com.gtrobot.exception;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.processor.AbstractProcessor;

public class CommandMatchedException extends Exception {
	private static final long serialVersionUID = 5164473922992670075L;

	private BaseCommand command;

	private AbstractProcessor processor;

	public CommandMatchedException(BaseCommand command,
			AbstractProcessor processor) {
		super();
		this.command = command;
		this.processor = processor;
	}

	public BaseCommand getCommand() {
		return command;
	}

	public AbstractProcessor getProcessor() {
		return processor;
	}
}
