package com.gtrobot.processor.word;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.XMPPException;

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

	private static final int STEP_TO_CONTENTMANAGENT_ADDUNIT = 2100;

	private static final int STEP_TO_CONTENTMANAGENT_SHOWUNIT = 2200;

	private static final int STEP_TO_CONTENTMANAGENT_CHANGEUNIT = 2300;

	private static final int STEP_TO_CONTENTMANAGENT_CHANGESTUDYTYPE = 2400;

	protected void initMenuComamndToStepMappings() {
		super.initMenuComamndToStepMappings();

		addCommandToStepMapping("1", STEP_TO_CONTINUE_STUDY);
		addCommandToStepMapping("21", STEP_TO_CONTENTMANAGENT_ADDUNIT);
		addCommandToStepMapping("22", STEP_TO_CONTENTMANAGENT_SHOWUNIT);
		addCommandToStepMapping("23", STEP_TO_CONTENTMANAGENT_CHANGEUNIT);
		addCommandToStepMapping("24", STEP_TO_CONTENTMANAGENT_CHANGESTUDYTYPE);
	}

	protected void prepareMenuInfo(List<String> menuInfo) {
		menuInfo.add("studyjpword.menu.conitinue");
		menuInfo.add("studyjpword.menu.conntentmanagement");
		menuInfo.add("studyjpword.menu.conntentmanagement.addunit");
		menuInfo.add("studyjpword.menu.conntentmanagement.showunit");
		menuInfo.add("studyjpword.menu.conntentmanagement.changeunit");
		menuInfo.add("studyjpword.menu.conntentmanagement.changestudytype");
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

			msgBuf
					.append("You have not set your profile correctly. Please go to step 23 firstly.");
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
		msgBuf.append("> .p Back to previous word.").append(endl);
		msgBuf.append("> .s Skip this word.").append(endl);

		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		if (userWordStudyingInfo.getStudyingType() != UserWordStudyingInfo.ALL_FAILED_WORDS
				&& userWordStudyingInfo.getStudyingWordUnitId().equals(
						userWordStudyingInfo.getPrivateWordUnitId())) {
			msgBuf.append("> .d Delete this word from my private list.")
					.append(endl);
		}

		msgBuf.append("> .f Mark this word as finished.").append(endl);
		msgBuf.append("> .m Add this word into my private unit.").append(endl);

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
				return STEP_TO_CONTINUE_STUDY;
			}
			if (".s".equalsIgnoreCase(cmdMsg)) {
				showWord(msgBuf, wordEntry);
				sendBackMessage(cmd, msgBuf.toString());
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
				msgBuf.append(
						"This word has been deleted from your private list.")
						.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				return STEP_TO_CONTINUE_STUDY;
			}
			if (".m".equalsIgnoreCase(cmdMsg)) {
				Long userId = cmd.getUserEntry().getUserId();
				wordService.addWordToUserPrivateUnit(userId, wordEntryId);

				showWord(msgBuf, wordEntry);
				msgBuf.append("This word has been added to your private list.")
						.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				return STEP_TO_CONTINUE_STUDY;
			}
			if (".f".equalsIgnoreCase(cmdMsg)) {
				Long userId = cmd.getUserEntry().getUserId();
				wordService.addWordToUserPrivateUnit(userId, wordEntryId);
				wordService.removeUserFailedWord(wordEntryId, userId);

				showWord(msgBuf, wordEntry);
				msgBuf
						.append(
								"This word has been moved from failed list to your private list.")
						.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
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
		msgBuf.append("~~~~~~~~~~~~~~~~~~~~");
		msgBuf.append(endl);

		Long wordEntryId = studyingSession.getCurrentWord();
		WordEntry wordEntry = wordEntryManager.getWordEntry(wordEntryId);
		msgBuf.append("Word(");
		msgBuf.append(studyingSession.getCurCount() + 1);
		msgBuf.append("/");
		msgBuf.append(studyingSession.getWordEntries().size());

		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		if (userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.ALL_FAILED_WORDS) {
			msgBuf.append("@Failed");
		} else {
			msgBuf.append("@Unit.").append(
					userWordStudyingInfo.getStudyingWordUnitId());
		}
		msgBuf.append("):  ");
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
			msgBuf.append(cmd
					.getI18NMessage("studyjpword.menu.1.enterword.prompt"));
			msgBuf.append(endl);
			msgBuf.append(wordEntry.getWord());
			if (wordEntry.getPronounce() != null) {
				msgBuf.append("  [");
				msgBuf.append(wordEntry.getPronounce());
				msgBuf.append("]");
			}

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
				msgBuf.append("Good, but you still have " + count
						+ " failed words");
				msgBuf.append(endl);
				sendBackMessage(cmd, msgBuf.toString());

				return STEP_TO_CONTINUE_STUDY_EXIT;
			}
		} else {
			msgBuf.append("Nice, you have reviewed all failed words.");
			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_CONTINUE_STUDY_EXIT;
		}
	}

	protected int interactiveProcessPrompt_1101(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		msgBuf
				.append(
						"Congratulation! Do you like to mark this unit as finished? (y/n)")
				.append(endl);
		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	protected int interactiveProcess_1101(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		if (YES == checkAnswer(cmd)) {
			msgBuf.append("OK. This unit has been marked as finished.");
			msgBuf.append(endl);
			Long userId = cmd.getUserEntry().getUserId();
			UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
					.getUserWordStudyingInfo(userId);
			UserUnitInfo userUnitInfo = userUnitInfoManager.getUserUnitInfo(
					userId, userWordStudyingInfo.getStudyingWordUnitId());
			userUnitInfo.setFinished(true);
			userUnitInfoManager.saveUserUnitInfo(userUnitInfo);

			msgBuf
					.append("Please goto step 23 to change a new unit for future study.");
		} else {
			msgBuf.append("..oh, good guy, go on.");
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

	/**
	 * STEP_TO_CONTENTMANAGENT_ADDUNIT = 2100 <br>
	 * Add the unit from system to user's list <br>
	 * This step is to show the system unit list which are not in the user's
	 * list.
	 */
	@SuppressWarnings("unchecked")
	protected int interactiveProcessPrompt_2100(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		List results = wordUnitManager.getWordUnitsNotInUserList(cmd
				.getUserEntry().getUserId());

		msgBuf.append("Totaly, there are : " + results.size()
				+ " units still not in your list.");
		msgBuf.append(endl);

		for (Iterator<WordUnit> it = results.iterator(); it.hasNext();) {
			WordUnit wordUnit = it.next();
			msgBuf.append(wordUnit.getWordUnitId()).append("  ");
			msgBuf.append(wordUnit.getName()).append("  ");
			msgBuf.append(wordUnit.getWordCount()).append("  ");
			msgBuf.append(wordUnit.getLevel()).append("  ");

			msgBuf.append(endl);
		}

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
		msgBuf.append("WordUnit: ");
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

		msgBuf.append(" have been added into your list.");
		msgBuf.append(endl);

		msgBuf.append("The folloing units ");
		for (Iterator<String> it = failedUnits.iterator(); it.hasNext();) {
			String unitId = it.next();
			msgBuf.append(unitId).append(",");
		}
		msgBuf.append(" are invalid, can't be added.");
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_CONTENTMANAGENT_SHOWUNIT = 2200 <br>
	 * Show the selected unit in the user's list
	 */
	@SuppressWarnings("unchecked")
	protected int interactiveProcess_2200(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		List<UserUnitInfo> results = userUnitInfoManager.getUserUnitInfos(cmd
				.getUserEntry().getUserId());

		msgBuf.append("Totaly, you have : " + results.size()
				+ " units in your list.");
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
	 * STEP_TO_CONTENTMANAGENT_CHANGEUNIT = 2300 <br>
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
			msgBuf
					.append("There is not any un-finished unit in your list. Please goto step 21 to add some one.");
			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_MENU;
		}

		msgBuf.append("Totaly, you have : " + results.size()
				+ " units in your list.");
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

		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		msgBuf.append(endl);
		msgBuf.append("Now, you are studing unit: ").append(
				userWordStudyingInfo.getStudyingWordUnitId()).append(
				". Your private list is unit: ").append(
				userWordStudyingInfo.getPrivateWordUnitId()).append(".");
		msgBuf.append(endl);
		msgBuf.append("Please input one unit number to study it.").append(endl);
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
				msgBuf.append("You entered one invalid unit nuimber.");
			} else {
				UserUnitInfoKey key = new UserUnitInfoKey();
				Long userId = cmd.getUserEntry().getUserId();
				key.setUserId(userId);
				key.setWordUnit(wordUnit);
				UserUnitInfo userUnitInfo = userUnitInfoManager
						.getUserUnitInfo(key);
				if (userUnitInfo == null) {
					msgBuf.append("You entered one invalid unit nuimber.");
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

					msgBuf.append("The unit " + wordUnitId + "("
							+ wordUnit.getName()
							+ ") has been set for your study.");
				}
			}
		} catch (NumberFormatException e) {
			msgBuf.append("You entered one invalid unit nuimber.");
		}

		// Clear the word session
		setSession(null);

		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_CONTENTMANAGENT_CHANGESTUDYTYPE = 2400
	 */
	protected int interactiveProcessPrompt_2400(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(endl);
		msgBuf.append("~~~~~~~~~~~~~~~~~~~~");
		msgBuf.append(endl);
		msgBuf.append("1): Study all words in selected unit.").append(endl);
		msgBuf.append("2): Study failed words in selected unit.").append(endl);
		msgBuf.append("3): Study all failed words.").append(endl);

		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);
		msgBuf.append(endl);
		msgBuf.append("Now, your selection is: ").append(
				userWordStudyingInfo.getStudyingType()).append(".");
		msgBuf.append(endl);

		msgBuf.append("Please input the number to change it.").append(endl);

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
			msgBuf.append("Invalid choose. Please try again.").append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_CONTENTMANAGENT_CHANGESTUDYTYPE;
		}
		userWordStudyingInfo.setStudyingType(studyingType);
		userStudyingWordInfoManager
				.saveUserWordStudyingInfo(userWordStudyingInfo);

		msgBuf.append("Change successed.").append(endl);
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
