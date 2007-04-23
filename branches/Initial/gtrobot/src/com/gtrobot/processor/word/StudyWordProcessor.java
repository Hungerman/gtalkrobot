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
import com.gtrobot.model.word.UserFailedWordInfo;
import com.gtrobot.model.word.UserFailedWordInfoKey;
import com.gtrobot.model.word.UserUnitInfo;
import com.gtrobot.model.word.UserUnitInfoKey;
import com.gtrobot.model.word.UserWordStudyingInfo;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.model.word.WordUnitEntryKey;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.service.word.UserFailedWordInfoManager;
import com.gtrobot.service.word.UserStudyingWordInfoManager;
import com.gtrobot.service.word.UserUnitInfoManager;
import com.gtrobot.service.word.WordEntryManager;
import com.gtrobot.service.word.WordUnitEntryManager;
import com.gtrobot.service.word.WordUnitManager;
import com.gtrobot.utils.CommonUtils;

public class StudyWordProcessor extends InteractiveProcessor {
	protected static final transient Log log = LogFactory
			.getLog(StudyWordProcessor.class);

	private static final int STEP_TO_CONTINUE_STUDY = 1000;

	private static final int STEP_TO_CONTINUE_STUDY_MARK_FINISHED = 1100;

	private static final int STEP_TO_CONTINUE_STUDY_EXIT = 1999;

	private static final int STEP_TO_CONTENTMANAGENT_ADDUNIT = 2100;

	private static final int STEP_TO_CONTENTMANAGENT_SHOWUNIT = 2200;

	private static final int STEP_TO_CONTENTMANAGENT_CHANGEUNIT = 2300;

	private static final int STEP_TO_CONTENTMANAGENT_CHANGESTUDYTYPE = 2400;

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

	protected int interactiveOnlineProcess(BaseCommand cmd)
			throws XMPPException {
		int result = super.interactiveOnlineProcess(cmd);
		if (result != CONTINUE) {
			return result;
		}

		StringBuffer msgBuf = new StringBuffer();
		List<String> cmds = cmd.getInteractiveCommands();
		if (cmds == null) {
			return CONTINUE;
		}
		String cmdMsg = cmds.get(0);
		if (cmds.size() == 2) {
			String content = cmds.get(1);
			if (".g".equalsIgnoreCase(cmdMsg)
					|| ".google".equalsIgnoreCase(cmdMsg)) {
				int maxResults = 20;
				if (".google".equalsIgnoreCase(cmdMsg)) {
					maxResults = 1000;
				}
				List ls = wordEntryManager.searchWordEntries(content,
						maxResults);
				Iterator it = ls.iterator();
				msgBuf.append("Search result: ").append(ls.size());
				msgBuf.append(endl);
				while (it.hasNext()) {
					showWord(msgBuf, (WordEntry) it.next());
				}
				sendBackMessage(cmd, msgBuf.toString());
				return REPEAT_THIS_STEP;
			}
		}
		return CONTINUE;
	}

	protected StringBuffer interactiveOnlineHelper(BaseCommand cmd) {
		StringBuffer msgBuf = super.interactiveOnlineHelper(cmd);
		msgBuf.append("> .g <keyword> Google word.").append(endl);
		return msgBuf;
	}

	/**
	 * STEP_TO_CONTINUE_STUDY = 1000
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
		int total = studyingSession.getWordEntries().size();
		if (studyingSession.next()) {
			msgBuf.append(endl);
			msgBuf.append("~~~~~~~~~~~~~~~~~~~~");
			msgBuf.append(endl);

			Long wordEntryId = studyingSession.getCurrentWord();
			WordEntry wordEntry = wordEntryManager.getWordEntry(wordEntryId);
			msgBuf.append("Word(");
			msgBuf.append(studyingSession.getCurCount() + 1);
			msgBuf.append("/");
			msgBuf.append(total);

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
		msgBuf.append("> .as <content> Append sentence as sample.")
				.append(endl);
		msgBuf.append("> .an <comments> Append note as conments.").append(endl);

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
					deleteWordFromPrivateUnit(studyingSession,
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
				addWordToUserPrivateUnit(userId, wordEntryId);

				showWord(msgBuf, wordEntry);
				msgBuf.append("This word has been added to your private list.")
						.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				return STEP_TO_CONTINUE_STUDY;
			}
			if (".f".equalsIgnoreCase(cmdMsg)) {
				Long userId = cmd.getUserEntry().getUserId();
				addWordToUserPrivateUnit(userId, wordEntryId);
				removeUserFailedWord(wordEntryId, userId);

				showWord(msgBuf, wordEntry);
				msgBuf
						.append(
								"This word has been moved from failed list to your private list.")
						.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				return STEP_TO_CONTINUE_STUDY;
			}
		}
		if (cmds.size() == 2) {
			// Process the command with parameter
			String content = cmds.get(1);
			if (".as".equalsIgnoreCase(cmdMsg)) {
				if (wordEntry.getSentence() != null) {
					msgBuf.append(wordEntry.getSentence());
					msgBuf.append(endl);
				}
				msgBuf.append(content.trim());
				wordEntry.setSentence(msgBuf.toString());
				wordEntryManager.saveWordEntry(wordEntry);

				msgBuf = new StringBuffer();
				showWord(msgBuf, wordEntry);
				msgBuf.append("Your entered sentence has been added.").append(
						endl);
				sendBackMessage(cmd, msgBuf.toString());
				return STEP_TO_CONTINUE_STUDY;
			}
			if (".an".equalsIgnoreCase(cmdMsg)) {
				if (wordEntry.getComments() != null) {
					msgBuf.append(wordEntry.getComments());
					msgBuf.append(endl);
				}
				msgBuf.append(content.trim());
				wordEntry.setComments(msgBuf.toString());
				wordEntryManager.saveWordEntry(wordEntry);

				msgBuf = new StringBuffer();
				showWord(msgBuf, wordEntry);
				msgBuf.append("Your entered comments has been added.").append(
						endl);
				sendBackMessage(cmd, msgBuf.toString());
				return STEP_TO_CONTINUE_STUDY;
			}
		}
		cmd.setOriginMessage(".");
		return this.getStep();
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

			updateUserFailedWordInfo(cmd, studyingSession, wordEntry);

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
				return this.getStep() + 1;
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

	protected int interactiveProcess_1101(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		msgBuf
				.append(
						"Congratulation! Do you like to mark this unit as finished? (y/n)")
				.append(endl);
		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_1102(ProcessableCommand cmd)
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

	private void deleteWordFromPrivateUnit(
			WordProcessingSession studyingSession,
			UserWordStudyingInfo userWordStudyingInfo, WordEntry wordEntry) {
		WordUnitEntry wordUnitEntry = wordUnitEntryManager.getWordUnitEntry(
				wordEntry.getWordEntryId(), userWordStudyingInfo
						.getPrivateWordUnitId());
		wordUnitEntryManager.removeWordUnitEntry(wordUnitEntry.getPk());

		studyingSession.remove(wordEntry.getWordEntryId());
	}

	private void removeUserFailedWord(Long wordEntryId, Long userId) {
		List ls = userFailedWordInfoManager.getUserFailedWordInfosByWord(
				userId, wordEntryId);
		for (Iterator it = ls.iterator(); it.hasNext();) {
			userFailedWordInfoManager
					.removeUserFailedWordInfo((UserFailedWordInfo) it.next());
		}
	}

	private void addWordToUserPrivateUnit(Long userId, Long wordEntryId)
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

	@SuppressWarnings("unused")
	private void updateUserUnitInfo(ProcessableCommand cmd,
			WordProcessingSession studyingSession) {
		Long userId = cmd.getUserEntry().getUserId();
		UserWordStudyingInfo userWordStudyingInfo = userStudyingWordInfoManager
				.getUserWordStudyingInfo(userId);

		if (userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.ALL_WORDS_BY_UNIT
				|| userWordStudyingInfo.getStudyingType() == UserWordStudyingInfo.FAILED_WORDS_BY_UNIT) {
			UserUnitInfo userUnitInfo = userUnitInfoManager.getUserUnitInfo(
					userId, studyingSession.getWordUnitId());
			userUnitInfo.setStudiedTimes(userUnitInfo.getStudiedTimes() + 1);
			userUnitInfo.setLastStudied(new Date());
			userUnitInfoManager.saveUserUnitInfo(userUnitInfo);
		}
	}

	private void updateUserFailedWordInfo(ProcessableCommand cmd,
			WordProcessingSession studyingSession, WordEntry wordEntry) {
		Long userId = cmd.getUserEntry().getUserId();

		UserFailedWordInfoKey key = new UserFailedWordInfoKey();
		key.setUserId(userId);
		key.setWordUnit(wordUnitManager.getWordUnit(studyingSession
				.getWordUnitId()));
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

	private void showWord(StringBuffer msgBuf, WordEntry wordEntry) {
		msgBuf.append(wordEntry.getWord());
		if (wordEntry.getPronounce() != null) {
			msgBuf.append("　/");
			msgBuf.append(wordEntry.getPronounce());
			msgBuf.append("/");
			msgBuf.append(wordEntry.getPronounceType());
			msgBuf.append("/　");
		}

		if (wordEntry.getWordType() != null) {
			msgBuf.append("　(");
			msgBuf.append(wordEntry.getWordType());
			msgBuf.append(")　");
		}
		msgBuf.append(wordEntry.getMeaning());
		msgBuf.append(endl);

		if (wordEntry.getSentence() != null) {
			msgBuf.append("<用例>").append(endl);
			msgBuf.append(wordEntry.getSentence());
			msgBuf.append(endl);
		}

		if (wordEntry.getComments() != null) {
			msgBuf.append("<注釈>").append(endl);
			msgBuf.append(wordEntry.getComments());
			msgBuf.append(endl);
		}
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
	protected int interactiveProcess_2100(ProcessableCommand cmd)
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
		return CONTINUE;
	}

	/**
	 * Process the user's selection, add it to the user's list
	 */
	protected int interactiveProcess_2101(ProcessableCommand cmd)
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
	protected int interactiveProcess_2300(ProcessableCommand cmd)
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
		return CONTINUE;
	}

	protected int interactiveProcess_2301(ProcessableCommand cmd)
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
	protected int interactiveProcess_2400(ProcessableCommand cmd)
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
		return CONTINUE;
	}

	protected int interactiveProcess_2401(ProcessableCommand cmd)
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
			studyingSession = createWordProcessingSession(cmd);
		}
		return studyingSession;
	}

	@SuppressWarnings("unchecked")
	private WordProcessingSession createWordProcessingSession(
			ProcessableCommand cmd) {
		Long userId = cmd.getUserEntry().getUserId();
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
		setSession(ss);
		return ss;
	}
}
