package com.gtrobot.context;

import java.io.Serializable;
import java.util.Locale;

import org.jivesoftware.smack.util.StringUtils;

/**
 * User entry model.
 * 
 * @author sunyuxin
 * 
 */
public class UserEntry implements Serializable {
	private static final long serialVersionUID = -9113870554641563860L;
	public static final int AVAILABLE = 0;
	public static final int UNAVAILABLE = 1;

	private long id;

	private String jid;

	private String nickName;

	private boolean chattable;

	private boolean echoable;

	private Locale locale;
	
	private int status;

	public UserEntry(String jid) {
		this.jid = jid;
		this.nickName = StringUtils.parseName(jid);
		this.chattable = true;
		echoable = false;
		locale = Locale.CHINESE;
		status = UNAVAILABLE;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isChattable() {
		return chattable;
	}

	public void setChattable(boolean chattable) {
		this.chattable = chattable;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
