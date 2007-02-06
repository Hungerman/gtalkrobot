package com.gtrobot.context;

import java.io.Serializable;
import java.util.Locale;

import org.jivesoftware.smack.util.StringUtils;

public class UserEntry implements Serializable {
	private static final long serialVersionUID = -9113870554641563860L;

	private String user;

	private String nickName;

	private boolean chattable;

	private boolean echoable;

	private Locale locale;

	public UserEntry(String user) {
		this.user = user;
		this.nickName = StringUtils.parseName(user);
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
