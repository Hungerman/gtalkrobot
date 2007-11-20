package com.gtrobot.model.word;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * @hibernate.class table="USER_UNIT_INFO"
 */
public class UserUnitInfo extends BaseObject {
    private static final long serialVersionUID = 3782501438376669985L;

    private UserUnitInfoKey pk = new UserUnitInfoKey();

    private long studiedTimes = 0;

    private boolean finished = false;

    private Date lastStudied = new Date();

    /**
     * @hibernate.composite-id
     */
    public UserUnitInfoKey getPk() {
        return this.pk;
    }

    public void setPk(final UserUnitInfoKey key) {
        this.pk = key;
    }

    /**
     * @hibernate.property column="STUDIED_TIMES"
     */
    public long getStudiedTimes() {
        return this.studiedTimes;
    }

    public void setStudiedTimes(final long finishedUnits) {
        this.studiedTimes = finishedUnits;
    }

    /**
     * @hibernate.property column="FINISHED"
     */
    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(final boolean finished) {
        this.finished = finished;
    }

    /**
     * @hibernate.property column="LAST_STUDIED"
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
        if (!(o instanceof UserUnitInfo)) {
            return false;
        }

        final UserUnitInfo target = (UserUnitInfo) o;

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
