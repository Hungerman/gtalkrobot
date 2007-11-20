package com.gtrobot.processor.word;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.model.word.UserUnitInfo;
import com.gtrobot.model.word.UserUnitInfoKey;
import com.gtrobot.model.word.UserWordStudyingInfo;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.utils.CommonUtils;

public class StudyWordProcessor extends BaseWordProcessor {
    protected static final transient Log log = LogFactory
            .getLog(StudyWordProcessor.class);

    private static final int STEP_TO_CONTINUE_STUDY = 1000;

    private static final int STEP_TO_CONTINUE_STUDY_MARK_FINISHED = 1100;

    private static final int STEP_TO_CONTINUE_STUDY_EXIT = 1999;

    private static final int STEP_TO_MANAGENT_ADDUNIT = 2100;

    private static final int STEP_TO_MANAGENT_SHOWUNIT = 2200;

    private static final int STEP_TO_MANAGENT_CHANGEUNIT = 2300;

    private static final int STEP_TO_MANAGENT_CHANGESTUDYTYPE = 2400;

    @Override
    protected void initMenuComamndToStepMappings() {
        super.initMenuComamndToStepMappings();

        this.addCommandToStepMapping("1",
                StudyWordProcessor.STEP_TO_CONTINUE_STUDY);
        this.addCommandToStepMapping("21",
                StudyWordProcessor.STEP_TO_MANAGENT_ADDUNIT);
        this.addCommandToStepMapping("22",
                StudyWordProcessor.STEP_TO_MANAGENT_SHOWUNIT);
        this.addCommandToStepMapping("23",
                StudyWordProcessor.STEP_TO_MANAGENT_CHANGEUNIT);
        this.addCommandToStepMapping("24",
                StudyWordProcessor.STEP_TO_MANAGENT_CHANGESTUDYTYPE);
    }

    @Override
    protected void prepareMenuInfo(final List<String> menuInfo) {
        menuInfo.add("studyword.conitinue");
        menuInfo.add("studyword.management");
        menuInfo.add("studyword.management.addunit");
        menuInfo.add("studyword.management.showunit");
        menuInfo.add("studyword.management.changeunit");
        menuInfo.add("studyword.management.changemode");
    }

    /**
     * Menu
     */
    @Override
    protected int interactiveProcessPrompt_10(final BaseCommand cmd)
            throws XMPPException {
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(cmd.getUserEntry().getUserId());
        if (userWordStudyingInfo == null) {
            return StudyWordProcessor.STEP_TO_MANAGENT_CHANGEUNIT;
        }

        return super.interactiveProcessPrompt_10(cmd);
    }

    /**
     * 继续当前的学习 STEP_TO_CONTINUE_STUDY = 1000
     */
    protected int interactiveProcess_1000(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        WordProcessingSession studyingSession = null;

        try {
            studyingSession = this.getWordProcessingSession(cmd);
        } catch (final Exception e) {
            StudyWordProcessor.log.error("Error", e);

            msgBuf.append(AbstractProcessor
                    .getI18NMessage("studyword.conitinue.unitNotSet"));
            msgBuf.append(AbstractProcessor.endl);
            this.sendBackMessage(cmd, msgBuf.toString());
            return StudyWordProcessor.STEP_TO_CONTINUE_STUDY_EXIT;
        }
        if (studyingSession.next()) {
            return InteractiveProcessor.CONTINUE;
        } else {
            studyingSession.reset();
            return StudyWordProcessor.STEP_TO_CONTINUE_STUDY_MARK_FINISHED;
        }
    }

    protected StringBuffer interactiveOnlineHelper_1001(
            final ProcessableCommand cmd) {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf
                .append(
                        AbstractProcessor
                                .getI18NMessage("studyword.conitinue.onlineHelper.prievios"))
                .append(AbstractProcessor.endl);
        msgBuf
                .append(
                        AbstractProcessor
                                .getI18NMessage("studyword.conitinue.onlineHelper.skip"))
                .append(AbstractProcessor.endl);

        final Long userId = cmd.getUserEntry().getUserId();
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(userId);
        if ((userWordStudyingInfo.getStudyingType() != UserWordStudyingInfo.ALL_FAILED_WORDS)
                && userWordStudyingInfo.getStudyingWordUnitId().equals(
                        userWordStudyingInfo.getPrivateWordUnitId())) {
            msgBuf
                    .append(
                            AbstractProcessor
                                    .getI18NMessage("studyword.conitinue.onlineHelper.delete"))
                    .append(AbstractProcessor.endl);
        }

        msgBuf
                .append(
                        AbstractProcessor
                                .getI18NMessage("studyword.conitinue.onlineHelper.add"))
                .append(AbstractProcessor.endl);
        msgBuf
                .append(
                        AbstractProcessor
                                .getI18NMessage("studyword.conitinue.onlineHelper.finish"))
                .append(AbstractProcessor.endl);

        this.commonOnlineHelper_ChangeWord(msgBuf);

        return msgBuf;
    }

    protected int interactiveOnlineProcess_1001(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        final List<String> cmds = cmd.getInteractiveCommands();
        if (cmds == null) {
            return InteractiveProcessor.CONTINUE;
        }
        final String cmdMsg = cmds.get(0);

        final WordProcessingSession studyingSession = this
                .getWordProcessingSession(cmd);
        final Long wordEntryId = studyingSession.getCurrentWord();
        final WordEntry wordEntry = this.wordEntryManager
                .getWordEntry(wordEntryId);

        if (cmds.size() == 1) {
            if (".p".equalsIgnoreCase(cmdMsg)) {
                studyingSession.backOne();
                studyingSession.backOne();
                cmd.setProcessed(true);
                return StudyWordProcessor.STEP_TO_CONTINUE_STUDY;
            }
            if (".s".equalsIgnoreCase(cmdMsg)) {
                this.showWord(msgBuf, wordEntry);
                this.sendBackMessage(cmd, msgBuf.toString());
                cmd.setProcessed(true);
                return StudyWordProcessor.STEP_TO_CONTINUE_STUDY;
            }
            if (".d".equalsIgnoreCase(cmdMsg)) {
                final Long userId = cmd.getUserEntry().getUserId();
                final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                        .getUserWordStudyingInfo(userId);
                if ((userWordStudyingInfo.getStudyingType() != UserWordStudyingInfo.ALL_FAILED_WORDS)
                        && userWordStudyingInfo.getStudyingWordUnitId().equals(
                                userWordStudyingInfo.getPrivateWordUnitId())) {
                    this.wordService.deleteWordFromPrivateUnit(studyingSession,
                            userWordStudyingInfo, wordEntry);
                }
                this.showWord(msgBuf, wordEntry);
                msgBuf
                        .append(
                                AbstractProcessor
                                        .getI18NMessage("studyword.conitinue.onlineHelper.delete.result"))
                        .append(AbstractProcessor.endl);
                this.sendBackMessage(cmd, msgBuf.toString());
                cmd.setProcessed(true);
                return StudyWordProcessor.STEP_TO_CONTINUE_STUDY;
            }
            if (".a".equalsIgnoreCase(cmdMsg)) {
                final Long userId = cmd.getUserEntry().getUserId();
                this.wordService.addWordToUserPrivateUnit(userId, wordEntryId);

                this.showWord(msgBuf, wordEntry);
                msgBuf
                        .append(
                                AbstractProcessor
                                        .getI18NMessage("studyword.conitinue.onlineHelper.add.result"))
                        .append(AbstractProcessor.endl);
                this.sendBackMessage(cmd, msgBuf.toString());
                cmd.setProcessed(true);
                return StudyWordProcessor.STEP_TO_CONTINUE_STUDY;
            }
            if (".f".equalsIgnoreCase(cmdMsg)) {
                final Long userId = cmd.getUserEntry().getUserId();
                this.wordService.addWordToUserPrivateUnit(userId, wordEntryId);
                this.wordService.removeUserFailedWord(wordEntryId, userId);

                this.showWord(msgBuf, wordEntry);
                msgBuf
                        .append(
                                AbstractProcessor
                                        .getI18NMessage("studyword.conitinue.onlineHelper.add"))
                        .append(AbstractProcessor.endl);
                this.sendBackMessage(cmd, msgBuf.toString());
                cmd.setProcessed(true);
                return StudyWordProcessor.STEP_TO_CONTINUE_STUDY;
            }
        }

        return this.commonOnlineProcess_ChangeWord(cmd, cmds, wordEntry);
    }

    protected int interactiveProcessPrompt_1001(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        final WordProcessingSession studyingSession = this
                .getWordProcessingSession(cmd);

        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage("studyword.separator"));
        msgBuf.append(AbstractProcessor.endl);

        final Long wordEntryId = studyingSession.getCurrentWord();
        final WordEntry wordEntry = this.wordEntryManager
                .getWordEntry(wordEntryId);

        msgBuf.append(AbstractProcessor.getI18NMessage(
                "studyword.conitinue.word.prompt", new Object[] {
                        (studyingSession.getCurCount() + 1),
                        studyingSession.getWordEntries().size() }));

        final Long userId = cmd.getUserEntry().getUserId();
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(userId);
        if (userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.ALL_FAILED_WORDS) {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("studyword.conitinue.word.faildUnit"));
        } else {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("studyword.conitinue.word.unit",
                            new Object[] { userWordStudyingInfo
                                    .getStudyingWordUnitId() }));
        }
        msgBuf.append(wordEntry.getWord());
        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    protected int interactiveProcess_1001(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        final WordProcessingSession studyingSession = this
                .getWordProcessingSession(cmd);
        final Long wordEntryId = studyingSession.getCurrentWord();
        final WordEntry wordEntry = this.wordEntryManager
                .getWordEntry(wordEntryId);

        final String cmdMsg = cmd.getOriginMessage();
        if (wordEntry.getWord().equals(cmdMsg)) {
            this.showWord(msgBuf, wordEntry);
            this.sendBackMessage(cmd, msgBuf.toString());
            return StudyWordProcessor.STEP_TO_CONTINUE_STUDY;
        } else {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("studyword.conitinue.word.enterPrompt"));
            msgBuf.append(AbstractProcessor.endl);
            this.showWord(msgBuf, wordEntry, false);

            this.wordService.updateUserFailedWordInfo(cmd.getUserEntry()
                    .getUserId(), studyingSession.getWordUnitId(), wordEntry);

            this.sendBackMessage(cmd, msgBuf.toString());
            return InteractiveProcessor.REPEAT_THIS_STEP;
        }
    }

    /**
     * STEP_TO_CONTINUE_STUDY_MARK_FINISHED = 1100
     */
    protected int interactiveProcess_1100(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        final Long userId = cmd.getUserEntry().getUserId();
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(userId);
        if ((userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.ALL_WORDS_BY_UNIT)
                || (userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.FAILED_WORDS_BY_UNIT)) {

            final long count = this.userFailedWordInfoManager
                    .getFailedWordCount(userId, userWordStudyingInfo
                            .getStudyingWordUnitId());
            if ((count == 0)
                    && !userWordStudyingInfo.getStudyingWordUnitId().equals(
                            userWordStudyingInfo.getPrivateWordUnitId())) {
                return InteractiveProcessor.CONTINUE;
            } else {
                msgBuf.append(AbstractProcessor.getI18NMessage(
                        "studyword.conitinue.failed", new Object[] { count }));
                msgBuf.append(AbstractProcessor.endl);
                this.sendBackMessage(cmd, msgBuf.toString());

                return StudyWordProcessor.STEP_TO_CONTINUE_STUDY_EXIT;
            }
        } else {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("studyword.conitinue.finished"));
            msgBuf.append(AbstractProcessor.endl);
            this.sendBackMessage(cmd, msgBuf.toString());
            return StudyWordProcessor.STEP_TO_CONTINUE_STUDY_EXIT;
        }
    }

    protected int interactiveProcessPrompt_1101(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        msgBuf.append(
                AbstractProcessor
                        .getI18NMessage("studyword.conitinue.finish.prompt"))
                .append(AbstractProcessor.endl);
        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    protected int interactiveProcess_1101(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        if (InteractiveProcessor.YES == InteractiveProcessor.checkAnswer(cmd)) {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("studyword.conitinue.finish.prompt.YES"));
            msgBuf.append(AbstractProcessor.endl);
            final Long userId = cmd.getUserEntry().getUserId();
            final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                    .getUserWordStudyingInfo(userId);
            final UserUnitInfo userUnitInfo = this.userUnitInfoManager
                    .getUserUnitInfo(userId, userWordStudyingInfo
                            .getStudyingWordUnitId());
            userUnitInfo.setFinished(true);
            this.userUnitInfoManager.saveUserUnitInfo(userUnitInfo);

            msgBuf
                    .append(AbstractProcessor
                            .getI18NMessage("studyword.conitinue.finish.prompt.YES.next"));
        } else {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("studyword.conitinue.finish.prompt.NO"));
        }
        msgBuf.append(AbstractProcessor.endl);
        this.sendBackMessage(cmd, msgBuf.toString());
        return StudyWordProcessor.STEP_TO_CONTINUE_STUDY_EXIT;
    }

    /**
     * STEP_TO_CONTINUE_STUDY_EXIT = 1999
     */
    protected int interactiveProcess_1999(final ProcessableCommand cmd)
            throws XMPPException {
        this.setSession(null);
        return InteractiveProcessor.STEP_TO_MENU;
    }

    @SuppressWarnings("unchecked")
    protected int interactiveProcessPrompt_2100(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        // formartMessageHeader(cmd, msgBuf);

        final List results = this.wordUnitManager.getWordUnitsNotInUserList(cmd
                .getUserEntry().getUserId());

        msgBuf.append(AbstractProcessor.endl);
        for (final Iterator<WordUnit> it = results.iterator(); it.hasNext();) {
            final WordUnit wordUnit = it.next();
            msgBuf.append(wordUnit.getWordUnitId()).append("  ");
            msgBuf.append(wordUnit.getName()).append("  ");
            msgBuf.append(wordUnit.getWordCount()).append("  ");
            msgBuf.append(wordUnit.getLevel()).append("  ");

            msgBuf.append(AbstractProcessor.endl);
        }

        msgBuf.append(AbstractProcessor.getI18NMessage(
                "studyword.addunit.info", new Object[] { results.size() }));
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage(
                "studyword.addunit.prompt", new Object[] { results.size() }));
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.endl);

        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    /**
     * Process the user's selection, add it to the user's list
     */
    protected int interactiveProcess_2100(final ProcessableCommand cmd)
            throws XMPPException {
        final List<String> units = CommonUtils.parseCommand(cmd
                .getOriginMessage().trim(), false);

        final List<String> failedUnits = new ArrayList<String>();

        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(AbstractProcessor
                .getI18NMessage("studyword.addunit.result.prompt"));
        for (final String unitId : units) {
            Long wordUnitId = null;

            try {
                wordUnitId = new Long(unitId);

                // check word unit exist
                final WordUnit wordUnit = this.wordUnitManager
                        .getWordUnit(wordUnitId);
                if (wordUnit == null) {
                    failedUnits.add(unitId);
                    continue;
                }

                final UserUnitInfo userUnitInfo = new UserUnitInfo();
                userUnitInfo.getPk().setUserId(cmd.getUserEntry().getUserId());
                userUnitInfo.getPk().getWordUnit().setWordUnitId(wordUnitId);
                this.userUnitInfoManager.saveUserUnitInfo(userUnitInfo);

                msgBuf.append(unitId).append(",");
            } catch (final NumberFormatException e) {
                continue;
            }
        }
        msgBuf.append(AbstractProcessor.endl);

        // msgBuf.append("The folloing units ");
        // for (Iterator<String> it = failedUnits.iterator(); it.hasNext();) {
        // String unitId = it.next();
        // msgBuf.append(unitId).append(",");
        // }
        // msgBuf.append(" are invalid, can't be added.");
        // msgBuf.append(endl);

        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.STEP_TO_MENU;
    }

    /**
     * STEP_TO_MANAGENT_SHOWUNIT = 2200 <br>
     * Show the selected unit in the user's list
     */
    @SuppressWarnings("unchecked")
    protected int interactiveProcess_2200(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        final List<UserUnitInfo> results = this.userUnitInfoManager
                .getUserUnitInfos(cmd.getUserEntry().getUserId());

        msgBuf.append(AbstractProcessor.getI18NMessage(
                "studyword.showunit.prompt", new Object[] { results.size() }));
        msgBuf.append(AbstractProcessor.endl);

        for (final UserUnitInfo userUnitInfo : results) {
            msgBuf.append(userUnitInfo.getPk().getWordUnit().getWordUnitId())
                    .append("  ");
            msgBuf.append(userUnitInfo.getPk().getWordUnit().getName()).append(
                    " ");
            msgBuf.append(userUnitInfo.getPk().getWordUnit().getWordCount())
                    .append(" ");
            msgBuf.append(userUnitInfo.isFinished()).append(" ");
            msgBuf.append(userUnitInfo.getLastStudied());

            msgBuf.append(AbstractProcessor.endl);
        }

        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.STEP_TO_MENU;
    }

    /**
     * STEP_TO_MANAGENT_CHANGEUNIT = 2300 <br>
     * Change the user current studying unit.
     */
    @SuppressWarnings("unchecked")
    protected int interactiveProcessPrompt_2300(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        // formartMessageHeader(cmd, msgBuf);

        final List<UserUnitInfo> results = this.userUnitInfoManager
                .getNotFinishedUserUnitInfos(cmd.getUserEntry().getUserId());

        if (results.size() == 0) {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("studyword.changeunit.noUnit"));
            msgBuf.append(AbstractProcessor.endl);
            this.sendBackMessage(cmd, msgBuf.toString());
            return StudyWordProcessor.STEP_TO_MANAGENT_ADDUNIT;
        }

        msgBuf.append(AbstractProcessor.getI18NMessage(
                "studyword.changeunit.info", new Object[] { results.size() }));
        msgBuf.append(AbstractProcessor.endl);

        for (final UserUnitInfo userUnitInfo : results) {
            msgBuf.append(userUnitInfo.getPk().getWordUnit().getWordUnitId())
                    .append("  ");
            msgBuf.append(userUnitInfo.getPk().getWordUnit().getName()).append(
                    " ");
            msgBuf.append(userUnitInfo.getPk().getWordUnit().getWordCount())
                    .append(" ");
            msgBuf.append(userUnitInfo.getLastStudied());

            msgBuf.append(AbstractProcessor.endl);
        }
        msgBuf.append(AbstractProcessor.endl);

        final Long userId = cmd.getUserEntry().getUserId();
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(userId);
        if (userWordStudyingInfo != null) {
            msgBuf.append(AbstractProcessor.getI18NMessage(
                    "studyword.changeunit.status", new Object[] {
                            userWordStudyingInfo.getStudyingWordUnitId(),
                            userWordStudyingInfo.getPrivateWordUnitId() }));
            msgBuf.append(AbstractProcessor.endl);
        }
        msgBuf
                .append(
                        AbstractProcessor
                                .getI18NMessage("studyword.changeunit.prompt"))
                .append(AbstractProcessor.endl);
        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    protected int interactiveProcess_2300(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append("WordUnit: ");
        Long wordUnitId = null;
        try {
            final String cmdMsg = cmd.getOriginMessage();

            wordUnitId = new Long(cmdMsg);
            final WordUnit wordUnit = this.wordUnitManager
                    .getWordUnit(wordUnitId);
            if (wordUnit == null) {
                msgBuf
                        .append(AbstractProcessor
                                .getI18NMessage("studyword.changeunit.invalidUnitNumber"));
            } else {
                final UserUnitInfoKey key = new UserUnitInfoKey();
                final Long userId = cmd.getUserEntry().getUserId();
                key.setUserId(userId);
                key.setWordUnit(wordUnit);
                final UserUnitInfo userUnitInfo = this.userUnitInfoManager
                        .getUserUnitInfo(key);
                if (userUnitInfo == null) {
                    msgBuf
                            .append(AbstractProcessor
                                    .getI18NMessage("studyword.changeunit.invalidUnitNumber"));
                } else {
                    UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                            .getUserWordStudyingInfo(userId);
                    if (userWordStudyingInfo == null) {
                        userWordStudyingInfo = new UserWordStudyingInfo();
                        userWordStudyingInfo.setUserId(userId);
                    }
                    userWordStudyingInfo.setStudyingWordUnitId(wordUnitId);
                    // TODO need confirm whether this is ok
                    userWordStudyingInfo
                            .setStudyingType(UserWordStudyingInfo.ALL_WORDS_BY_UNIT);
                    userWordStudyingInfo.setLastStudied(new Date());
                    this.userStudyingWordInfoManager
                            .saveUserWordStudyingInfo(userWordStudyingInfo);

                    msgBuf.append(AbstractProcessor.getI18NMessage(
                            "studyword.changeunit.result", new Object[] {
                                    wordUnitId, wordUnit.getName() }));
                }
            }
        } catch (final NumberFormatException e) {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("studyword.changeunit.invalidUnitNumber"));
        }

        // Clear the word session
        this.setSession(null);

        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.STEP_TO_MENU;
    }

    /**
     * STEP_TO_MANAGENT_CHANGESTUDYTYPE = 2400
     */
    protected int interactiveProcessPrompt_2400(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage("studyword.separator"));
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(
                AbstractProcessor
                        .getI18NMessage("studyword.changemode.allInUnit"))
                .append(AbstractProcessor.endl);
        msgBuf.append(
                AbstractProcessor
                        .getI18NMessage("studyword.changemode.failedInUnit"))
                .append(AbstractProcessor.endl);
        msgBuf.append(
                AbstractProcessor
                        .getI18NMessage("studyword.changemode.allFailed"))
                .append(AbstractProcessor.endl);

        final Long userId = cmd.getUserEntry().getUserId();
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(userId);
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage(
                "studyword.changemode.status",
                new Object[] { userWordStudyingInfo.getStudyingType() }));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf
                .append(
                        AbstractProcessor
                                .getI18NMessage("studyword.changemode.prompt"))
                .append(AbstractProcessor.endl);

        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    protected int interactiveProcess_2400(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        final Long userId = cmd.getUserEntry().getUserId();
        final UserWordStudyingInfo userWordStudyingInfo = this.userStudyingWordInfoManager
                .getUserWordStudyingInfo(userId);
        // switch (userWordStudyingInfo.getStudyingType())
        int studyingType = -1;
        final String cmdMsg = cmd.getOriginMessage();
        try {
            studyingType = Integer.parseInt(cmdMsg);
        } catch (final Exception e) {
            studyingType = -1;
        }
        switch (studyingType) {
        case UserWordStudyingInfo.ALL_WORDS_BY_UNIT:
        case UserWordStudyingInfo.ALL_FAILED_WORDS:
        case UserWordStudyingInfo.FAILED_WORDS_BY_UNIT:
            break;
        default:
            msgBuf
                    .append(
                            AbstractProcessor
                                    .getI18NMessage("studyword.changemode.invalidChoice"))
                    .append(AbstractProcessor.endl);
            this.sendBackMessage(cmd, msgBuf.toString());
            return StudyWordProcessor.STEP_TO_MANAGENT_CHANGESTUDYTYPE;
        }
        userWordStudyingInfo.setStudyingType(studyingType);
        this.userStudyingWordInfoManager
                .saveUserWordStudyingInfo(userWordStudyingInfo);

        msgBuf.append(
                AbstractProcessor.getI18NMessage("studyword.changemode.OK"))
                .append(AbstractProcessor.endl);
        this.sendBackMessage(cmd, msgBuf.toString());

        // Clear the word session
        this.setSession(null);
        if (studyingType == UserWordStudyingInfo.ALL_FAILED_WORDS) {
            return StudyWordProcessor.STEP_TO_CONTINUE_STUDY;
        } else {
            return InteractiveProcessor.STEP_TO_MENU;
        }
    }

    private WordProcessingSession getWordProcessingSession(
            final ProcessableCommand cmd) {
        WordProcessingSession studyingSession = (WordProcessingSession) this
                .getSession();
        if ((studyingSession == null)
                || (studyingSession.getWordEntries().size() == 0)) {
            studyingSession = this.wordService.createWordProcessingSession(cmd
                    .getUserEntry().getUserId());
            this.setSession(studyingSession);
        }
        return studyingSession;
    }
}
