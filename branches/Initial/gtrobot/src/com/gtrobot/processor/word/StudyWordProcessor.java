package com.gtrobot.processor.word;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.model.word.UserFailedWordInfo;
import com.gtrobot.model.word.UserFailedWordInfoKey;
import com.gtrobot.model.word.UserUnitInfo;
import com.gtrobot.model.word.UserUnitInfoKey;
import com.gtrobot.model.word.UserWordStudyingInfo;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.service.word.UserFailedWordInfoManager;
import com.gtrobot.service.word.UserStudyingWordInfoManager;
import com.gtrobot.service.word.UserUnitInfoManager;
import com.gtrobot.service.word.WordEntryManager;
import com.gtrobot.service.word.WordUnitManager;
import com.gtrobot.utils.CommonUtils;

public class StudyWordProcessor extends InteractiveProcessor {
	private static final int STEP_TO_CONTINUE_STUDY = 1000;

	private static final int STEP_TO_CONTENTMANAGENT_ADDUNIT = 2100;

	private static final int STEP_TO_CONTENTMANAGENT_SHOWUNIT = 2200;

	private static final int STEP_TO_CONTENTMANAGENT_CHANGEUNIT = 2300;

	private static final int STEP_TO_REVIEW_31 = 3100;

	private static final int STEP_TO_REVIEW_32 = 3200;

	private UserUnitInfoManager userUnitInfoManager;

	private WordUnitManager wordUnitManager;

	private UserStudyingWordInfoManager userStudyingWordInfoManager;

	private WordEntryManager wordEntryManager;
	private UserFailedWordInfoManager userFailedWordInfoManager;

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

	protected void initCommandToStepMappings() {
		super.initCommandToStepMappings();

		addCommandToStepMapping("1", STEP_TO_CONTINUE_STUDY);
		addCommandToStepMapping("21", STEP_TO_CONTENTMANAGENT_ADDUNIT);
		addCommandToStepMapping("22", STEP_TO_CONTENTMANAGENT_SHOWUNIT);
		addCommandToStepMapping("23", STEP_TO_CONTENTMANAGENT_CHANGEUNIT);
		addCommandToStepMapping("31", STEP_TO_REVIEW_31);
		addCommandToStepMapping("32", STEP_TO_REVIEW_32);
	}

	protected List<String> prepareMenuInfo() {
		List<String> menuInfo = super.prepareMenuInfo();

		menuInfo.add("studyjpword.menu.conitinue");
		menuInfo.add("studyjpword.menu.conntentmanagement");
		menuInfo.add("studyjpword.menu.conntentmanagement.addunit");
		menuInfo.add("studyjpword.menu.conntentmanagement.showunit");
		menuInfo.add("studyjpword.menu.conntentmanagement.changeunit");
		menuInfo.add("studyjpword.menu.review");
		menuInfo.add("studyjpword.menu.review.31");
		menuInfo.add("studyjpword.menu.review.32");
		return menuInfo;
	}

	/**
	 * STEP_TO_CONTINUE_STUDY = 1000
	 */
	protected int interactiveProcess_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		WordProcessingSession studyingSession = getWordProcessingSession(cmd);
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
			msgBuf.append("):  ");
			msgBuf.append(wordEntry.getWord());
			sendBackMessage(cmd, msgBuf.toString());
			return CONTINUE;
		} else {
			msgBuf.append(cmd.getI18NMessage("studyjpword.menu.1.finished"));
			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			setSession(null);
			return STEP_TO_MENU;
		}
	}

	protected int interactiveProcess_1001(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		WordProcessingSession studyingSession = getWordProcessingSession(cmd);
		Long wordEntryId = studyingSession.getCurrentWord();
		WordEntry wordEntry = wordEntryManager.getWordEntry(wordEntryId);

		String cmdMsg = cmd.getOriginMessage().trim();
		if (wordEntry.getWord().equals(cmdMsg)) {
			showWord(msgBuf, wordEntry);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_CONTINUE_STUDY;
		} else if ("?".equals(cmdMsg)) {
			showWord(msgBuf, wordEntry);
			msgBuf.append(endl);
			msgBuf
					.append("==> c): Continue  p): Previous word  as): Append Sentence  an): Append notes");
			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return CONTINUE;
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
			
			UserFailedWordInfoKey key = new UserFailedWordInfoKey();
			key.setUserId(cmd.getUserEntry().getUserId());
			key.setWordUnit(wordUnitManager.getWordUnit(studyingSession.getWordUnitId()));
			key.setWordEntry(wordEntry);
			UserFailedWordInfo userFailedWordInfo = userFailedWordInfoManager.getUserFailedWordInfo(key);
			if(userFailedWordInfo == null)
			{
				userFailedWordInfo = new UserFailedWordInfo();
				userFailedWordInfo.setPk(key);
			}
			userFailedWordInfo.setFailedCounts(userFailedWordInfo.getFailedCounts() + 1);
			userFailedWordInfo.setLastStudied(new Date());
			
			sendBackMessage(cmd, msgBuf.toString());
			return REPEAT_THIS_STEP;
		}
	}

	protected int interactiveProcess_1002(ProcessableCommand cmd)
			throws XMPPException {
		String cmdMsg = cmd.getOriginMessage().trim();
		WordProcessingSession studyingSession = getWordProcessingSession(cmd);
		Long wordEntryId = studyingSession.getCurrentWord();

		if ("c".equalsIgnoreCase(cmdMsg)) {
			return STEP_TO_CONTINUE_STUDY;
		}
		if ("p".equalsIgnoreCase(cmdMsg)) {
			studyingSession.backOne();
			studyingSession.backOne();
			return STEP_TO_CONTINUE_STUDY;
		}
		String subCmd = cmdMsg.substring(0, 2);

		WordEntry wordEntry = wordEntryManager.getWordEntry(wordEntryId);
		StringBuffer msgBuf = new StringBuffer();
		String content = cmdMsg.substring(2);
		if (content != null && content.trim().length() > 0) {
			if ("as".equalsIgnoreCase(subCmd)) {
				if (wordEntry.getSentence() != null) {
					msgBuf.append(wordEntry.getSentence());
					msgBuf.append(endl);
				}

				msgBuf.append(content.trim());
				wordEntry.setSentence(msgBuf.toString());
				wordEntryManager.saveWordEntry(wordEntry);
			}
			if ("an".equalsIgnoreCase(subCmd)) {
				if (wordEntry.getComments() != null) {
					msgBuf.append(wordEntry.getComments());
					msgBuf.append(endl);
				}
				msgBuf.append(content.trim());
				wordEntry.setComments(msgBuf.toString());
				wordEntryManager.saveWordEntry(wordEntry);
			}
		}

		cmd.setOriginMessage("?");
		return this.getStep() - 1;
	}

	private void showWord(StringBuffer msgBuf, WordEntry wordEntry) {
		msgBuf.append(wordEntry.getWord());
		if (wordEntry.getPronounce() != null) {
			msgBuf.append(" /");
			msgBuf.append(wordEntry.getPronounce());
			msgBuf.append("/ ");
		}

		if (wordEntry.getWordType() != null) {
			msgBuf.append("  (");
			msgBuf.append(wordEntry.getWordType());
			msgBuf.append(")  ");
		}
		msgBuf.append(wordEntry.getMeaning());
		msgBuf.append(endl);

		msgBuf.append("用例>").append(endl);
		if (wordEntry.getSentence() != null) {
			msgBuf.append(wordEntry.getSentence());
		}
		msgBuf.append(endl);

		msgBuf.append("注釈>").append(endl);
		if (wordEntry.getComments() != null) {
			msgBuf.append(wordEntry.getComments());
		}
		msgBuf.append(endl);
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
	protected int interactiveProcess_2200(ProcessableCommand cmd)
			throws XMPPException {
		showUnitsInUserList(cmd);
		return STEP_TO_MENU;
	}

	@SuppressWarnings("unchecked")
	private void showUnitsInUserList(ProcessableCommand cmd)
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
			msgBuf.append(userUnitInfo.getFailedWordCounts()).append("  ");
			msgBuf.append(userUnitInfo.getLastStudied());

			msgBuf.append(endl);
		}

		sendBackMessage(cmd, msgBuf.toString());
	}

	/**
	 * STEP_TO_CONTENTMANAGENT_CHANGEUNIT = 2300 <br>
	 * Change the user current studying unit.
	 */
	protected int interactiveProcess_2300(ProcessableCommand cmd)
			throws XMPPException {
		showUnitsInUserList(cmd);
		return CONTINUE;
	}

	protected int interactiveProcess_2301(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("WordUnit: ");
		Long wordUnitId = null;
		try {
			wordUnitId = new Long(cmd.getOriginMessage().trim());
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
							.getUserWordInfo(userId);
					if (userWordStudyingInfo == null) {
						userWordStudyingInfo = new UserWordStudyingInfo();
						userWordStudyingInfo.setUserId(userId);
					}
					userWordStudyingInfo.setStudyingWordUnit(wordUnit);
					userWordStudyingInfo.setLastStudied(new Date());
					userStudyingWordInfoManager
							.saveUserWordInfo(userWordStudyingInfo);

					msgBuf.append("The unit "
							+ wordUnitId
							+ "("
							+ userWordStudyingInfo.getStudyingWordUnit()
									.getName()
							+ ") has been set for your study.");
				}
			}
		} catch (NumberFormatException e) {
			msgBuf.append("You entered one invalid unit nuimber.");
		}
		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_MENU;
	}

	/**
	 * userStudyingWordInfoManager STEP_TO_ADD_WORDS_TO_MY_LIST = 901000;
	 */
	// protected int interactiveProcess_901000(ProcessableCommand cmd)
	// throws XMPPException {
	// StringBuffer msgBuf = new StringBuffer();
	// // formartMessageHeader(cmd, msgBuf);
	//
	// msgBuf.append("Are you sure to add new words into your word list
	// (y/n)?");
	//
	// sendBackMessage(cmd, msgBuf.toString());
	// return CONTINUE;
	// }
	protected int interactiveProcess_901000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		// if (checkAnswer(cmd) == YES) {
		msgBuf.append(cmd.getI18NMessage("studyjpword.menu.901.prompt"));
		sendBackMessage(cmd, msgBuf.toString());
		msgBuf = new StringBuffer();
		// int count = DaoFactory.get WordEntryDao().add WordEntryToUser Word(
		// cmd.getUserEntry().getId());
		msgBuf.append(cmd.getI18NMessage("studyjpword.menu.901.finished"));
		// msgBuf.append(count);
		msgBuf.append(cmd
				.getI18NMessage("studyjpword.menu.901.finished.prompt"));

		createWordProcessingSession(cmd);

		sendBackMessage(cmd, msgBuf.toString());
		// }
		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_CONTENTMANAGENT
	 */
	protected int interactiveProcess_2000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		msgBuf.append("Not ready, please visit this action soon.");
		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_MENU;
	}

	protected int interactiveProcess_2001(ProcessableCommand cmd)
			throws XMPPException {

		return STEP_TO_MENU;
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
				.getUserWordInfo(userId);

		UserUnitInfoKey key = new UserUnitInfoKey();
		key.setUserId(userId);
		WordUnit studyingWordUnit = userWordStudyingInfo.getStudyingWordUnit();
		key.setWordUnit(studyingWordUnit);
		UserUnitInfo userUnitInfo = userUnitInfoManager.getUserUnitInfo(key);

		Iterator<WordUnitEntry> iterator = studyingWordUnit.getWordEntries()
				.iterator();
		List<Long> wordEntries = new ArrayList<Long>();
		while (iterator.hasNext()) {
			wordEntries.add(iterator.next().getPk().getWordEntryId());
		}
		WordProcessingSession ss = new WordProcessingSession(studyingWordUnit
				.getWordUnitId(), wordEntries);
		setSession(ss);
		return ss;
	}
}
