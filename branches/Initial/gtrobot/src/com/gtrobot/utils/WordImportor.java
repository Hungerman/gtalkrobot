package com.gtrobot.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.model.word.WordUnitEntryKey;
import com.gtrobot.service.word.WordEntryManager;
import com.gtrobot.service.word.WordUnitEntryManager;
import com.gtrobot.service.word.WordUnitManager;

/**
 * 实现数据的导入处理。
 * 
 * @author Joey
 * 
 */
public abstract class WordImportor {
    protected static final Log log = LogFactory.getLog(WordImportor.class);

    private WordEntryManager wordEntryManager = null;

    private WordUnitManager wordUnitManager = null;

    private WordUnitEntryManager wordUnitEntryManager = null;

    protected int wordLelvel = 0;

    public WordImportor() {
        GTRobotContextHelper.initApplicationContext();

        this.wordEntryManager = (WordEntryManager) GTRobotContextHelper
                .getBean("wordEntryManager");
        this.wordUnitManager = (WordUnitManager) GTRobotContextHelper
                .getBean("wordUnitManager");
        this.wordUnitEntryManager = (WordUnitEntryManager) GTRobotContextHelper
                .getBean("wordUnitEntryManager");

        final SessionFactory sessionFactory = (SessionFactory) GTRobotContextHelper
                .getBean("sessionFactory");
        final Session session = SessionFactoryUtils.getSession(sessionFactory,
                true);
        session.setFlushMode(FlushMode.MANUAL);
        // session.setFlushMode(FlushMode.COMMIT);
        TransactionSynchronizationManager.bindResource(sessionFactory,
                new SessionHolder(session));
    }

    public abstract void importFile(String fileName);

    protected void flush() {
        final SessionFactory sessionFactory = (SessionFactory) GTRobotContextHelper
                .getBean("sessionFactory");
        final SessionHolder holder = (SessionHolder) TransactionSynchronizationManager
                .getResource(sessionFactory);
        holder.getSession().flush();
    }

    protected void saveWordUnit(final WordUnit wordUnit) {
        this.wordUnitManager.saveWordUnit(wordUnit);
        wordUnit.setWordCount(wordUnit.getWordEntries().size());
        this.wordUnitManager.saveWordUnit(wordUnit);
    }

    protected void saveWordEntry(final WordUnit wordUnit,
            final WordEntry wordEntry) {
        this.wordEntryManager.saveWordEntry(wordEntry);
        final WordUnitEntryKey key = new WordUnitEntryKey();
        key.setWordEntryId(wordEntry.getWordEntryId());
        key.setWordUnitId(wordUnit.getWordUnitId());
        WordUnitEntry wordUnitEntry = null;
        wordUnitEntry = this.wordUnitEntryManager.getWordUnitEntry(key);
        if (wordUnitEntry == null) {
            wordUnitEntry = new WordUnitEntry();
            wordUnitEntry.setPk(key);
            this.wordUnitEntryManager.saveWordUnitEntry(wordUnitEntry);
        }
    }

    protected WordUnit createWordUnit(final String unitName) {
        WordUnit wordUnit = this.wordUnitManager.getWordUnit(unitName);
        if (wordUnit == null) {
            wordUnit = new WordUnit();
            wordUnit.setName(unitName);
            wordUnit.setLevel(this.wordLelvel);
        }
        return wordUnit;
    }

    protected WordEntry createWordEntry(final String word,
            final String pronounce, final String pronounceType,
            final String wordType, final String meaning, final String sentence,
            final String comments) {
        WordEntry wordEntry = this.wordEntryManager.getWordEntry(word);
        if (wordEntry == null) {
            wordEntry = new WordEntry();
            wordEntry.setWord(word);
            wordEntry.setPronounce(pronounce);
            wordEntry.setPronounceType(pronounceType);
            wordEntry.setWordType(wordType);
            wordEntry.setMeaning(meaning);
            wordEntry.setSentence(sentence);
            wordEntry.setComments(comments);
        } else {
            wordEntry.setPronounce(this.mergeContents(pronounce, wordEntry
                    .getPronounce(), "; "));
            wordEntry.setPronounceType(this.mergeContents(pronounceType,
                    wordEntry.getPronounceType(), "; "));
            wordEntry.setWordType(this.mergeContents(wordType, wordEntry
                    .getWordType(), "; "));
            wordEntry.setMeaning(this.mergeContents(meaning, wordEntry
                    .getMeaning(), "; "));

            wordEntry.setSentence(this.mergeContents(sentence, wordEntry
                    .getSentence(), "\r\n"));
            wordEntry.setComments(this.mergeContents(comments, wordEntry
                    .getComments(), "\r\n"));
        }
        return wordEntry;
    }

    private String mergeContents(final String newContent,
            final String oldContent, final String spliter) {
        if (oldContent == null) {
            return newContent;
        } else {
            if ((newContent != null) && (newContent.length() != 0)
                    && (oldContent.indexOf(newContent) < 0)) {
                return oldContent + spliter + newContent;
            } else {
                return oldContent;
            }
        }
    }
}
