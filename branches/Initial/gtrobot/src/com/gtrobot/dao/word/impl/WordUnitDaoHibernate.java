package com.gtrobot.dao.word.impl;

import java.util.List;

import com.gtrobot.dao.impl.BaseDaoHibernate;
import com.gtrobot.dao.word.WordUnitDao;
import com.gtrobot.model.word.WordUnit;

public class WordUnitDaoHibernate extends BaseDaoHibernate implements
		WordUnitDao {

	/**
	 * @see com.gtrobot.dao.word.WordUnitDao#get
	 *      WordUnits(com.gtrobot.model.word.WordUnit)
	 */
	public List getWordUnits() {
		return getHibernateTemplate().find("from WordUnit");

		/*
		 * Remove the line above and uncomment this code block if you want to
		 * use Hibernate's Query by Example API. if (wordUnit == null) { return
		 * getHibernateTemplate().find("from WordUnit"); } else { // filter on
		 * properties set in the wordUnit HibernateCallback callback = new
		 * HibernateCallback() { public Object doInHibernate(Session session)
		 * throws HibernateException { Example ex =
		 * Example.create(wordUnit).ignoreCase().enableLike(MatchMode.ANYWHERE);
		 * return session.createCriteria(WordUnit.class).add(ex).list(); } };
		 * return (List) getHibernateTemplate().execute(callback); }
		 */
	}

	public List getWordUnitsNotInUserList(final Long userEntryId) {
		String hql = " from WordUnit as wordUnit";
		hql = hql
				+ " where wordUnit.owner = null and wordUnit.wordUnitId not in( ";
		hql = hql
				+ "  select userUnitInfo.pk.wordUnit.wordUnitId from UserUnitInfo as userUnitInfo where userUnitInfo.pk.userId=? )";
		return getHibernateTemplate().find(hql, userEntryId);
	}

	/**
	 * @see com.gtrobot.dao.word.WordUnitDao#get WordUnit(Long wordUnitId)
	 */
	public WordUnit getWordUnit(final Long wordUnitId) {
		WordUnit wordUnit = (WordUnit) getHibernateTemplate().get(
				WordUnit.class, wordUnitId);
		if (wordUnit == null) {
			log.warn("uh oh, wordUnit with wordUnitId '" + wordUnitId
					+ "' not found...");
			// throw new ObjectRetrievalFailureException(WordUnit.class,
			// wordUnitId);
			return null;
		}

		return wordUnit;
	}

	public WordUnit getWordUnit(final String name) {
		List ls = getHibernateTemplate().find("from WordUnit where name=?",
				name);
		if (ls != null && ls.size() > 0) {
			return (WordUnit) ls.get(0);
		}
		return null;
	}

	/**
	 * @see com.gtrobot.dao.word.WordUnitDao#save WordUnit(WordUnit wordUnit)
	 */
	public void saveWordUnit(final WordUnit wordUnit) {
		getHibernateTemplate().saveOrUpdate(wordUnit);
	}

	/**
	 * @see com.gtrobot.dao.word.WordUnitDao#remove WordUnit(Long wordUnitId)
	 */
	public void removeWordUnit(final Long wordUnitId) {
		getHibernateTemplate().delete(getWordUnit(wordUnitId));
	}
}
