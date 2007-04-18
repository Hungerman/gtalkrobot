package com.gtrobot.dao.word;

import java.util.List;

import com.gtrobot.dao.Dao;
import com.gtrobot.model.word.UserUnitInfo;
import com.gtrobot.model.word.UserUnitInfoKey;

public interface UserUnitInfoDao extends Dao {

	/**
	 * Retrieves all of the user UnitInfos
	 */
	public List getUserUnitInfos(final Long userEntryId);

	public List getNotFinishedUserUnitInfos(final Long userEntryId);

	/**
	 * Gets user UnitInfo's information based on primary key. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the user UnitInfo's id
	 * @return user UnitInfo populated user UnitInfo object
	 */
	public UserUnitInfo getUserUnitInfo(final UserUnitInfoKey key);

	public UserUnitInfo getUserUnitInfo(final Long userId, final Long wordUnitId);

	/**
	 * Saves a user UnitInfo's information
	 * 
	 * @param userUnitInfo
	 *            the object to be saved
	 */
	public void saveUserUnitInfo(UserUnitInfo userUnitInfo);

	/**
	 * Removes a user UnitInfo from the database by id
	 * 
	 * @param id
	 *            the user UnitInfo's id
	 */
	public void removeUserUnitInfo(final UserUnitInfo userUnitInfo);
}
