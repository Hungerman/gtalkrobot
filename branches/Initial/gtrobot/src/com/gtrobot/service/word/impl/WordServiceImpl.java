package com.gtrobot.service.word.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.model.word.UserFailedWordInfo;
import com.gtrobot.model.word.UserFailedWordInfoKey;
import com.gtrobot.model.word.UserUnitInfo;
import com.gtrobot.model.word.UserWordStudyingInfo;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.model.word.WordUnitEntryKey;
import com.gtrobot.processor.word.WordProcessingSession;
import com.gtrobot.service.word.UserFailedWordInfoManager;
import com.gtrobot.service.word.UserStudyingWordInfoManager;
import com.gtrobot.service.word.UserUnitInfoManager;
import com.gtrobot.service.word.WordEntryManager;
import com.gtrobot.service.word.WordService;
import com.gtrobot.service.word.WordUnitEntryManager;
import com.gtrobot.service.word.WordUnitManager;

public class WordServiceImpl implements WordService {
	protected static final transient Log log = LogFactory
			.getLog(WordServiceImpl.class);

	private UserUnitInfoManager userUnitInfoManager;

	private WordUnitManager wordUnitManager;

	private UserStudyingWordInfoManager userStudyingWordInfoManager;

	private WordEntryManager wordEntryManager;

	private UserFailedWordInfoManager userFailedWordInfoManager;

	private WordUnitEntryManager wordUnitEntryManager;

	public void setWordUnitManager(WordUnitManager wordUnitManager) {
		this.wordUnitManager = wordUnitManager;
	}

	public void setUserUnitInfoManager(UserUnitInfoManager userUnitInfoManager) {
		this.userUnitInfoManager = userUnitInfoManager;
	}

	public void setUserStudyingWordInfoManager(
			UserStudyingWordInfoManager userStudyingWordInfoManager) {
		this.userStudyingWordInfoManager = userStudyingWordInfoManager;
	}

	public void setWordEntryManager(WordEntryManager wordEntryManager) {
		this.wordEntryManager = wordEntryManager;
	}

	public void setUserFailedWordInfoManager(
			UserFailedWordInfoManager userFailedWordInfoManager) {
		this.userFailedWordInfoManager = userFailedWordInfoManager;
	}

	public void setWordUnitEntryManager(
			WordUnitEntryManager wordUnitEntryManager) {
		this.wordUnitEntryManager = wordUnitEntryManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gtrobot.service.word.impl.WordSerice#deleteWordFromPrivateUnit(com.gtrobot.processor.word.WordProcessingSession,
	 *      com.gtrobot.model.word.UserWordStudyingInfo,
	 *      com.gtrobot.model.word.WordEntry)
	 */
	public void deleteWordFromPrivateUnit(
			WordProcessingSession studyingSession,
			UserWordStudyingInfo userWordStudyingInfo, WordEntry wordEntry) {
		WordUnitEntry wordUnitEntry = wordUnitEntryManager.getWordUnitEntry(
				wordEntry.getWordEntryId(), userWordStudyingInfo
						.getPrivateWordUnitId());
		wordUnitEntryManager.removeWordUnitEntry(wordUnitEntry.getPk());

		studyingSession.remove(wordEntry.getWordEntryId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gtrobot.service.word.impl.WordSerice#removeUserFailedWord(java.lang.Long,
	 *      java.lang.Long)
	 */
	public void removeUserFailedWord(Long wordEntryId, Long userId) {
		List ls = userFailedWordInfoManager.getUserFailedWordInfosByWord(
				userId, wordEntryId);
		for (Iterator it = ls.iterator(); it.hasNext();) {
			userFailedWordInfoManager
					.removeUserFailedWordInfo((UserFailedWordInfo) it.next());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gtrobot.service.word.impl.WordSerice#addWordToUserPrivateUnit(java.lang.Long,
	 *      java.lang.Long)
	 */
	public void addWordToUserPrivateUnit(Long userId, Long wordEntryId)
			throws XMPPException {
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		Long wordUnitId = userWordStudyingInfo.getPrivateWordUnitId();
		if (userWordStudyingInfo.getPrivateWordUnitId() == null) {
			WordUnit wordUnit = new WordUnit();
			wordUnit.setLevel(99);
			wordUnit.setName(userId + " user's word unit");
			wordUnit.setOwner(userId);
			wordUnitManager.saveWordUnit(wordUnit);
			wordUnitId = wordUnit.getWordUnitId();

			UserUnitInfo userUnitInfo = new UserUnitInfo();
			userUnitInfo.getPk().setUserId(userId);
			userUnitInfo.getPk().setWordUnit(wordUnit);
			userUnitInfoManager.saveUserUnitInfo(userUnitInfo);

			userWordStudyingInfo.setPrivateWordUnitId(wordUnitId);
			userStudyingWordInfoManager
					.saveUserWordStudyingInfo(userWordStudyingInfo);
		}
		WordUnitEntryKey key = new WordUnitEntryKey();
		key.setWordEntryId(wordEntryId);
		key.setWordUnitId(wordUnitId);
		WordUnitEntry wordUnitEntry = wordUnitEntryManager
				.getWordUnitEntry(key);
		if (wordUnitEntry == null) {
			wordUnitEntry = new WordUnitEntry();
			wordUnitEntry.setPk(key);
			wordUnitEntryManager.saveWordUnitEntry(wordUnitEntry);
		}
	}

	// TODO not used
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gtrobot.service.word.impl.WordSerice#updateUserUnitInfo(java.lang.Long,
	 *      java.lang.Long)
	 */
	@SuppressWarnings("unused")
	public void updateUserUnitInfo(Long userId, Long wordUnitId) {
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);

		if (userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.ALL_WORDS_BY_UNIT
				|| userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.FAILED_WORDS_BY_UNIT) {
			UserUnitInfo userUnitInfo = userUnitInfoManager.getUserUnitInfo(
					userId, wordUnitId);
			userUnitInfo.setStudiedTimes(userUnitInfo.getStudiedTimes() + 1);
			userUnitInfo.setLastStudied(new Date());
			userUnitInfoManager.saveUserUnitInfo(userUnitInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gtrobot.service.word.impl.WordSerice#updateUserFailedWordInfo(java.lang.Long,
	 *      java.lang.Long, com.gtrobot.model.word.WordEntry)
	 */
	public void updateUserFailedWordInfo(Long userId, Long wordUnitId,
			WordEntry wordEntry) {

		UserFailedWordInfoKey key = new UserFailedWordInfoKey();
		key.setUserId(userId);
		key.setWordUnit(wordUnitManager.getWordUnit(wordUnitId));
		key.setWordEntry(wordEntry);
		UserFailedWordInfo userFailedWordInfo = userFailedWordInfoManager
				.getUserFailedWordInfo(key);
		if (userFailedWordInfo == null) {
			// New word studying
			userFailedWordInfo = new UserFailedWordInfo();
			userFailedWordInfo.setPk(key);
		}
		userFailedWordInfo
				.setFailedCounts(userFailedWordInfo.getFailedCounts() + 1);
		userFailedWordInfo
				.setStudiedTimes(userFailedWordInfo.getStudiedTimes() + 1);
		userFailedWordInfo.setLastStudied(new Date());

		userFailedWordInfoManager.saveUserFailedWordInfo(userFailedWordInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gtrobot.service.word.impl.WordSerice#createWordProcessingSession(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public WordProcessingSession createWordProcessingSession(Long userId) {
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);

		List<Long> wordEntries = new ArrayList<Long>();
		switch (userWordStudyingInfo.getStudyingType()) {
		case UserWordStudyingInfo.ALL_WORDS_BY_UNIT:
			WordUnit studyingWordUnit = wordUnitManager
					.getWordUnit(userWordStudyingInfo.getStudyingWordUnitId());
			Iterator<WordUnitEntry> wordsIterator = studyingWordUnit
					.getWordEntries().iterator();
			while (wordsIterator.hasNext()) {
				wordEntries.add(wordsIterator.next().getPk().getWordEntryId());
			}
			break;
		case UserWordStudyingInfo.FAILED_WORDS_BY_UNIT:
			List userFailedWordInfos = userFailedWordInfoManager
					.getUserFailedWordInfos(userId, userWordStudyingInfo
							.getStudyingWordUnitId());
			Iterator<UserFailedWordInfo> userFailedWordInfosIt = userFailedWordInfos
					.iterator();
			while (userFailedWordInfosIt.hasNext()) {
				wordEntries.add(userFailedWordInfosIt.next().getPk()
						.getWordEntry().getWordEntryId());
			}
			break;
		case UserWordStudyingInfo.ALL_FAILED_WORDS:
			userFailedWordInfos = userFailedWordInfoManager
					.getUserFailedWordInfos(userId);
			userFailedWordInfosIt = userFailedWordInfos.iterator();
			while (userFailedWordInfosIt.hasNext()) {
				wordEntries.add(userFailedWordInfosIt.next().getPk()
						.getWordEntry().getWordEntryId());
			}
			break;
		default:
			log.error("Invalid StudyingType for "
					+ userWordStudyingInfo.getUserId());
		}

		WordProcessingSession ss = new WordProcessingSession(
				userWordStudyingInfo.getStudyingWordUnitId(), wordEntries);

		return ss;
	}
}
