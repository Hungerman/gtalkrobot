package com.gtrobot.processor.word;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.service.word.UserFailedWordInfoManager;
import com.gtrobot.service.word.UserStudyingWordInfoManager;
import com.gtrobot.service.word.UserUnitInfoManager;
import com.gtrobot.service.word.WordEntryManager;
import com.gtrobot.service.word.WordService;
import com.gtrobot.service.word.WordUnitEntryManager;
import com.gtrobot.service.word.WordUnitManager;

public class BaseWordProcessor extends InteractiveProcessor {
    protected static final transient Log log = LogFactory
            .getLog(BaseWordProcessor.class);

    protected UserUnitInfoManager userUnitInfoManager;

    protected WordUnitManager wordUnitManager;

    protected UserStudyingWordInfoManager userStudyingWordInfoManager;

    protected WordEntryManager wordEntryManager;

    protected UserFailedWordInfoManager userFailedWordInfoManager;

    protected WordUnitEntryManager wordUnitEntryManager;

    protected WordService wordService;

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

    public void setWordService(final WordService wordService) {
        this.wordService = wordService;
    }

    @Override
    protected int interactiveOnlineProcess(final BaseCommand cmd)
            throws XMPPException {
        final int result = super.interactiveOnlineProcess(cmd);
        if (result != InteractiveProcessor.CONTINUE) {
            return result;
        }

        final StringBuffer msgBuf = new StringBuffer();
        final List<String> cmds = cmd.getInteractiveCommands();
        if (cmds == null) {
            return InteractiveProcessor.CONTINUE;
        }
        final String cmdMsg = cmds.get(0);
        if (cmds.size() == 2) {
            final String content = cmds.get(1);
            if (".g".equalsIgnoreCase(cmdMsg)) {
                int maxResults = 20;
                if (AbstractProcessor.getUserEntryHolder().isAdmin()) {
                    maxResults = 1000;
                }
                final List ls = this.wordEntryManager.searchWordEntries(
                        content, maxResults);
                final Iterator it = ls.iterator();
                msgBuf.append(AbstractProcessor.getI18NMessage(
                        "baseword.search.result", new Object[] { ls.size() }));
                msgBuf.append(AbstractProcessor.endl);
                while (it.hasNext()) {
                    this.showWord(msgBuf, (WordEntry) it.next());
                }
                this.sendBackMessage(cmd, msgBuf.toString());
                return InteractiveProcessor.REPEAT_THIS_STEP;
            }
        }
        return InteractiveProcessor.CONTINUE;
    }

    @Override
    protected StringBuffer interactiveOnlineHelper(final BaseCommand cmd) {
        final StringBuffer msgBuf = super.interactiveOnlineHelper(cmd);
        msgBuf.append(
                AbstractProcessor.getI18NMessage("baseword.search.prompt"))
                .append(AbstractProcessor.endl);
        return msgBuf;
    }

    protected void showWord(final StringBuffer msgBuf, final WordEntry wordEntry) {
        this.showWord(msgBuf, wordEntry, true);
    }

    protected void showWord(final StringBuffer msgBuf,
            final WordEntry wordEntry, boolean showAll) {
        if (!showAll) {
            msgBuf.append(wordEntry.getWordEntryId()).append(". ");
        }

        msgBuf.append(wordEntry.getWord());
        if (wordEntry.getPronounce() != null) {
            msgBuf.append("　/");
            msgBuf.append(wordEntry.getPronounce());

            if (wordEntry.getPronounceType() != null) {
                msgBuf.append("/");
                msgBuf.append(wordEntry.getPronounceType());
            }
            msgBuf.append("/　");
        }

        if (wordEntry.getWordType() != null) {
            msgBuf.append("　(");
            msgBuf.append(wordEntry.getWordType());
            msgBuf.append(")　");
        }
        msgBuf.append(wordEntry.getMeaning());
        msgBuf.append(AbstractProcessor.endl);
        if (showAll) {
            if (wordEntry.getSentence() != null) {
                msgBuf.append(
                        AbstractProcessor
                                .getI18NMessage("baseword.showword.sentence"))
                        .append(AbstractProcessor.endl);
                msgBuf.append(wordEntry.getSentence());
                msgBuf.append(AbstractProcessor.endl);
            }

            if (wordEntry.getComments() != null) {
                msgBuf.append(
                        AbstractProcessor
                                .getI18NMessage("baseword.showword.comments"))
                        .append(AbstractProcessor.endl);
                msgBuf.append(wordEntry.getComments());
                msgBuf.append(AbstractProcessor.endl);
            }
        }
    }

    protected void commonOnlineHelper_ChangeWord(final StringBuffer msgBuf) {
        if (AbstractProcessor.getUserEntryHolder().isAdmin()) {
            msgBuf.append(
                    AbstractProcessor
                            .getI18NMessage("baseword.changeword.word"))
                    .append(AbstractProcessor.endl);
            msgBuf.append(
                    AbstractProcessor
                            .getI18NMessage("baseword.changeword.pronounce"))
                    .append(AbstractProcessor.endl);
            msgBuf
                    .append(
                            AbstractProcessor
                                    .getI18NMessage("baseword.changeword.pronounceType"))
                    .append(AbstractProcessor.endl);
            msgBuf.append(
                    AbstractProcessor
                            .getI18NMessage("baseword.changeword.wordType"))
                    .append(AbstractProcessor.endl);
            msgBuf.append(
                    AbstractProcessor
                            .getI18NMessage("baseword.changeword.meaning"))
                    .append(AbstractProcessor.endl);
            msgBuf.append(
                    AbstractProcessor
                            .getI18NMessage("baseword.changeword.sentence"))
                    .append(AbstractProcessor.endl);
            msgBuf.append(
                    AbstractProcessor
                            .getI18NMessage("baseword.changeword.comments"))
                    .append(AbstractProcessor.endl);
        }
        msgBuf.append(
                AbstractProcessor
                        .getI18NMessage("baseword.changeword.appendSentence"))
                .append(AbstractProcessor.endl);
        msgBuf.append(
                AbstractProcessor
                        .getI18NMessage("baseword.changeword.appendConments"))
                .append(AbstractProcessor.endl);
        msgBuf.append(
                AbstractProcessor
                        .getI18NMessage("baseword.changeword.reportError"))
                .append(AbstractProcessor.endl);
    }

    protected int commonOnlineProcess_ChangeWord(final BaseCommand cmd,
            final List<String> cmds, final WordEntry wordEntry)
            throws XMPPException {
        StringBuffer msgBuf = new StringBuffer();
        if (cmds == null) {
            return InteractiveProcessor.CONTINUE;
        }
        final String cmdMsg = cmds.get(0);
        boolean flag = false;
        if (cmds.size() == 2) {
            // Process the command with parameter
            final String content = cmds.get(1);
            if (AbstractProcessor.getUserEntryHolder().isAdmin()) {
                if (".cw".equalsIgnoreCase(cmdMsg)) {
                    final WordEntry wordEntryTemp = this.wordEntryManager
                            .getWordEntry(content);
                    if (wordEntryTemp == null) {
                        wordEntry.setWord(content);
                        msgBuf
                                .append(
                                        AbstractProcessor
                                                .getI18NMessage("baseword.changeword.word.result"))
                                .append(AbstractProcessor.endl);
                    } else {
                        msgBuf
                                .append(
                                        AbstractProcessor
                                                .getI18NMessage("baseword.changeword.word.fail"))
                                .append(AbstractProcessor.endl);
                    }
                    flag = true;
                }
                if (".cp".equalsIgnoreCase(cmdMsg)) {
                    wordEntry.setPronounce(content);
                    flag = true;
                    msgBuf
                            .append(
                                    AbstractProcessor
                                            .getI18NMessage("baseword.changeword.pronounce.result"))
                            .append(AbstractProcessor.endl);
                }
                if (".cpt".equalsIgnoreCase(cmdMsg)) {
                    wordEntry.setPronounceType(content);
                    flag = true;
                    msgBuf
                            .append(
                                    AbstractProcessor
                                            .getI18NMessage("baseword.changeword.pronounceType.result"))
                            .append(AbstractProcessor.endl);
                }
                if (".cwt".equalsIgnoreCase(cmdMsg)) {
                    wordEntry.setWordType(content);
                    flag = true;
                    msgBuf
                            .append(
                                    AbstractProcessor
                                            .getI18NMessage("baseword.changeword.wordType.result"))
                            .append(AbstractProcessor.endl);
                }
                if (".cm".equalsIgnoreCase(cmdMsg)) {
                    wordEntry.setMeaning(content);
                    flag = true;
                    msgBuf
                            .append(
                                    AbstractProcessor
                                            .getI18NMessage("baseword.changeword.meaning.result"))
                            .append(AbstractProcessor.endl);
                }
                if (".cs".equalsIgnoreCase(cmdMsg)) {
                    wordEntry.setSentence(content);
                    flag = true;
                    msgBuf
                            .append(
                                    AbstractProcessor
                                            .getI18NMessage("baseword.changeword.sentence.result"))
                            .append(AbstractProcessor.endl);
                }
                if (".cn".equalsIgnoreCase(cmdMsg)) {
                    wordEntry.setComments(content);
                    flag = true;
                    msgBuf
                            .append(
                                    AbstractProcessor
                                            .getI18NMessage("baseword.changeword.comments.result"))
                            .append(AbstractProcessor.endl);
                }
            }

            if (".as".equalsIgnoreCase(cmdMsg)) {
                if (wordEntry.getSentence() != null) {
                    msgBuf.append(wordEntry.getSentence());
                    msgBuf.append(AbstractProcessor.endl);
                }
                msgBuf.append(content.trim());
                wordEntry.setSentence(msgBuf.toString());

                msgBuf = new StringBuffer();
                flag = true;
                msgBuf
                        .append(
                                AbstractProcessor
                                        .getI18NMessage("baseword.changeword.appendSentence.result"))
                        .append(AbstractProcessor.endl);
            }
            if (".an".equalsIgnoreCase(cmdMsg)) {
                if (wordEntry.getComments() != null) {
                    msgBuf.append(wordEntry.getComments());
                    msgBuf.append(AbstractProcessor.endl);
                }
                msgBuf.append(content.trim());
                wordEntry.setComments(msgBuf.toString());

                msgBuf = new StringBuffer();
                flag = true;
                msgBuf
                        .append(
                                AbstractProcessor
                                        .getI18NMessage("baseword.changeword.appendConments.result"))
                        .append(AbstractProcessor.endl);
            }
            if (".rr".equalsIgnoreCase(cmdMsg)) {
                if (wordEntry.getComments() != null) {
                    msgBuf.append(wordEntry.getComments());
                    msgBuf.append(AbstractProcessor.endl);
                }
                msgBuf.append(AbstractProcessor.endl);
                msgBuf.append("Error reported by: \"");
                msgBuf.append(AbstractProcessor.getUserEntryHolder()
                        .getNickName());
                msgBuf.append("\" <");
                msgBuf.append(AbstractProcessor.getUserEntryHolder().getJid());
                msgBuf.append(">: ");
                msgBuf.append(AbstractProcessor.endl);

                msgBuf.append(content.trim());
                wordEntry.setComments(msgBuf.toString());
                wordEntry.setHasError(true);

                msgBuf = new StringBuffer();
                flag = true;
                msgBuf
                        .append(
                                AbstractProcessor
                                        .getI18NMessage("baseword.changeword.reportError.result"))
                        .append(AbstractProcessor.endl);
            }
        }
        if (flag) {
            this.wordEntryManager.saveWordEntry(wordEntry);
            this.showWord(msgBuf, wordEntry);
            this.sendBackMessage(cmd, msgBuf.toString());
            cmd.setProcessed(true);
        }
        return InteractiveProcessor.CONTINUE;

    }
}
