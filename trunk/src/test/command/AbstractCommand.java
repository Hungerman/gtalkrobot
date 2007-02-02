package test.command;

import java.util.Hashtable;

import test.context.GlobalContext;
import test.context.UserEntry;

public class AbstractCommand {
	private static final String ERROR_MESSAGE ="ERROR_MESSAGE";
	private UserEntry userEntry = null;
	private Hashtable internalData = new Hashtable();

	public AbstractCommand(String user) {
		userEntry = GlobalContext.getInstance().getUser((String) user);
	}

	public UserEntry getUserEntry() {
		return userEntry;
	}

	public void setUserEntry(UserEntry userEntry) {
		this.userEntry = userEntry;
	}

	public Hashtable getInternalData() {
		return internalData;
	}

	public void setInternalData(Hashtable internalData) {
		this.internalData = internalData;
	}
	
	public void setErrorMessage(String msg)
	{
		internalData.put(ERROR_MESSAGE,msg);
	}
	public String getErrorMessage()
	{
		return (String)internalData.get(ERROR_MESSAGE);
	}

}
