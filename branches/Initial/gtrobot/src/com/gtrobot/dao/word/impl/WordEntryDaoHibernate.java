package com.gtrobot.dao.word.impl;

import java.util.List;

import org.hibernate.Query;

import com.gtrobot.dao.impl.BaseDaoHibernate;
import com.gtrobot.dao.word.WordEntryDao;
import com.gtrobot.model.word.WordEntry;

public class WordEntryDaoHibernate extends BaseDaoHibernate implements
        WordEntryDao {

    /**
     * @see com.gtrobot.dao.word.WordEntryDao#get
     *      WordEntrys(com.gtrobot.model.word.WordEntry)
     */
    public List getWordEntries() {
        return this.getHibernateTemplate().find("from WordEntry");

        /*
         * Remove the line above and uncomment this code block if you want to
         * use Hibernate's Query by Example API. if (wordEntry == null) { return
         * getHibernateTemplate().find("from WordEntry"); } else { // filter on
         * properties set in the wordEntry HibernateCallback callback = new
         * HibernateCallback() { public Object doInHibernate(Session session)
         * throws HibernateException { Example ex =
         * Example.create(wordEntry).ignoreCase().enableLike(MatchMode.ANYWHERE);
         * return session.createCriteria(WordEntry.class).add(ex).list(); } };
         * return (List) getHibernateTemplate().execute(callback); }
         */
    }

    public List searchWordEntries(final String keyword, final int maxResults) {
        final String hql = "from WordEntry where word like '" + keyword
                + "' or pronounce like '" + keyword + "'";
        final Query q = this.getSession().createQuery(hql);
        q.setFirstResult(0);
        q.setMaxResults(maxResults);
        return q.list();
    }

    /**
     * @see com.gtrobot.dao.word.WordEntryDao#get WordEntry(Long wordEntryId)
     */
    public WordEntry getWordEntry(final Long wordEntryId) {
        final WordEntry wordEntry = (WordEntry) this.getHibernateTemplate()
                .get(WordEntry.class, wordEntryId);
        if (wordEntry == null) {
            this.log.debug("uh oh, wordEntry with wordEntryId '" + wordEntryId
                    + "' not found...");
            return null;
        }

        return wordEntry;
    }

    public WordEntry getWordEntry(final String word) {
        final List ls = this.getHibernateTemplate().find(
                "from WordEntry where word=?", word);
        if ((ls != null) && (ls.size() > 0)) {
            return (WordEntry) ls.get(0);
        }
        return null;
    }

    /**
     * @see com.gtrobot.dao.word.WordEntryDao#save WordEntry(WordEntry
     *      wordEntry)
     */
    public void saveWordEntry(final WordEntry wordEntry) {
        this.getHibernateTemplate().saveOrUpdate(wordEntry);
    }

    /**
     * @see com.gtrobot.dao.word.WordEntryDao#remove WordEntry(Long wordEntryId)
     */
    public void removeWordEntry(final Long wordEntryId) {
        this.getHibernateTemplate().delete(this.getWordEntry(wordEntryId));
    }
}
