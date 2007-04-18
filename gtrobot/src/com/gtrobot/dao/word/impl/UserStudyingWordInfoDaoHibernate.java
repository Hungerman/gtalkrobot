package com.gtrobot.dao.word.impl;

import java.util.List;

import com.gtrobot.dao.impl.BaseDaoHibernate;
import com.gtrobot.dao.word.UserStudyingWordInfoDao;
import com.gtrobot.model.word.UserWordStudyingInfo;

public class UserStudyingWordInfoDaoHibernate extends BaseDaoHibernate
		implements UserStudyingWordInfoDao {

	/**
	 * @see com.gtrobot.dao.word.UserStudyingWordInfoDao#getUserWordStudyingInfos(com.gtrobot.model.word.UserWordStudyingInfo)
	 */
	public List getUserWordStudyingInfos() {
		return getHibernateTemplate().find("from UserWordStudyingInfo");

		/*
		 * Remove the line above and uncomment this code block if you want to
		 * use Hibernate's Query by Example API. if (UserWordStudyingInfo ==
		 * null) { return getHibernateTemplate().find("from
		 * UserWordStudyingInfo"); } else { // filter on properties set in the
		 * UserWordStudyingInfo HibernateCallback callback = new
		 * HibernateCallback() { public Object doInHibernate(Session session)
		 * throws HibernateException { Example ex =
		 * Example.create(UserWordStudyingInfo).ignoreCase().enableLike(MatchMode.ANYWHERE);
		 * return
		 * session.createCriteria(UserWordStudyingInfo.class).add(ex).list(); } };
		 * return (List) getHibernateTemplate().execute(callback); }
		 */
	}

	/**
	 * @see com.gtrobot.dao.word.UserStudyingWordInfoDao#getUserWordStudyingInfo(Long
	 *      userId)
	 */
	public UserWordStudyingInfo getUserWordStudyingInfo(final Long userId) {
		UserWordStudyingInfo userWordStudyingInfo = (UserWordStudyingInfo) getHibernateTemplate()
				.get(UserWordStudyingInfo.class, userId);
		if (userWordStudyingInfo == null) {
			log.warn("uh oh, UserWordStudyingInfo with userId '" + userId
					+ "' not found...");
			// throw new
			// ObjectRetrievalFailureException(UserWordStudyingInfo.class,
			// userId);
			return null;
		}

		return userWordStudyingInfo;
	}

	/**
	 * @see com.gtrobot.dao.word.UserStudyingWordInfoDao#saveUserWordStudyingInfo(UserWordStudyingInfo
	 *      UserWordStudyingInfo)
	 */
	public void saveUserWordStudyingInfo(
			final UserWordStudyingInfo userWordStudyingInfo) {
		getHibernateTemplate().saveOrUpdate(userWordStudyingInfo);
	}

	/**
	 * @see com.gtrobot.dao.word.UserStudyingWordInfoDao#removeUserWordStudyingInfo(Long
	 *      userId)
	 */
	public void removeUserWordStudyingInfo(final Long userId) {
		getHibernateTemplate().delete(getUserWordStudyingInfo(userId));
	}
}
