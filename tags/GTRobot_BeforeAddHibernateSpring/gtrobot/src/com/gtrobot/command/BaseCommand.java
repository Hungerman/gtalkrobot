package com.gtrobot.command;

import java.util.List;

import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.utils.MessageBundle;

public class BaseCommand {
	private String commandType;

	private UserEntry userEntry;

	private String originMessage;

	private String from;

	private List argv;

	private String errorMessage;

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
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
		setUserEntry(GlobalContext.getInstance().getUser(jid));
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getI18NMessage(String key) {
		return MessageBundle.getInstance().getMessage(key,
				userEntry.getLocale());
	}
}
