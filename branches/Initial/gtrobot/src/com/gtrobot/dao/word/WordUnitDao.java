package com.gtrobot.dao.word;

import java.util.List;

import com.gtrobot.dao.Dao;
import com.gtrobot.model.word.WordUnit;

public interface WordUnitDao extends Dao {

	/**
	 * Retrieves all of the wordUnits
	 */
	public List getWordUnits(WordUnit wordUnit);

	/**
	 * Gets wordUnit's information based on primary key. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
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

	public List getWordUnitsNotInUserList(Long userEntryId);
}
