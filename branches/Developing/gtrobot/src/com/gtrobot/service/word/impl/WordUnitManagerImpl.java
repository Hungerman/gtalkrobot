package com.gtrobot.service.word.impl;

import java.util.List;

import com.gtrobot.dao.word.WordUnitDao;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.service.impl.BaseManager;
import com.gtrobot.service.word.WordUnitManager;

public class WordUnitManagerImpl extends BaseManager implements WordUnitManager {
    private WordUnitDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * 
     * @param dao
     */
    public void setWordUnitDao(final WordUnitDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.gtrobot.model.service.WordUnitManager#get
     *      WordUnits(com.gtrobot.model.word.WordUnit)
     */
    public List getWordUnits() {
        return this.dao.getWordUnits();
    }

    public List getWordUnitsNotInUserList(final Long userEntryId) {
        return this.dao.getWordUnitsNotInUserList(userEntryId);
    }

    /**
     * @see com.gtrobot.model.service.WordUnitManager#get WordUnit(String
     *      wordUnitId)
     */
    public WordUnit getWordUnit(final Long wordUnitId) {
        return this.dao.getWordUnit(wordUnitId);
    }

    public WordUnit getWordUnit(final String name) {
        return this.dao.getWordUnit(name);
    }

    /**
     * @see com.gtrobot.model.service.WordUnitManager#save WordUnit(WordUnit
     *      wordUnit)
     */
    public void saveWordUnit(final WordUnit wordUnit) {
        this.dao.saveWordUnit(wordUnit);
    }

    /**
     * @see com.gtrobot.model.service.WordUnitManager#remove WordUnit(String
     *      wordUnitId)
     */
    public void removeWordUnit(final Long wordUnitId) {
        this.dao.removeWordUnit(wordUnitId);
    }
}
