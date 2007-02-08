package com.gtrobot.exception;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.processor.AbstractProcessor;

public class CommandMatchedException extends Exception {
	private static final long serialVersionUID = 5164473922992670075L;

	private AbstractCommand command;

	private AbstractProcessor processor;

	public CommandMatchedException(AbstractCommand command,
			AbstractProcessor processor) {
		super();
		this.command = command;
		this.processor = processor;
	}

	public AbstractCommand getCommand() {
		return command;
	}

	public AbstractProcessor getProcessor() {
		return processor;
	}
}
