package com.gtrobot.processor.word;

import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.utils.CommonUtils;

public class WordManagementProcessor extends BaseWordProcessor {
    private static final int STEP_TO_WORD_MANAGEMENT = 1000;

    private static final int STEP_TO_SUB_LOOP_WORDS = 1010;

    private static final int STEP_TO_SUB_CHANGE_WORD = 1020;

    /**
     * Menu
     */
    @Override
    protected int interactiveProcessPrompt_10(final BaseCommand cmd)
            throws XMPPException {
        return WordManagementProcessor.STEP_TO_WORD_MANAGEMENT;
    }

    /**
     * 选择单元 STEP_TO_WORD_MANAGEMENT = 1000
     */
    protected StringBuffer interactiveOnlineHelper_1000(
            final ProcessableCommand cmd) {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append("> .c <wordUnitName> Create a new word unit.").append(
                AbstractProcessor.endl);
        msgBuf.append("> .l List all word units.").append(
                AbstractProcessor.endl);

        return msgBuf;
    }

    @SuppressWarnings("unchecked")
    protected int interactiveOnlineProcess_1000(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        final List<String> cmds = cmd.getInteractiveCommands();
        if (cmds == null) {
            return InteractiveProcessor.CONTINUE;
        }
        final String cmdMsg = cmds.get(0);

        if (cmds.size() == 1) {
            if (".l".equalsIgnoreCase(cmdMsg)) {
                final List results = this.wordUnitManager.getWordUnits();

                msgBuf.append("Totaly, there are : " + results.size()
                        + " units.");
                msgBuf.append(AbstractProcessor.endl);

                for (final Iterator<WordUnit> it = results.iterator(); it
                        .hasNext();) {
                    final WordUnit wordUnit = it.next();
                    msgBuf.append(wordUnit.getWordUnitId()).append("  ");
                    msgBuf.append(wordUnit.getName()).append("  ");
                    msgBuf.append(wordUnit.getWordCount()).append("  ");
                    msgBuf.append(wordUnit.getLevel()).append("  ");

                    msgBuf.append(AbstractProcessor.endl);
                }

                msgBuf.append(AbstractProcessor.endl);
                this.sendBackMessage(cmd, msgBuf.toString());
                return InteractiveProcessor.CONTINUE;
            }
        }
        if (cmds.size() == 2) {
            final String content = cmds.get(1);
            if (".c".equalsIgnoreCase(cmdMsg)) {
                final WordUnit wordUnitTemp = this.wordUnitManager
                        .getWordUnit(content);
                if (wordUnitTemp == null) {
                    final WordUnit wordUnit = new WordUnit();
                    wordUnit.setName(content);
                    this.wordUnitManager.saveWordUnit(wordUnit);

                    msgBuf.append("New word unit has been created with id: "
                            + wordUnit.getWordUnitId());
                } else {
                    msgBuf.append("Error: Word unit has been there with id: "
                            + wordUnitTemp.getWordUnitId());
                }
                this.sendBackMessage(cmd, msgBuf.toString());
                return InteractiveProcessor.CONTINUE;
            }
        }
        return InteractiveProcessor.CONTINUE;
    }

    protected int interactiveProcessPrompt_1000(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        msgBuf.append("Please choose one to modify...");
        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    @SuppressWarnings("unchecked")
    protected int interactiveProcess_1000(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        Long wordUnitId = null;

        final String cmdMsg = cmd.getOriginMessage();
        try {
            wordUnitId = new Long(cmdMsg);
            // check word unit exist
            final WordUnit wordUnit = this.wordUnitManager
                    .getWordUnit(wordUnitId);
            if (wordUnit == null) {
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException e) {
            msgBuf.append("You entered one invalid word unit number.");
            return InteractiveProcessor.REPEAT_THIS_STEP;
        }
        this.setSession(wordUnitId);

        // Next Step
        return WordManagementProcessor.STEP_TO_SUB_LOOP_WORDS;
    }

    /**
     * 选择单词 STEP_TO_SUB_LOOP_WORDS = 1010
     */
    protected StringBuffer interactiveOnlineHelper_1010(
            final ProcessableCommand cmd) {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append("> .c <wordName> Create a new word.").append(
                AbstractProcessor.endl);
        msgBuf.append("> .s <wordId> Add words into unit.").append(
                AbstractProcessor.endl);

        msgBuf.append("> .d <wordId> Delete a word from unit.").append(
                AbstractProcessor.endl);
        msgBuf.append("> .l List all words.").append(AbstractProcessor.endl);

        return msgBuf;
    }

    @SuppressWarnings("unchecked")
    protected int interactiveOnlineProcess_1010(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        final List<String> cmds = cmd.getInteractiveCommands();
        if (cmds == null) {
            return InteractiveProcessor.CONTINUE;
        }
        final String cmdMsg = cmds.get(0);

        final Long wordUnitId = (Long) this.getSession();

        if (cmds.size() == 1) {
            if (".l".equalsIgnoreCase(cmdMsg)) {
                final WordUnit wordUnit = this.wordUnitManager
                        .getWordUnit(wordUnitId);

                final Iterator<WordUnitEntry> iterator = wordUnit
                        .getWordEntries().iterator();
                msgBuf.append("Totaly, there are : "
                        + wordUnit.getWordEntries().size() + " words in unit: "
                        + wordUnit.getWordUnitId() + "." + wordUnit.getName());
                msgBuf.append(AbstractProcessor.endl);

                while (iterator.hasNext()) {
                    final WordEntry wordEntry = this.wordEntryManager
                            .getWordEntry(iterator.next().getPk()
                                    .getWordEntryId());
                    this.showWord(msgBuf, wordEntry, false);
                }
                msgBuf.append(AbstractProcessor.endl);
                this.sendBackMessage(cmd, msgBuf.toString());
                cmd.setProcessed(true);
                return InteractiveProcessor.CONTINUE;
            }
        }
        if (cmds.size() == 2) {
            final String content = cmds.get(1);
            if (".c".equalsIgnoreCase(cmdMsg)) {
                WordEntry wordEntry = this.wordEntryManager
                        .getWordEntry(content);
                if (wordEntry == null) {
                    wordEntry = new WordEntry();
                    wordEntry.setWord(content);
                    wordEntry.setPronounce(content);
                    wordEntry.setMeaning(content);
                    this.wordEntryManager.saveWordEntry(wordEntry);
                }

                final WordUnitEntry wordUnitEntry = new WordUnitEntry();
                wordUnitEntry.getPk()
                        .setWordEntryId(wordEntry.getWordEntryId());
                wordUnitEntry.getPk().setWordUnitId(wordUnitId);
                if (this.wordUnitEntryManager.getWordUnitEntry(wordUnitEntry
                        .getPk()) == null) {
                    this.wordUnitEntryManager.saveWordUnitEntry(wordUnitEntry);
                }

                msgBuf.append("New word has been created with id: "
                        + wordEntry.getWordEntryId());
                this.sendBackMessage(cmd, msgBuf.toString());
                cmd.setProcessed(true);
                return InteractiveProcessor.CONTINUE;
            }
            if (".d".equalsIgnoreCase(cmdMsg)) {
                final List<String> params = CommonUtils.parseCommand(content,
                        false);
                final Iterator<String> it = params.iterator();
                msgBuf.append("Word Entry: ");
                while (it.hasNext()) {
                    try {
                        final Long weId = new Long(it.next());
                        final WordUnitEntry wordUnitEntry = this.wordUnitEntryManager
                                .getWordUnitEntry(weId, wordUnitId);
                        if (wordUnitEntry != null) {
                            this.wordUnitEntryManager
                                    .removeWordUnitEntry(wordUnitEntry.getPk());
                        }
                        msgBuf.append(weId).append(",");
                    } catch (final Exception e) {
                    }
                }
                msgBuf.append(" have been removed from unit: ").append(
                        wordUnitId);
                this.sendBackMessage(cmd, msgBuf.toString());
                cmd.setProcessed(true);
                return InteractiveProcessor.CONTINUE;
            }
            if (".a".equalsIgnoreCase(cmdMsg)) {
                final List<String> params = CommonUtils.parseCommand(content,
                        false);
                final Iterator<String> it = params.iterator();
                msgBuf.append("Word Entry: ");
                while (it.hasNext()) {
                    try {
                        final Long weId = new Long(it.next());
                        WordUnitEntry wordUnitEntry = this.wordUnitEntryManager
                                .getWordUnitEntry(weId, wordUnitId);
                        if (wordUnitEntry == null) {
                            wordUnitEntry = new WordUnitEntry();
                            wordUnitEntry.getPk().setWordEntryId(weId);
                            wordUnitEntry.getPk().setWordUnitId(wordUnitId);
                            this.wordUnitEntryManager
                                    .saveWordUnitEntry(wordUnitEntry);
                        }
                        msgBuf.append(weId).append(",");
                    } catch (final Exception e) {
                    }
                }
                msgBuf.append(" have been added from unit: ")
                        .append(wordUnitId);
                this.sendBackMessage(cmd, msgBuf.toString());
                cmd.setProcessed(true);
                return InteractiveProcessor.CONTINUE;
            }
        }
        return InteractiveProcessor.CONTINUE;
    }

    protected int interactiveProcessPrompt_1010(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append("Please choose one word to modify...");
        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    protected int interactiveProcess_1010(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        final String cmdMsg = cmd.getOriginMessage();
        Long wordEntryId = null;
        WordEntry wordEntry = null;
        try {
            wordEntryId = new Long(cmdMsg);
            // check word exist
            wordEntry = this.wordEntryManager.getWordEntry(wordEntryId);
            if (wordEntry == null) {
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException e) {
            msgBuf.append("You entered one invalid word number.");
            this.sendBackMessage(cmd, msgBuf.toString());
            return InteractiveProcessor.REPEAT_THIS_STEP;
        }
        this.setTempSession(wordEntry);
        return WordManagementProcessor.STEP_TO_SUB_CHANGE_WORD;
    }

    /**
     * 修改一个单词 STEP_TO_SUB_CHANGE_WORD = 1020
     */
    protected StringBuffer interactiveOnlineHelper_1020(
            final ProcessableCommand cmd) {
        final StringBuffer msgBuf = new StringBuffer();

        this.commonOnlineHelper_ChangeWord(msgBuf);

        return msgBuf;
    }

    @SuppressWarnings("unchecked")
    protected int interactiveOnlineProcess_1020(final ProcessableCommand cmd)
            throws XMPPException {
        final List<String> cmds = cmd.getInteractiveCommands();

        return this.commonOnlineProcess_ChangeWord(cmd, cmds, (WordEntry) this
                .getTempSession());
    }

    protected int interactiveProcessPrompt_1020(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        final WordEntry wordEntry = (WordEntry) this.getTempSession();
        this.showWord(msgBuf, wordEntry);

        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append("OK?");

        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    @SuppressWarnings("unchecked")
    protected int interactiveProcess_1020(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        if (InteractiveProcessor.YES == InteractiveProcessor.checkAnswer(cmd)) {
            msgBuf.append("OK.");
            msgBuf.append(AbstractProcessor.endl);
            this.sendBackMessage(cmd, msgBuf.toString());
            return WordManagementProcessor.STEP_TO_SUB_LOOP_WORDS;
        }
        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.REPEAT_THIS_STEP;
    }
}
