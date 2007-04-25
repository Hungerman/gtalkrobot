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

	protected void initMenuComamndToStepMappings() {
		super.initMenuComamndToStepMappings();

		addCommandToStepMapping("1", STEP_TO_CONTINUE_STUDY);
		addCommandToStepMapping("21", STEP_TO_MANAGENT_ADDUNIT);
		addCommandToStepMapping("22", STEP_TO_MANAGENT_SHOWUNIT);
		addCommandToStepMapping("23", STEP_TO_MANAGENT_CHANGEUNIT);
		addCommandToStepMapping("24", STEP_TO_MANAGENT_CHANGESTUDYTYPE);
	}

	protected void prepareMenuInfo(List<String> menuInfo) {
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
	protected int interactiveProcessPrompt_10(BaseCommand cmd)
			throws XMPPException {
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(cmd.getUserEntry().getUserId());
		if (userWordStudyingInfo == null) {
			return STEP_TO_MANAGENT_CHANGEUNIT;
		}

		return super.interactiveProcessPrompt_10(cmd);
	}

	/**
	 * 继续当前的学习 STEP_TO_CONTINUE_STUDY = 1000
	 */
	protected int interactiveProcess_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		WordProcessingSession studyingSession = null;

		try {
			studyingSession = getWordProcessingSession(cmd);
		} catch (Exception e) {
			log.error("Error", e);

			msgBuf.append(getI18NMessage("studyword.conitinue.unitNotSet"));
			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_CONTINUE_STUDY_EXIT;
		}
		if (studyingSession.next()) {
			return CONTINUE;
		} else {
			studyingSession.reset();
			return STEP_TO_CONTINUE_STUDY_MARK_FINISHED;
		}
	}

	protected StringBuffer interactiveOnlineHelper_1001(ProcessableCommand cmd) {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(
				getI18NMessage("studyword.conitinue.onlineHelper.prievios"))
				.append(endl);
		msgBuf.append(getI18NMessage("studyword.conitinue.onlineHelper.skip"))
				.append(endl);

		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		if (userWordStudyingInfo.getStudyingType() != UserWordStudyingInfo.ALL_FAILED_WORDS
				&& userWordStudyingInfo.getStudyingWordUnitId().equals(
						userWordStudyingInfo.getPrivateWordUnitId())) {
			msgBuf.append(
					getI18NMessage("studyword.conitinue.onlineHelper.delete"))
					.append(endl);
		}

		msgBuf.append(getI18NMessage("studyword.conitinue.onlineHelper.add"))
				.append(endl);
		msgBuf
				.append(
						getI18NMessage("studyword.conitinue.onlineHelper.finish"))
				.append(endl);

		commonOnlineHelper_ChangeWord(msgBuf);

		return msgBuf;
	}

	protected int interactiveOnlineProcess_1001(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		List<String> cmds = cmd.getInteractiveCommands();
		if (cmds == null) {
			return CONTINUE;
		}
		String cmdMsg = cmds.get(0);

		WordProcessingSession studyingSession = getWordProcessingSession(cmd);
		Long wordEntryId = studyingSession.getCurrentWord();
		WordEntry wordEntry = wordEntryManager.getWordEntry(wordEntryId);

		if (cmds.size() == 1) {
			if (".p".equalsIgnoreCase(cmdMsg)) {
				studyingSession.backOne();
				studyingSession.backOne();
				cmd.setProcessed(true);
				return STEP_TO_CONTINUE_STUDY;
			}
			if (".s".equalsIgnoreCase(cmdMsg)) {
				showWord(msgBuf, wordEntry);
				sendBackMessage(cmd, msgBuf.toString());
				cmd.setProcessed(true);
				return STEP_TO_CONTINUE_STUDY;
			}
			if (".d".equalsIgnoreCase(cmdMsg)) {
				Long userId = cmd.getUserEntry().getUserId();
				UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
						.getUserWordStudyingInfo(userId);
				if (userWordStudyingInfo.getStudyingType() != UserWordStudyingInfo.ALL_FAILED_WORDS
						&& userWordStudyingInfo.getStudyingWordUnitId().equals(
								userWordStudyingInfo.getPrivateWordUnitId())) {
					wordService.deleteWordFromPrivateUnit(studyingSession,
							userWordStudyingInfo, wordEntry);
				}
				showWord(msgBuf, wordEntry);
				msgBuf
						.append(
								getI18NMessage("studyword.conitinue.onlineHelper.delete.result"))
						.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				cmd.setProcessed(true);
				return STEP_TO_CONTINUE_STUDY;
			}
			if (".a".equalsIgnoreCase(cmdMsg)) {
				Long userId = cmd.getUserEntry().getUserId();
				wordService.addWordToUserPrivateUnit(userId, wordEntryId);

				showWord(msgBuf, wordEntry);
				msgBuf
						.append(
								getI18NMessage("studyword.conitinue.onlineHelper.add.result"))
						.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				cmd.setProcessed(true);
				return STEP_TO_CONTINUE_STUDY;
			}
			if (".f".equalsIgnoreCase(cmdMsg)) {
				Long userId = cmd.getUserEntry().getUserId();
				wordService.addWordToUserPrivateUnit(userId, wordEntryId);
				wordService.removeUserFailedWord(wordEntryId, userId);

				showWord(msgBuf, wordEntry);
				msgBuf.append(
						getI18NMessage("studyword.conitinue.onlineHelper.add"))
						.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				cmd.setProcessed(true);
				return STEP_TO_CONTINUE_STUDY;
			}
		}

		return commonOnlineProcess_ChangeWord(cmd, cmds, wordEntry);
	}

	protected int interactiveProcessPrompt_1001(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		WordProcessingSession studyingSession = getWordProcessingSession(cmd);

		msgBuf.append(endl);
		msgBuf.append(getI18NMessage("studyword.separator"));
		msgBuf.append(endl);

		Long wordEntryId = studyingSession.getCurrentWord();
		WordEntry wordEntry = wordEntryManager.getWordEntry(wordEntryId);

		msgBuf.append(getI18NMessage("studyword.conitinue.word.prompt",
				new Object[] { (studyingSession.getCurCount() + 1),
						studyingSession.getWordEntries().size() }));

		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		if (userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.ALL_FAILED_WORDS) {
			msgBuf.append(getI18NMessage("studyword.conitinue.word.faildUnit"));
		} else {
			msgBuf
					.append(getI18NMessage("studyword.conitinue.word.unit",
							new Object[] { userWordStudyingInfo
									.getStudyingWordUnitId() }));
		}
		msgBuf.append(wordEntry.getWord());
		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	protected int interactiveProcess_1001(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		WordProcessingSession studyingSession = getWordProcessingSession(cmd);
		Long wordEntryId = studyingSession.getCurrentWord();
		WordEntry wordEntry = wordEntryManager.getWordEntry(wordEntryId);

		String cmdMsg = cmd.getOriginMessage();
		if (wordEntry.getWord().equals(cmdMsg)) {
			showWord(msgBuf, wordEntry);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_CONTINUE_STUDY;
		} else {
			msgBuf
					.append(getI18NMessage("studyword.conitinue.word.enterPrompt"));
			msgBuf.append(endl);
			showWord(msgBuf, wordEntry, false);

			wordService.updateUserFailedWordInfo(
					cmd.getUserEntry().getUserId(), studyingSession
							.getWordUnitId(), wordEntry);

			sendBackMessage(cmd, msgBuf.toString());
			return REPEAT_THIS_STEP;
		}
	}

	/**
	 * STEP_TO_CONTINUE_STUDY_MARK_FINISHED = 1100
	 */
	protected int interactiveProcess_1100(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		if (userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.ALL_WORDS_BY_UNIT
				|| userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.FAILED_WORDS_BY_UNIT) {

			long count = userFailedWordInfoManager.getFailedWordCount(userId,
					userWordStudyingInfo.getStudyingWordUnitId());
			if (count == 0
					&& !userWordStudyingInfo.getStudyingWordUnitId().equals(
							userWordStudyingInfo.getPrivateWordUnitId())) {
				return CONTINUE;
			} else {
				msgBuf.append(getI18NMessage("studyword.conitinue.failed",
						new Object[] { count }));
				msgBuf.append(endl);
				sendBackMessage(cmd, msgBuf.toString());

				return STEP_TO_CONTINUE_STUDY_EXIT;
			}
		} else {
			msgBuf.append(getI18NMessage("studyword.conitinue.finished"));
			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_CONTINUE_STUDY_EXIT;
		}
	}

	protected int interactiveProcessPrompt_1101(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		msgBuf.append(getI18NMessage("studyword.conitinue.finish.prompt"))
				.append(endl);
		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	protected int interactiveProcess_1101(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		if (YES == checkAnswer(cmd)) {
			msgBuf
					.append(getI18NMessage("studyword.conitinue.finish.prompt.YES"));
			msgBuf.append(endl);
			Long userId = cmd.getUserEntry().getUserId();
			UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
					.getUserWordStudyingInfo(userId);
			UserUnitInfo userUnitInfo = userUnitInfoManager.getUserUnitInfo(
					userId, userWordStudyingInfo.getStudyingWordUnitId());
			userUnitInfo.setFinished(true);
			userUnitInfoManager.saveUserUnitInfo(userUnitInfo);

			msgBuf
					.append(getI18NMessage("studyword.conitinue.finish.prompt.YES.next"));
		} else {
			msgBuf
					.append(getI18NMessage("studyword.conitinue.finish.prompt.NO"));
		}
		msgBuf.append(endl);
		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_CONTINUE_STUDY_EXIT;
	}

	/**
	 * STEP_TO_CONTINUE_STUDY_EXIT = 1999
	 */
	protected int interactiveProcess_1999(ProcessableCommand cmd)
			throws XMPPException {
		setSession(null);
		return STEP_TO_MENU;
	}

	@SuppressWarnings("unchecked")
	protected int interactiveProcessPrompt_2100(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		List results = wordUnitManager.getWordUnitsNotInUserList(cmd
				.getUserEntry().getUserId());

		msgBuf.append(endl);
		for (Iterator<WordUnit> it = results.iterator(); it.hasNext();) {
			WordUnit wordUnit = it.next();
			msgBuf.append(wordUnit.getWordUnitId()).append("  ");
			msgBuf.append(wordUnit.getName()).append("  ");
			msgBuf.append(wordUnit.getWordCount()).append("  ");
			msgBuf.append(wordUnit.getLevel()).append("  ");

			msgBuf.append(endl);
		}

		msgBuf.append(getI18NMessage("studyword.addunit.info",
				new Object[] { results.size() }));
		msgBuf.append(endl);
		msgBuf.append(getI18NMessage("studyword.addunit.prompt",
				new Object[] { results.size() }));
		msgBuf.append(endl);
		msgBuf.append(endl);
		msgBuf.append(endl);
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	/**
	 * Process the user's selection, add it to the user's list
	 */
	protected int interactiveProcess_2100(ProcessableCommand cmd)
			throws XMPPException {
		List<String> units = CommonUtils.parseCommand(cmd.getOriginMessage()
				.trim(), false);

		List<String> failedUnits = new ArrayList<String>();

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(getI18NMessage("studyword.addunit.result.prompt"));
		for (Iterator<String> it = units.iterator(); it.hasNext();) {
			String unitId = it.next();
			Long wordUnitId = null;

			try {
				wordUnitId = new Long(unitId);

				// check word unit exist
				WordUnit wordUnit = wordUnitManager.getWordUnit(wordUnitId);
				if (wordUnit == null) {
					failedUnits.add(unitId);
					continue;
				}

				UserUnitInfo userUnitInfo = new UserUnitInfo();
				userUnitInfo.getPk().setUserId(cmd.getUserEntry().getUserId());
				userUnitInfo.getPk().getWordUnit().setWordUnitId(wordUnitId);
				userUnitInfoManager.saveUserUnitInfo(userUnitInfo);

				msgBuf.append(unitId).append(",");
			} catch (NumberFormatException e) {
				continue;
			}
		}
		msgBuf.append(endl);

		// msgBuf.append("The folloing units ");
		// for (Iterator<String> it = failedUnits.iterator(); it.hasNext();) {
		// String unitId = it.next();
		// msgBuf.append(unitId).append(",");
		// }
		// msgBuf.append(" are invalid, can't be added.");
		// msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_MANAGENT_SHOWUNIT = 2200 <br>
	 * Show the selected unit in the user's list
	 */
	@SuppressWarnings("unchecked")
	protected int interactiveProcess_2200(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		List<UserUnitInfo> results = userUnitInfoManager.getUserUnitInfos(cmd
				.getUserEntry().getUserId());

		msgBuf.append(getI18NMessage("studyword.showunit.prompt",
				new Object[] { results.size() }));
		msgBuf.append(endl);

		for (Iterator<UserUnitInfo> it = results.iterator(); it.hasNext();) {
			UserUnitInfo userUnitInfo = it.next();
			msgBuf.append(userUnitInfo.getPk().getWordUnit().getWordUnitId())
					.append("  ");
			msgBuf.append(userUnitInfo.getPk().getWordUnit().getName()).append(
					" ");
			msgBuf.append(userUnitInfo.getPk().getWordUnit().getWordCount())
					.append(" ");
			msgBuf.append(userUnitInfo.isFinished()).append(" ");
			msgBuf.append(userUnitInfo.getLastStudied());

			msgBuf.append(endl);
		}

		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_MANAGENT_CHANGEUNIT = 2300 <br>
	 * Change the user current studying unit.
	 */
	@SuppressWarnings("unchecked")
	protected int interactiveProcessPrompt_2300(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		List<UserUnitInfo> results = userUnitInfoManager
				.getNotFinishedUserUnitInfos(cmd.getUserEntry().getUserId());

		if (results.size() == 0) {
			msgBuf.append(getI18NMessage("studyword.changeunit.noUnit"));
			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_MANAGENT_ADDUNIT;
		}

		msgBuf.append(getI18NMessage("studyword.changeunit.info",
				new Object[] { results.size() }));
		msgBuf.append(endl);

		for (Iterator<UserUnitInfo> it = results.iterator(); it.hasNext();) {
			UserUnitInfo userUnitInfo = it.next();
			msgBuf.append(userUnitInfo.getPk().getWordUnit().getWordUnitId())
					.append("  ");
			msgBuf.append(userUnitInfo.getPk().getWordUnit().getName()).append(
					" ");
			msgBuf.append(userUnitInfo.getPk().getWordUnit().getWordCount())
					.append(" ");
			msgBuf.append(userUnitInfo.getLastStudied());

			msgBuf.append(endl);
		}
		msgBuf.append(endl);

		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		if (userWordStudyingInfo != null) {
			msgBuf.append(getI18NMessage("studyword.changeunit.status",
					new Object[] {
							userWordStudyingInfo.getStudyingWordUnitId(),
							userWordStudyingInfo.getPrivateWordUnitId() }));
			msgBuf.append(endl);
		}
		msgBuf.append(getI18NMessage("studyword.changeunit.prompt")).append(
				endl);
		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	protected int interactiveProcess_2300(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("WordUnit: ");
		Long wordUnitId = null;
		try {
			String cmdMsg = cmd.getOriginMessage();

			wordUnitId = new Long(cmdMsg);
			WordUnit wordUnit = wordUnitManager.getWordUnit(wordUnitId);
			if (wordUnit == null) {
				msgBuf
						.append(getI18NMessage("studyword.changeunit.invalidUnitNumber"));
			} else {
				UserUnitInfoKey key = new UserUnitInfoKey();
				Long userId = cmd.getUserEntry().getUserId();
				key.setUserId(userId);
				key.setWordUnit(wordUnit);
				UserUnitInfo userUnitInfo = userUnitInfoManager
						.getUserUnitInfo(key);
				if (userUnitInfo == null) {
					msgBuf
							.append(getI18NMessage("studyword.changeunit.invalidUnitNumber"));
				} else {
					UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
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
					userStudyingWordInfoManager
							.saveUserWordStudyingInfo(userWordStudyingInfo);

					msgBuf.append(getI18NMessage("studyword.changeunit.result",
							new Object[] { wordUnitId, wordUnit.getName() }));
				}
			}
		} catch (NumberFormatException e) {
			msgBuf
					.append(getI18NMessage("studyword.changeunit.invalidUnitNumber"));
		}

		// Clear the word session
		setSession(null);

		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_MANAGENT_CHANGESTUDYTYPE = 2400
	 */
	protected int interactiveProcessPrompt_2400(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(endl);
		msgBuf.append(getI18NMessage("studyword.separator"));
		msgBuf.append(endl);
		msgBuf.append(getI18NMessage("studyword.changemode.allInUnit")).append(
				endl);
		msgBuf.append(getI18NMessage("studyword.changemode.failedInUnit"))
				.append(endl);
		msgBuf.append(getI18NMessage("studyword.changemode.allFailed")).append(
				endl);

		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		msgBuf.append(endl);
		msgBuf.append(getI18NMessage("studyword.changemode.status",
				new Object[] { userWordStudyingInfo.getStudyingType() }));
		msgBuf.append(endl);

		msgBuf.append(getI18NMessage("studyword.changemode.prompt")).append(
				endl);

		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	protected int interactiveProcess_2400(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		// switch (userWordStudyingInfo.getStudyingType())
		int studyingType = -1;
		String cmdMsg = cmd.getOriginMessage();
		try {
			studyingType = Integer.parseInt(cmdMsg);
		} catch (Exception e) {
			studyingType = -1;
		}
		switch (studyingType) {
		case UserWordStudyingInfo.ALL_WORDS_BY_UNIT:
		case UserWordStudyingInfo.ALL_FAILED_WORDS:
		case UserWordStudyingInfo.FAILED_WORDS_BY_UNIT:
			break;
		default:
			msgBuf.append(getI18NMessage("studyword.changemode.invalidChoice"))
					.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_MANAGENT_CHANGESTUDYTYPE;
		}
		userWordStudyingInfo.setStudyingType(studyingType);
		userStudyingWordInfoManager
				.saveUserWordStudyingInfo(userWordStudyingInfo);

		msgBuf.append(getI18NMessage("studyword.changemode.OK")).append(endl);
		sendBackMessage(cmd, msgBuf.toString());

		// Clear the word session
		setSession(null);
		if (studyingType == UserWordStudyingInfo.ALL_FAILED_WORDS) {
			return STEP_TO_CONTINUE_STUDY;
		} else {
			return STEP_TO_MENU;
		}
	}

	private WordProcessingSession getWordProcessingSession(
			ProcessableCommand cmd) {
		WordProcessingSession studyingSession = (WordProcessingSession) getSession();
		if (studyingSession == null
				|| studyingSession.getWordEntries().size() == 0) {
			studyingSession = wordService.createWordProcessingSession(cmd
					.getUserEntry().getUserId());
			setSession(studyingSession);
		}
		return studyingSession;
	}
}
