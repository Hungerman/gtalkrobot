package com.gtrobot.dao.word.impl;

import java.util.List;

import com.gtrobot.dao.impl.BaseDaoHibernate;
import com.gtrobot.dao.word.UserFailedWordInfoDao;
import com.gtrobot.model.word.UserFailedWordInfo;
import com.gtrobot.model.word.UserFailedWordInfoKey;

public class UserFailedWordInfoDaoHibernate extends BaseDaoHibernate implements
		UserFailedWordInfoDao {

	/**
	 * @see com.gtrobot.dao.word.UserFailedWordInfoDao#getUserFailedWordInfos(com.gtrobot.model.word.UserFailedWordInfo)
	 */
	public List getUserFailedWordInfos() {
		return getHibernateTemplate().find("from UserFailedWordInfo");
	}

	public List getUserFailedWordInfos(Long userId) {
		return getHibernateTemplate().find(
				"from UserFailedWordInfo where pk.userId=?", userId);
	}

	public List getUserFailedWordInfos(Long userId, Long wordUnitId) {
		return getHibernateTemplate()
				.find(
						"from UserFailedWordInfo where pk.userId=? and pk.wordUnit.wordUnitId=?",
						new Object[] { userId, wordUnitId });
	}

	public List getUserFailedWordInfosByWord(final Long userId,
			final Long wordEntryId) {
		return getHibernateTemplate()
				.find(
						"from UserFailedWordInfo where pk.userId=? and pk.wordEntry.wordEntryId=?",
						new Object[] { userId, wordEntryId });
	}

	public long getFailedWordCount(final Long userId, final Long wordUnitId) {
		String strHQL = "Select count(*) From UserFailedWordInfo where pk.userId="
				+ userId + " and pk.wordUnit.wordUnitId=" + wordUnitId;
		Long count = (Long) getSession().createQuery(strHQL).uniqueResult();
		if (count == null) {
			return 0;
		}
		return count.longValue();
	}

	/**
	 * @see com.gtrobot.dao.word.UserFailedWordInfoDao#getUserFailedWordInfo(Long
	 *      id)
	 */
	public UserFailedWordInfo getUserFailedWordInfo(
			final UserFailedWordInfoKey userFailedWordInfoKey) {
		UserFailedWordInfo userFailedWordInfo = (UserFailedWordInfo) getHibernateTemplate()
				.get(UserFailedWordInfo.class, userFailedWordInfoKey);
		if (userFailedWordInfo == null) {
			log.warn("uh oh, UserFailedWordInfo with id '"
					+ userFailedWordInfoKey + "' not found...");
			return null;
		}

		return userFailedWordInfo;
	}

	public UserFailedWordInfo getUserFailedWordInfos(Long userId,
			Long wordUnitId, Long wordEntryId) {
		List ls = getHibernateTemplate()
				.find(
						"from UserFailedWordInfo where pk.userId=? and pk.wordUnit.wordUnitId=? and pk.wordEntry.wordEntryId=?",
						new Object[] { userId, wordUnitId, wordEntryId });
		if (ls.size() == 0) {
			return null;
		} else {
			return (UserFailedWordInfo) ls.get(0);
		}
	}

	/**
	 * @see com.gtrobot.dao.word.UserFailedWordInfoDao#saveUserFailedWordInfo(UserFailedWordInfo
	 *      UserFailedWordInfo)
	 */
	public void saveUserFailedWordInfo(
			final UserFailedWordInfo userFailedWordInfo) {
		getHibernateTemplate().saveOrUpdate(userFailedWordInfo);
	}

	/**
	 * @see com.gtrobot.dao.word.UserFailedWordInfoDao#removeUserFailedWordInfo(Long
	 *      id)
	 */
	public void removeUserFailedWordInfo(
			final UserFailedWordInfo userFailedWordInfo) {
		getHibernateTemplate().delete(userFailedWordInfo);
	}
}
