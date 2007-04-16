package com.gtrobot.dao.word.impl;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.gtrobot.dao.impl.BaseDaoHibernate;
import com.gtrobot.dao.word.UserUnitInfoDao;
import com.gtrobot.model.word.UserUnitInfo;
import com.gtrobot.model.word.UserUnitInfoKey;

/**
 * @author Joey
 * 
 */
public class UserUnitInfoDaoHibernate extends BaseDaoHibernate implements
		UserUnitInfoDao {

	// /**
	// * @see
	// com.gtrobot.dao.word.UserUnitInfoDao#getUserUnitInfos(com.gtrobot.model.word.UserUnitInfo)
	// */
	// public List getUserUnitInfos(final UserUnitInfo userUnitInfo) {
	// // return getHibernateTemplate().find("from UserUnitInfo");
	//
	// /* Remove the line above and uncomment this code block if you want
	// to use Hibernate's Query by Example API.
	// */
	// if (userUnitInfo == null) {
	// return getHibernateTemplate().find("from UserUnitInfo");
	// } else {
	// // filter on properties set in the UserUnitInfo
	// HibernateCallback callback = new HibernateCallback() {
	// public Object doInHibernate(Session session) throws HibernateException {
	// Example ex =
	// Example.create(userUnitInfo).excludeZeroes().ignoreCase().enableLike(MatchMode.ANYWHERE);
	// return session.createCriteria(UserUnitInfo.class).add(ex).list();
	// }
	// };
	// return (List) getHibernateTemplate().execute(callback);
	// }
	// }

	public List getUserUnitInfos(final Long userEntryId) {
		return getHibernateTemplate().find(
				"from UserUnitInfo where pk.userId=?", userEntryId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gtrobot.dao.word.UserUnitInfoDao#getUserUnitInfo(com.gtrobot.model.word.UserUnitInfoKey)
	 */
	public UserUnitInfo getUserUnitInfo(final UserUnitInfoKey key) {
		UserUnitInfo userUnitInfo = (UserUnitInfo) getHibernateTemplate().get(
				UserUnitInfo.class, key);
		if (userUnitInfo == null) {
			log.warn("uh oh, UserUnitInfo with id '" + key + "' not found...");
			throw new ObjectRetrievalFailureException(UserUnitInfo.class, key);
		}

		return userUnitInfo;
	}

	/**
	 * @see com.gtrobot.dao.word.UserUnitInfoDao#saveUserUnitInfo(UserUnitInfo
	 *      UserUnitInfo)
	 */
	public void saveUserUnitInfo(final UserUnitInfo userUnitInfo) {
		getHibernateTemplate().saveOrUpdate(userUnitInfo);
	}

	/**
	 * @see com.gtrobot.dao.word.UserUnitInfoDao#removeUserUnitInfo(Long id)
	 */
	public void removeUserUnitInfo(final UserUnitInfo userUnitInfo) {
		getHibernateTemplate().delete(userUnitInfo);
	}
}
