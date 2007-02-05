package test.context;

import java.io.Serializable;

public class UserEntry implements Serializable {
	private static final long serialVersionUID = -9113870554641563860L;

	private String user;

	private String nickName;

	private boolean chattable;

	private boolean echoable;

	public UserEntry(String user) {
		this.user = user;
		this.nickName = user;
		this.chattable = true;
		echoable = false;
	}

	public boolean isChattable() {
		return chattable;
	}

	public void setChattable(boolean chattable) {
		this.chattable = chattable;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public boolean isEchoable() {
		return echoable;
	}

	public void setEchoable(boolean echoable) {
		this.echoable = echoable;
	}

	// public RosterEntry getRosterEntry() {
	// return rosterEntry;
	// }
	// public void setRosterEntry(RosterEntry rosterEntry) {
	// this.rosterEntry = rosterEntry;
	// }
}
