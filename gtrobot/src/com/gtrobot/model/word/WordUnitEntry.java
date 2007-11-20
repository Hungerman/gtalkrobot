package com.gtrobot.model.word;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * @hibernate.class table="WORD_UNIT_ENTRY"
 */
public class WordUnitEntry extends BaseObject {
    private static final long serialVersionUID = -8921629023230583323L;

    private WordUnitEntryKey pk = new WordUnitEntryKey();

    /**
     * @hibernate.composite-id
     */
    public WordUnitEntryKey getPk() {
        return this.pk;
    }

    public void setPk(final WordUnitEntryKey key) {
        this.pk = key;
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
        if (!(o instanceof WordUnitEntry)) {
            return false;
        }

        final WordUnitEntry target = (WordUnitEntry) o;

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
