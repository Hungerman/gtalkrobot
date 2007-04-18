package com.gtrobot.service.word.impl;

import java.util.List;

import com.gtrobot.dao.word.UserFailedWordInfoDao;
import com.gtrobot.model.word.UserFailedWordInfo;
import com.gtrobot.model.word.UserFailedWordInfoKey;
import com.gtrobot.service.impl.BaseManager;
import com.gtrobot.service.word.UserFailedWordInfoManager;

public class UserFailedWordInfoManagerImpl extends BaseManager implements
		UserFailedWordInfoManager {
	private UserFailedWordInfoDao dao;

	/**
	 * Set the Dao for communication with the data layer.
	 * 
	 * @param dao
	 */
	public void setUserFailedWordInfoDao(UserFailedWordInfoDao dao) {
		this.dao = dao;
	}

	/**
	 * @see com.gtrobot.model.service.UserFailedWordInfoManager#getUserFailedWordInfos(com.gtrobot.model.word.UserFailedWordInfo)
	 */
	public List getUserFailedWordInfos() {
		return dao.getUserFailedWordInfos();
	}

	public List getUserFailedWordInfos(final Long userId) {
		return dao.getUserFailedWordInfos(userId);
	}

	public List getUserFailedWordInfos(final Long userId, final Long wordUnitId) {
		return dao.getUserFailedWordInfos(userId, wordUnitId);
	}

	public long getFailedWordCount(Long userId, Long wordUnitId) {
		return dao.getFailedWordCount(userId, wordUnitId);
	}

	public List getUserFailedWordInfosByWord(final Long userId,
			final Long wordEntryId) {
		return dao.getUserFailedWordInfosByWord(userId, wordEntryId);
	}

	/**
	 * @see com.gtrobot.model.service.UserFailedWordInfoManager#getUserFailedWordInfo(String
	 *      id)
	 */
	public UserFailedWordInfo getUserFailedWordInfo(
			final UserFailedWordInfoKey userFailedWordInfoKey) {
		return dao.getUserFailedWordInfo(userFailedWordInfoKey);
	}

	public UserFailedWordInfo getUserFailedWordInfos(final Long userId,
			final Long wordUnitId, final Long wordEntryId) {
		return dao.getUserFailedWordInfos(userId, wordUnitId, wordEntryId);
	}

	/**
	 * @see com.gtrobot.model.service.UserFailedWordInfoManager#saveUserFailedWordInfo(UserFailedWordInfo
	 *      UserFailedWordInfo)
	 */
	public void saveUserFailedWordInfo(UserFailedWordInfo userFailedWordInfo) {
		dao.saveUserFailedWordInfo(userFailedWordInfo);
	}

	/**
	 * @see com.gtrobot.model.service.UserFailedWordInfoManager#removeUserFailedWordInfo(String
	 *      id)
	 */
	public void removeUserFailedWordInfo(
			final UserFailedWordInfo userFailedWordInfo) {
		dao.removeUserFailedWordInfo(userFailedWordInfo);
	}
}
