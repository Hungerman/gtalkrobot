package com.gtrobot.service.word;

import java.util.List;

import com.gtrobot.model.word.WordUnit;
import com.gtrobot.service.Manager;

public interface WordUnitManager extends Manager {
    /**
     * Retrieves all of the wordUnits
     */
    public List getWordUnits();

    public List getWordUnitsNotInUserList(final Long userEntryId);

    /**
     * Gets wordUnit's information based on wordUnitId.
     * 
     * @param wordUnitId
     *            the wordUnit's wordUnitId
     * @return wordUnit populated wordUnit object
     */
    public WordUnit getWordUnit(final Long wordUnitId);

    public WordUnit getWordUnit(final String name);

    /**
     * Saves a wordUnit's information
     * 
     * @param wordUnit
     *            the object to be saved
     */
    public void saveWordUnit(WordUnit wordUnit);

    /**
     * Removes a wordUnit from the database by wordUnitId
     * 
     * @param wordUnitId
     *            the wordUnit's wordUnitId
     */
    public void removeWordUnit(final Long wordUnitId);
}
