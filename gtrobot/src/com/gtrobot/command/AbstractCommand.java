package com.gtrobot.command;


import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.utils.MessageBundle;

public class AbstractCommand {
	private UserEntry userEntry;

	private String originMessage;

	private String errorMessage;

	public AbstractCommand(String user) {
		setUserEntry(GlobalContext.getInstance().getUser((String) user));
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

	public UserEntry getUserEntry() {
		return userEntry;
	}

	public void setUserEntry(UserEntry userEntry) {
		this.userEntry = userEntry;
	}

	public String getI18NMessage(String key) {
		return MessageBundle.getInstance().getMessage(key, userEntry.getLocale());
	}

}
