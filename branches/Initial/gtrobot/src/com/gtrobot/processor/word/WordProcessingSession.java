package com.gtrobot.processor.word;

import java.util.List;

public class WordProcessingSession {
	private static final int START = -1;

	private Long wordUnitId;

	private List<Long> wordEntries;

	int curCount;

	public WordProcessingSession(Long wordUnitId, List<Long> wordEntries) {
		this.wordUnitId = wordUnitId;
		this.wordEntries = wordEntries;
		reset();
	}

	public int getCurCount() {
		return curCount;
	}

	public List<Long> getWordEntries() {
		return wordEntries;
	}

	public void reset() {
		curCount = START;
	}

	public Long getCurrentWord() {
		return wordEntries.get(curCount);
	}

	public boolean next() {
		curCount++;
		if (curCount >= wordEntries.size())
			return false;
		return true;
	}

	public Long getWordUnitId() {
		return wordUnitId;
	}

	public void backOne() {
		curCount--;
		if (curCount < START) {
			curCount = START;
		}
	}
}