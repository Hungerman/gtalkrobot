package com.gtrobot.dao.word;

import java.util.List;

import com.gtrobot.dao.Dao;
import com.gtrobot.model.word.UserFailedWordInfo;
import com.gtrobot.model.word.UserFailedWordInfoKey;

public interface UserFailedWordInfoDao extends Dao {

	/**
	 * Retrieves all of the UserFailedWordInfos
	 */
	public List getUserFailedWordInfos();

	/**
	 * Gets UserFailedWordInfos's information based on primary key. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the UserFailedWordInfos's id
	 * @return UserFailedWordInfos populated UserFailedWordInfos object
	 */
	public UserFailedWordInfo getUserFailedWordInfo(
			final UserFailedWordInfoKey userFailedWordInfoKey);

	/**
	 * Saves a UserFailedWordInfos's information
	 * 
	 * @param userFailedWordInfo
	 *            the object to be saved
	 */
	public void saveUserFailedWordInfo(
			final UserFailedWordInfo userFailedWordInfo);

	/**
	 * Removes a UserFailedWordInfos from the database by id
	 * 
	 * @param id
	 *            the UserFailedWordInfos's id
	 */
	public void removeUserFailedWordInfo(
			final UserFailedWordInfo userFailedWordInfo);
}
