package test.exception;

import test.command.AbstractCommand;
import test.processer.AbstractProcessor;

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

	public void setCommand(AbstractCommand command) {
		this.command = command;
	}

	public AbstractProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(AbstractProcessor processor) {
		this.processor = processor;
	}

}
