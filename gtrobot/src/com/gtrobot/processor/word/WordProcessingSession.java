package com.gtrobot.processor.word;

import java.util.List;

public class WordProcessingSession {
    private static final int START = -1;

    private Long wordUnitId;

    private List<Long> wordEntries;

    int curCount;

    public WordProcessingSession(final Long wordUnitId,
            final List<Long> wordEntries) {
        this.wordUnitId = wordUnitId;
        this.wordEntries = wordEntries;
        this.reset();
    }

    public int getCurCount() {
        return this.curCount;
    }

    public List<Long> getWordEntries() {
        return this.wordEntries;
    }

    public void reset() {
        this.curCount = WordProcessingSession.START;
    }

    public Long getCurrentWord() {
        return this.wordEntries.get(this.curCount);
    }

    public boolean next() {
        this.curCount++;
        if (this.curCount >= this.wordEntries.size()) {
            return false;
        }
        return true;
    }

    public Long getWordUnitId() {
        return this.wordUnitId;
    }

    public void backOne() {
        this.curCount--;
        if (this.curCount < WordProcessingSession.START) {
            this.curCount = WordProcessingSession.START;
        }
    }

    public void remove(final Long wordEntryId) {
        this.wordEntries.remove(wordEntryId);
    }
}