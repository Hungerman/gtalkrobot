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

    private boolean echoable = false;

    private AccountType accountType = AccountType.user;

    private Locale locale = Locale.ENGLISH;

    /**
     * @hibernate.id column="USER_ID" generator-class="native"
     * @hibernate.generator-param name="sequence" value="SEQ_USER_ENTRY"
     * @hibernate.generator-param name="identity"
     */
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(final Long id) {
        this.userId = id;
        if (this.nickName == null) {
            this.nickName = StringUtils.parseName(this.jid);
        }
    }

    /**
     * @hibernate.property column="SYS_MSG_ENABLE" not-null="true"
     */
    public boolean isSysMessageEnable() {
        return this.sysMessageEnable;
    }

    public void setSysMessageEnable(final boolean sysMessageEnable) {
        this.sysMessageEnable = sysMessageEnable;
    }

    /**
     * @hibernate.property column="JID" length="100" not-null="true"
     *                     unique="true" index="ind_user_jid"
     */
    public String getJid() {
        return this.jid;
    }

    public void setJid(final String jid) {
        this.jid = jid;
    }

    /**
     * @hibernate.property column="NICK_NAME" length="100" not-null="true"
     *                     unique="true" index="ind_user_nickname"
     */
    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(final String nickName) {
        this.nickName = nickName;
    }

    /**
     * @hibernate.property column="ECHOABLE"
     */
    public boolean isEchoable() {
        return this.echoable;
    }

    public void setEchoable(final boolean echoable) {
        this.echoable = echoable;
    }

    /**
     * @hibernate.property column="LOCALE" length="3" not-null="true"
     */
    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * @hibernate.property column="ACCOUNT_TYPE" not-null="true"
     */
    public int getAccountTypeCode() {
        return this.accountType.getCode();
    }

    public void setAccountTypeCode(final int code) {
        this.accountType = AccountType.fromCode(code);
        if (this.accountType == null) {
            this.accountType = AccountType.user;
        }
    }

    public boolean isAdmin() {
        return this.accountType.equals(AccountType.admin);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEntry)) {
            return false;
        }

        final UserEntry target = (UserEntry) o;

        return !(this.userId != null ? !this.userId.equals(target.userId)
                : target.userId != null);

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (this.userId != null ? this.userId.hashCode() : 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                this.jid).toString();
    }
}
