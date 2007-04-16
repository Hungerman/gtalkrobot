package com.gtrobot.dao.word.impl;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.gtrobot.dao.impl.BaseDaoHibernate;
import com.gtrobot.dao.word.WordUnitEntryDao;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.model.word.WordUnitEntryKey;

public class WordUnitEntryDaoHibernate extends BaseDaoHibernate implements
		WordUnitEntryDao {

	/**
	 * @see com.gtrobot.model.dao.WordUnitEntryDao#getWordUnitEntrys(com.gtrobot.model.word.WordUnitEntry)
	 */
	public List getWordUnitEntrys(final WordUnitEntry wordUnitEntry) {
		return getHibernateTemplate().find("from WordUnitEntry");

		/*
		 * Remove the line above and uncomment this code block if you want to
		 * use Hibernate's Query by Example API. if (wordUnitEntry == null) {
		 * return getHibernateTemplate().find("from WordUnitEntry"); } else { //
		 * filter on properties set in the wordUnitEntry HibernateCallback
		 * callback = new HibernateCallback() { public Object
		 * doInHibernate(Session session) throws HibernateException { Example ex =
		 * Example.create(wordUnitEntry).ignoreCase().enableLike(MatchMode.ANYWHERE);
		 * return session.createCriteria(WordUnitEntry.class).add(ex).list(); } };
		 * return (List) getHibernateTemplate().execute(callback); }
		 */
	}

	/**
	 * @see com.gtrobot.model.dao.WordUnitEntryDao#getWordUnitEntry(WordUnitEntryKey
	 *      key)
	 */
	public WordUnitEntry getWordUnitEntry(final WordUnitEntryKey key) {
		WordUnitEntry wordUnitEntry = (WordUnitEntry) getHibernateTemplate()
				.get(WordUnitEntry.class, key);
		if (wordUnitEntry == null) {
			log
					.warn("uh oh, wordUnitEntry with key '" + key
							+ "' not found...");
			throw new ObjectRetrievalFailureException(WordUnitEntry.class, key);
		}

		return wordUnitEntry;
	}

	/**
	 * @see com.gtrobot.model.dao.WordUnitEntryDao#saveWordUnitEntry(WordUnitEntry
	 *      wordUnitEntry)
	 */
	public void saveWordUnitEntry(final WordUnitEntry wordUnitEntry) {
		getHibernateTemplate().saveOrUpdate(wordUnitEntry);
	}

	/**
	 * @see com.gtrobot.model.dao.WordUnitEntryDao#removeWordUnitEntry(WordUnitEntryKey
	 *      key)
	 */
	public void removeWordUnitEntry(final WordUnitEntryKey key) {
		getHibernateTemplate().delete(getWordUnitEntry(key));
	}
}
