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

		wordEntryManager = (WordEntryManager) GTRobotContextHelper
				.getBean("wordEntryManager");
		wordUnitManager = (WordUnitManager) GTRobotContextHelper
				.getBean("wordUnitManager");
		wordUnitEntryManager = (WordUnitEntryManager) GTRobotContextHelper
				.getBean("wordUnitEntryManager");

		SessionFactory sessionFactory = (SessionFactory) GTRobotContextHelper
				.getBean("sessionFactory");
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.MANUAL);
		// session.setFlushMode(FlushMode.COMMIT);
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(session));
	}

	public abstract void importFile(String fileName);

	protected void flush() {
		SessionFactory sessionFactory = (SessionFactory) GTRobotContextHelper
				.getBean("sessionFactory");
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager
				.getResource(sessionFactory);
		holder.getSession().flush();
	}

	protected void saveWordUnit(WordUnit wordUnit) {
		wordUnitManager.saveWordUnit(wordUnit);
		wordUnit.setWordCount(wordUnit.getWordEntries().size());
		wordUnitManager.saveWordUnit(wordUnit);
	}

	protected void saveWordEntry(WordUnit wordUnit, WordEntry wordEntry) {
		wordEntryManager.saveWordEntry(wordEntry);
		WordUnitEntryKey key = new WordUnitEntryKey();
		key.setWordEntryId(wordEntry.getWordEntryId());
		key.setWordUnitId(wordUnit.getWordUnitId());
		WordUnitEntry wordUnitEntry = null;
		wordUnitEntry = wordUnitEntryManager.getWordUnitEntry(key);
		if (wordUnitEntry == null) {
			wordUnitEntry = new WordUnitEntry();
			wordUnitEntry.setPk(key);
			wordUnitEntryManager.saveWordUnitEntry(wordUnitEntry);
		}
	}

	protected WordUnit createWordUnit(String unitName) {
		WordUnit wordUnit = wordUnitManager.getWordUnit(unitName);
		if (wordUnit == null) {
			wordUnit = new WordUnit();
			wordUnit.setName(unitName);
			wordUnit.setLevel(wordLelvel);
		}
		return wordUnit;
	}

	protected WordEntry createWordEntry(String word, String pronounce,
			String pronounceType, String wordType, String meaning,
			String sentence, String comments) {
		WordEntry wordEntry = wordEntryManager.getWordEntry(word);
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
			wordEntry.setPronounce(mergeContents(pronounce, wordEntry
					.getPronounce(), "; "));
			wordEntry.setPronounceType(mergeContents(pronounceType, wordEntry
					.getPronounceType(), "; "));
			wordEntry.setWordType(mergeContents(wordType, wordEntry
					.getWordType(), "; "));
			wordEntry.setMeaning(mergeContents(meaning, wordEntry.getMeaning(),
					"; "));

			wordEntry.setSentence(mergeContents(sentence, wordEntry
					.getSentence(), "\r\n"));
			wordEntry.setComments(mergeContents(comments, wordEntry
					.getComments(), "\r\n"));
		}
		return wordEntry;
	}

	private String mergeContents(String newContent, String oldContent,
			String spliter) {
		if (oldContent == null) {
			return newContent;
		} else {
			if (newContent != null && newContent.length() != 0
					&& oldContent.indexOf(newContent) < 0) {
				return oldContent + spliter + newContent;
			} else
				return oldContent;
		}
	}
}
