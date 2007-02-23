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

	private Long id;

	private String jid;

	private String nickName;

	private boolean chattableInPublicRoom;

	private boolean echoable;

	private Locale locale;

	private int status;

	public UserEntry(String jid) {
		this.jid = jid;
		this.nickName = StringUtils.parseName(jid);
		this.chattableInPublicRoom = true;
		echoable = false;
		locale = Locale.CHINESE;
		status = UNAVAILABLE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isChattableInPublicRoom() {
		return chattableInPublicRoom;
	}

	public void setChattableInPublicRoom(boolean chattable) {
		this.chattableInPublicRoom = chattable;
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
