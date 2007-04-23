package com.gtrobot.service.word;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.model.word.UserWordStudyingInfo;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.processor.word.WordProcessingSession;

public interface WordService {
	public abstract void deleteWordFromPrivateUnit(
			WordProcessingSession studyingSession,
			UserWordStudyingInfo userWordStudyingInfo, WordEntry wordEntry);

	public abstract void removeUserFailedWord(Long wordEntryId, Long userId);

	public abstract void addWordToUserPrivateUnit(Long userId, Long wordEntryId)
			throws XMPPException;

	// TODO not used
	@SuppressWarnings("unused")
	public abstract void updateUserUnitInfo(Long userId, Long wordUnitId);

	public abstract void updateUserFailedWordInfo(Long userId, Long wordUnitId,
			WordEntry wordEntry);

	@SuppressWarnings("unchecked")
	public abstract WordProcessingSession createWordProcessingSession(
			Long userId);
}
