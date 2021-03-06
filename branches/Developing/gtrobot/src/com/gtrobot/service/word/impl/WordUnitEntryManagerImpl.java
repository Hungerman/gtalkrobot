package com.gtrobot.service.word.impl;

import java.util.List;

import com.gtrobot.dao.word.WordUnitEntryDao;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.model.word.WordUnitEntryKey;
import com.gtrobot.service.impl.BaseManager;
import com.gtrobot.service.word.WordUnitEntryManager;

public class WordUnitEntryManagerImpl extends BaseManager implements
        WordUnitEntryManager {
    private WordUnitEntryDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * 
     * @param dao
     */
    public void setWordUnitEntryDao(final WordUnitEntryDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.gtrobot.model.service.WordUnitEntryManager#get
     *      WordUnitEntrys(com.gtrobot.model.word.WordUnitEntry)
     */
    public List getWordUnitEntrys() {
        return this.dao.getWordUnitEntrys();
    }

    /**
     * @see com.gtrobot.model.service.WordUnitEntryManager#get
     *      WordUnitEntry(String key)
     */
    public WordUnitEntry getWordUnitEntry(final WordUnitEntryKey key) {
        return this.dao.getWordUnitEntry(key);
    }

    public WordUnitEntry getWordUnitEntry(final Long wordEntryId,
            final Long wordUnitId) {
        return this.dao.getWordUnitEntry(wordEntryId, wordUnitId);
    }

    /**
     * @see com.gtrobot.model.service.WordUnitEntryManager#save
     *      WordUnitEntry(WordUnitEntry wordUnitEntry)
     */
    public void saveWordUnitEntry(final WordUnitEntry wordUnitEntry) {
        this.dao.saveWordUnitEntry(wordUnitEntry);
    }

    /**
     * @see com.gtrobot.model.service.WordUnitEntryManager#remove
     *      WordUnitEntry(String key)
     */
    public void removeWordUnitEntry(final WordUnitEntryKey key) {
        this.dao.removeWordUnitEntry(key);
    }
}
