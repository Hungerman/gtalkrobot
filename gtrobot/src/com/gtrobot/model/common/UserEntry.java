package com.gtrobot.model.common;

import java.util.Locale;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.model.BaseObject;

/**
 * User entry model.
 * 
 * @author sunyuxin
 * @hibernate.class table="USER_ENTRY"
 */
public class UserEntry extends BaseObject {
	private static final long serialVersionUID = 7767511322988084969L;

	private Long userId;

	private String jid;

	private String nickName;

	private boolean sysMessageEnable = true;

	private boolean chattable = true;

	private boolean echoable = false;

	private Locale locale = Locale.CHINESE;

	/**
	 * @hibernate.id column="USER_ID" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="SEQ_USER_ENTRY"
	 * @hibernate.generator-param name="identity"
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long id) {
		this.userId = id;
		if (nickName == null) {
			this.nickName = StringUtils.parseName(jid);
		}
	}

	/**
	 * @hibernate.property column="SYS_MSG_ENABLE" not-null="true"
	 */
	public boolean isSysMessageEnable() {
		return sysMessageEnable;
	}

	public void setSysMessageEnable(boolean sysMessageEnable) {
		this.sysMessageEnable = sysMessageEnable;
	}

	/**
	 * @hibernate.property column="CHATABLE" not-null="true"
	 */
	public boolean isChattable() {
		return chattable;
	}

	public void setChattable(boolean chattable) {
		this.chattable = chattable;
	}

	/**
	 * @hibernate.property column="JID" length="100" not-null="true"
	 *                     unique="true"
	 */
	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	/**
	 * @hibernate.property column="NICK_NAME" length="100" not-null="true"
	 *                     unique="true"
	 */
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @hibernate.property column="ECHOABLE"
	 */
	public boolean isEchoable() {
		return echoable;
	}

	public void setEchoable(boolean echoable) {
		this.echoable = echoable;
	}

	/**
	 * @hibernate.property column="LOCALE" length="3" not-null="true"
	 */
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserEntry))
			return false;

		final UserEntry target = (UserEntry) o;

		return !(userId != null ? !userId.equals(target.userId)
				: target.userId != null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (userId != null ? userId.hashCode() : 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				this.jid).toString();
	}
}
