package test.command;

import test.context.GlobalContext;
import test.context.UserEntry;

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

}
