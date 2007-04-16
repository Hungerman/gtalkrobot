package com.gtrobot.command;

import java.util.List;

import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.Processor;
import com.gtrobot.utils.MessageHelper;

public class BaseCommand {
	private Processor processor;

	private String commandType;

	private UserEntry userEntry;

	private String originMessage;

	private String from;

	private List argv;

	private ErrorType errorType = ErrorType.normal;

	private String errorMessage;

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOriginMessage() {
		return originMessage;
	}

	public void setOriginMessage(String orginMessage) {
		this.originMessage = orginMessage;
	}

	public List getArgv() {
		return argv;
	}

	public void setArgv(List argvs) {
		this.argv = argvs;
	}

	public UserEntry getUserEntry() {
		return userEntry;
	}

	public void setUserEntry(UserEntry userEntry) {
		this.userEntry = userEntry;
	}

	public void setUserEntry(String jid) {
		setUserEntry(GTRobotContextHelper.getUserEntryService().getUserEntry(
				jid));
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getI18NMessage(String key) {
		return MessageHelper.getMessage(key, userEntry.getLocale());
	}

	public enum ErrorType {
		/**
		 * Nornal error type. When show the error message, will show the command
		 * relation information
		 */
		normal,
		/**
		 * Abnormal error.
		 */
		abnormal
	}
}
