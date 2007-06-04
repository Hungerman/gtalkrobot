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

    protected UserUnitInfoManager userUnitInfoManager;

    protected WordUnitManager wordUnitManager;

    protected UserStudyingWordInfoManager userStudyingWordInfoManager;

    protected WordEntryManager wordEntryManager;

    protected UserFailedWordInfoManager userFailedWordInfoManager;

    protected WordUnitEntryManager wordUnitEntryManager;

    public void setWordUnitManager(final WordUnitManager wordUnitManager) {
        this.wordUnitManager = wordUnitManager;
    }

    public void setUserUnitInfoManager(
            final UserUnitInfoManager userUnitInfoManager) {
        this.userUnitInfoManager = userUnitInfoManager;
    }

    public void setUserStudyingWordInfoManager(
            final UserStudyingWordInfoManager userStudyingWordInfoManager) {
        this.userStudyingWordInfoManager = userStudyingWordInfoManager;
    }

    public void setWordEntryManager(final WordEntryManager wordEntryManager) {
        this.wordEntryManager = wordEntryManager;
    }

    public void setUserFailedWordInfoManager(
            final UserFailedWordInfoManager userFailedWordInfoManager) {
        this.userFailedWordInfoManager = userFailedWordInfoManager;
    }

    public void setWordUnitEntryManager(
            final WordUnitEntryManager wordUnitEntryManager) {
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
            final WordProcessingSession studyingSession,
            final UserWordStudyingInfo userWordStudyingInfo,
            final WordEntry wordEntry) {
        final WordUnitEntry wordUnitEntry = this.wordUnitEntryManager
                .getWordUnitEntry(wordEntry.getWordEntryId(),
                        userWordStudyingInfo.getPrivateWordUnitId());
        this.wordUnitEntryManager.removeWordUnitEntry(wordUnitEntry.getPk());

        studyingSession.remove(wordEntry.getWordEntryId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gtrobot.service.word.impl.WordSerice#removeUserFailedWord(java.lang.Long,
     *      java.lang.Long)
     */
    public void removeUserFailedWord(final Long wordEntryId, final Long userId) {
        final List ls = this.userFailedWordInfoManager
                .getUserFailedWordInfosByWord(userId, wordEntryId);
        for (final Iterator it = ls.iterator(); it.hasNext();) {
            this.userFailedWordInfoManager
                    .removeUserFailedWordInfo((UserFailedWordInfo) it.next());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gtrobot.service.word.impl.WordSerice#addWordToUserPrivateUnit(java.lang.Long,
     *      java.lang.Long)
     */
    public void addWordToUserPrivateUnit(final Long userId,
            final Long wordEntryId) throws XMPPException {
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(userId);
        Long wordUnitId = userWordStudyingInfo.getPrivateWordUnitId();
        if (userWordStudyingInfo.getPrivateWordUnitId() == null) {
            final WordUnit wordUnit = new WordUnit();
            wordUnit.setLevel(99);
            wordUnit.setName(userId + " user's word unit");
            wordUnit.setOwner(userId);
            this.wordUnitManager.saveWordUnit(wordUnit);
            wordUnitId = wordUnit.getWordUnitId();

            final UserUnitInfo userUnitInfo = new UserUnitInfo();
            userUnitInfo.getPk().setUserId(userId);
            userUnitInfo.getPk().setWordUnit(wordUnit);
            this.userUnitInfoManager.saveUserUnitInfo(userUnitInfo);

            userWordStudyingInfo.setPrivateWordUnitId(wordUnitId);
            this.userStudyingWordInfoManager
                    .saveUserWordStudyingInfo(userWordStudyingInfo);
        }
        final WordUnitEntryKey key = new WordUnitEntryKey();
        key.setWordEntryId(wordEntryId);
        key.setWordUnitId(wordUnitId);
        WordUnitEntry wordUnitEntry = this.wordUnitEntryManager
                .getWordUnitEntry(key);
        if (wordUnitEntry == null) {
            wordUnitEntry = new WordUnitEntry();
            wordUnitEntry.setPk(key);
            this.wordUnitEntryManager.saveWordUnitEntry(wordUnitEntry);
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
    public void updateUserUnitInfo(final Long userId, final Long wordUnitId) {
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(userId);

        if ((userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.ALL_WORDS_BY_UNIT)
                || (userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.FAILED_WORDS_BY_UNIT)) {
            final UserUnitInfo userUnitInfo = this.userUnitInfoManager
                    .getUserUnitInfo(userId, wordUnitId);
            userUnitInfo.setStudiedTimes(userUnitInfo.getStudiedTimes() + 1);
            userUnitInfo.setLastStudied(new Date());
            this.userUnitInfoManager.saveUserUnitInfo(userUnitInfo);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gtrobot.service.word.impl.WordSerice#updateUserFailedWordInfo(java.lang.Long,
     *      java.lang.Long, com.gtrobot.model.word.WordEntry)
     */
    public void updateUserFailedWordInfo(final Long userId,
            final Long wordUnitId, final WordEntry wordEntry) {

        final UserFailedWordInfoKey key = new UserFailedWordInfoKey();
        key.setUserId(userId);
        key.setWordUnit(this.wordUnitManager.getWordUnit(wordUnitId));
        key.setWordEntry(wordEntry);
        UserFailedWordInfo userFailedWordInfo = this.userFailedWordInfoManager
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

        this.userFailedWordInfoManager
                .saveUserFailedWordInfo(userFailedWordInfo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gtrobot.service.word.impl.WordSerice#createWordProcessingSession(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public WordProcessingSession createWordProcessingSession(final Long userId) {
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(userId);

        final List<Long> wordEntries = new ArrayList<Long>();
        switch (userWordStudyingInfo.getStudyingType()) {
        case UserWordStudyingInfo.ALL_WORDS_BY_UNIT:
            final WordUnit studyingWordUnit = this.wordUnitManager
                    .getWordUnit(userWordStudyingInfo.getStudyingWordUnitId());
            final Iterator<WordUnitEntry> wordsIterator = studyingWordUnit
                    .getWordEntries().iterator();
            while (wordsIterator.hasNext()) {
                wordEntries.add(wordsIterator.next().getPk().getWordEntryId());
            }
            break;
        case UserWordStudyingInfo.FAILED_WORDS_BY_UNIT:
            List userFailedWordInfos = this.userFailedWordInfoManager
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
            userFailedWordInfos = this.userFailedWordInfoManager
                    .getUserFailedWordInfos(userId);
            userFailedWordInfosIt = userFailedWordInfos.iterator();
            while (userFailedWordInfosIt.hasNext()) {
                wordEntries.add(userFailedWordInfosIt.next().getPk()
                        .getWordEntry().getWordEntryId());
            }
            break;
        default:
            WordServiceImpl.log.error("Invalid StudyingType for "
                    + userWordStudyingInfo.getUserId());
        }

        final WordProcessingSession ss = new WordProcessingSession(
                userWordStudyingInfo.getStudyingWordUnitId(), wordEntries);

        return ss;
    }
}
