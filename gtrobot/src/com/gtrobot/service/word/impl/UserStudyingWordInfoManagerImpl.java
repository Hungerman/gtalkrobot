package com.gtrobot.service.word.impl;

import java.util.List;

import com.gtrobot.dao.word.UserStudyingWordInfoDao;
import com.gtrobot.model.word.UserWordStudyingInfo;
import com.gtrobot.service.impl.BaseManager;
import com.gtrobot.service.word.UserStudyingWordInfoManager;

public class UserStudyingWordInfoManagerImpl extends BaseManager implements
		UserStudyingWordInfoManager {
	private UserStudyingWordInfoDao dao;

	/**
	 * Set the Dao for communication with the data layer.
	 * 
	 * @param dao
	 */
	public void setUserStudyingWordInfoDao(UserStudyingWordInfoDao dao) {
		this.dao = dao;
	}

	/**
	 * @see com.gtrobot.model.service.UserStudyingWordInfoManager#getUserWordInfos(com.gtrobot.model.word.UserWordStudyingInfo)
	 */
	public List getUserWordInfos(final UserWordStudyingInfo userWordStudyingInfo) {
		return dao.getUserWordInfos(userWordStudyingInfo);
	}

	/**
	 * @see com.gtrobot.model.service.UserStudyingWordInfoManager#getUserWordInfo(String
	 *      userId)
	 */
	public UserWordStudyingInfo getUserWordInfo(final Long userId) {
		return dao.getUserWordInfo(userId);
	}

	/**
	 * @see com.gtrobot.model.service.UserStudyingWordInfoManager#saveUserWordInfo(UserWordStudyingInfo
	 *      UserWordStudyingInfo)
	 */
	public void saveUserWordInfo(UserWordStudyingInfo userWordStudyingInfo) {
		dao.saveUserWordInfo(userWordStudyingInfo);
	}

	/**
	 * @see com.gtrobot.model.service.UserStudyingWordInfoManager#removeUserWordInfo(String
	 *      userId)
	 */
	public void removeUserWordInfo(final Long userId) {
		dao.removeUserWordInfo(userId);
	}
}
