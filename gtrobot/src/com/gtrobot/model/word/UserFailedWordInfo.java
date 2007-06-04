package com.gtrobot.model.word;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * @hibernate.class table="USER_FAILED_WORD_INFO"
 */
public class UserFailedWordInfo extends BaseObject {
    private static final long serialVersionUID = -9143906055746598355L;

    private UserFailedWordInfoKey pk = new UserFailedWordInfoKey();

    private long failedCounts = 0;

    private long studiedTimes = 0;

    private Date lastStudied = new Date();

    /**
     * @hibernate.composite-id
     */
    public UserFailedWordInfoKey getPk() {
        return this.pk;
    }

    public void setPk(final UserFailedWordInfoKey key) {
        this.pk = key;
    }

    /**
     * @hibernate.property column="FAILED_COUNTS" not-null="true"
     */
    public long getFailedCounts() {
        return this.failedCounts;
    }

    public void setFailedCounts(final long failedWordCounts) {
        this.failedCounts = failedWordCounts;
    }

    /**
     * @hibernate.property column="STUDIED_TIMES" not-null="true"
     */
    public long getStudiedTimes() {
        return this.studiedTimes;
    }

    public void setStudiedTimes(final long finishedUnits) {
        this.studiedTimes = finishedUnits;
    }

    /**
     * @hibernate.property column="LAST_STUDIED" not-null="true"
     */
    public Date getLastStudied() {
        return this.lastStudied;
    }

    public void setLastStudied(final Date lastStudied) {
        this.lastStudied = lastStudied;
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
        if (!(o instanceof UserFailedWordInfo)) {
            return false;
        }

        final UserFailedWordInfo target = (UserFailedWordInfo) o;

        return !(this.pk != null ? !this.pk.equals(target.pk)
                : target.pk != null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (this.pk != null ? this.pk.hashCode() : 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                this.pk).toString();
    }

}
