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
	public void setWordUnitDao(WordUnitDao dao) {
		this.dao = dao;
	}

	/**
	 * @see com.gtrobot.model.service.WordUnitManager#get
	 *      WordUnits(com.gtrobot.model.word.WordUnit)
	 */
	public List getWordUnits(final WordUnit wordUnit) {
		return dao.getWordUnits(wordUnit);
	}

	public List getWordUnitsNotInUserList(Long userEntryId) {
		return dao.getWordUnitsNotInUserList(userEntryId);
	}

	/**
	 * @see com.gtrobot.model.service.WordUnitManager#get WordUnit(String
	 *      wordUnitId)
	 */
	public WordUnit getWordUnit(final Long wordUnitId) {
		return dao.getWordUnit(wordUnitId);
	}

	public WordUnit getWordUnit(final String name) {
		return dao.getWordUnit(name);
	}

	/**
	 * @see com.gtrobot.model.service.WordUnitManager#save WordUnit(WordUnit
	 *      wordUnit)
	 */
	public void saveWordUnit(WordUnit wordUnit) {
		dao.saveWordUnit(wordUnit);
	}

	/**
	 * @see com.gtrobot.model.service.WordUnitManager#remove WordUnit(String
	 *      wordUnitId)
	 */
	public void removeWordUnit(final Long wordUnitId) {
		dao.removeWordUnit(wordUnitId);
	}
}
