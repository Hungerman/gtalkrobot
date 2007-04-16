package com.gtrobot.processor.word;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.service.word.UserStudyingWordInfoManager;
import com.gtrobot.service.word.UserUnitInfoManager;
import com.gtrobot.service.word.WordEntryManager;
import com.gtrobot.service.word.WordUnitManager;

public class WordManagementProcessor extends InteractiveProcessor {
	private static final int STEP_TO_WORD_MANAGEMENT = 1000;

	private static final int STEP_TO_SUB_LOOP_WORDS = 1010;

	private static final int STEP_TO_SUB_CHANGE_WORD = 1020;

	private UserUnitInfoManager userUnitInfoManager;

	private WordUnitManager wordUnitManager;

	private UserStudyingWordInfoManager userStudyingWordInfoManager;

	private WordEntryManager wordEntryManager;

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

	/**
	 * Menu
	 */
	protected int interactiveProcess_10(BaseCommand cmd) throws XMPPException {
		return STEP_TO_WORD_MANAGEMENT;
	}

	/**
	 * STEP_TO_WORD_MANAGEMENT = 1000
	 */
	@SuppressWarnings("unchecked")
	protected int interactiveProcess_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		msgBuf.append("Please choose one to modify...");
		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	@SuppressWarnings("unchecked")
	protected int interactiveProcess_1001(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		Long wordUnitId = null;

		String cmdMsg = cmd.getOriginMessage().trim();
		if ("?".equals(cmdMsg)) {// List all wordUnits
			List results = wordUnitManager.getWordUnits(null);

			msgBuf.append("Totaly, there are : " + results.size() + " units.");
			msgBuf.append(endl);

			for (Iterator<WordUnit> it = results.iterator(); it.hasNext();) {
				WordUnit wordUnit = it.next();
				msgBuf.append(wordUnit.getWordUnitId()).append("  ");
				msgBuf.append(wordUnit.getName()).append("  ");
				msgBuf.append(wordUnit.getWordCount()).append("  ");
				msgBuf.append(wordUnit.getLevel()).append("  ");

				msgBuf.append(endl);
			}

			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_WORD_MANAGEMENT;
		}
		try {
			wordUnitId = new Long(cmdMsg);
			// check word unit exist
			WordUnit wordUnit = wordUnitManager.getWordUnit(wordUnitId);
			if (wordUnit == null) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			msgBuf.append("You entered one invalid word unit number.");
			return STEP_TO_WORD_MANAGEMENT;
		}
		createWordProcessingSession(wordUnitId);

		// Next Step
		return STEP_TO_SUB_LOOP_WORDS;
	}

	/**
	 * STEP_TO_SUB_LOOP_WORDS = 1010
	 */
	protected int interactiveProcess_1010(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		WordProcessingSession processingSession = getWordProcessingSession(cmd);
		if (processingSession.next()) {
			msgBuf.append(endl);
			msgBuf.append("~~~~~~~~~~~~~~~~~~~~");
			msgBuf.append(endl);

			Long wordEntryId = processingSession.getCurrentWord();
			WordEntry wordEntry = wordEntryManager.getWordEntry(wordEntryId);

			showWord(msgBuf, wordEntry);
			msgBuf.append(endl);
			msgBuf
					.append(
							"==> s): Skip    c): Change    u): Up to change unit")
					.append(endl);
			msgBuf.append(endl);
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
		msgBuf.append(wordEntry.getSentence());
		msgBuf.append(endl);

		msgBuf.append("注釈>").append(endl);
		msgBuf.append(wordEntry.getComments());
		msgBuf.append(endl);
	}

	protected int interactiveProcess_1011(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		String cmdMsg = cmd.getOriginMessage().trim();
		if ("s".equalsIgnoreCase(cmdMsg)) {
			return STEP_TO_SUB_LOOP_WORDS;
		}
		if ("c".equalsIgnoreCase(cmdMsg)) {
			return STEP_TO_SUB_CHANGE_WORD;
		}
		if ("u".equalsIgnoreCase(cmdMsg)) {
			return STEP_TO_WORD_MANAGEMENT;
		}

		msgBuf.append("==> s): Skip    c): Change    u): Up to change unit")
				.append(endl);
		msgBuf.append(endl);
		sendBackMessage(cmd, msgBuf.toString());
		return REPEAT_THIS_STEP;
	}

	/**
	 * STEP_TO_SUB_CHANGE_WORD = 1020
	 */

	@SuppressWarnings("unchecked")
	protected int interactiveProcess_1020(ProcessableCommand cmd)
			throws XMPPException {
		Long wordEntryId = getWordProcessingSession(cmd).getCurrentWord();
		WordEntry wordEntry = wordEntryManager.getWordEntry(wordEntryId);
		setTempSession(wordEntry);

		cmd.setOriginMessage(null);
		return this.getStep() + 1;
	}

	protected int interactiveProcess_1021(ProcessableCommand cmd)
			throws XMPPException {
		return commonChangeProcess(cmd, "The word is: ", "word");
	}

	protected int interactiveProcess_1022(ProcessableCommand cmd)
			throws XMPPException {
		return commonChangeProcess(cmd, "The word's pronounce is: ",
				"pronounce");
	}

	protected int interactiveProcess_1023(ProcessableCommand cmd)
			throws XMPPException {
		return commonChangeProcess(cmd, "The word's pronounceType is: ",
				"pronounceType");
	}

	protected int interactiveProcess_1024(ProcessableCommand cmd)
			throws XMPPException {
		return commonChangeProcess(cmd, "The word's wordType is: ", "wordType");
	}

	protected int interactiveProcess_1025(ProcessableCommand cmd)
			throws XMPPException {
		return commonChangeProcess(cmd, "The word's meaning is: ", "meaning");
	}

	protected int interactiveProcess_1026(ProcessableCommand cmd)
			throws XMPPException {
		return commonChangeProcess(cmd, "The word's sentence is: ", "sentence");
	}

	protected int interactiveProcess_1027(ProcessableCommand cmd)
			throws XMPPException {
		return commonChangeProcess(cmd, "The word's comments is: ", "comments");
	}

	protected int interactiveProcess_1028(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		WordEntry wordEntry = (WordEntry) getTempSession();

		showWord(msgBuf, wordEntry);
		msgBuf.append(endl);
		msgBuf
				.append("==> y): Confirm & Save    c): Change again   n): Drop your modification");
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_1029(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		String cmdMsg = cmd.getOriginMessage().trim();
		if ("y".equalsIgnoreCase(cmdMsg)) {
			WordEntry wordEntry = (WordEntry) getTempSession();
			wordEntryManager.saveWordEntry(wordEntry);
			setTempSession(null);
			return STEP_TO_SUB_LOOP_WORDS;
		}
		if ("c".equalsIgnoreCase(cmdMsg)) {
			cmd.setOriginMessage(null);
			return STEP_TO_SUB_CHANGE_WORD + 1;
		}
		if ("n".equalsIgnoreCase(cmdMsg)) {
			return STEP_TO_SUB_LOOP_WORDS;
		}

		msgBuf
				.append("==> y: Confirm & Save    c: Change again   n: Drop your modification");
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return REPEAT_THIS_STEP;
	}

	protected int commonChangeProcess(ProcessableCommand cmd, String prompt,
			String propertyName) throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		WordEntry wordEntry = (WordEntry) getTempSession();

		String cmdMsg = cmd.getOriginMessage();
		if (cmdMsg == null) {
			msgBuf.append(prompt);
			Object value = null;
			try {
				value = PropertyUtils.getProperty(wordEntry, propertyName);
			} catch (Exception e) {
			}
			msgBuf.append(value);
			msgBuf.append(endl);
			msgBuf.append("==> s): Skip    内容): Change").append(endl);
			msgBuf.append(endl);

			sendBackMessage(cmd, msgBuf.toString());
			return REPEAT_THIS_STEP;
		} else {
			cmdMsg = cmdMsg.trim();
			if (!("s".equalsIgnoreCase(cmdMsg))) {
				try {
					PropertyUtils.setProperty(wordEntry, propertyName, cmdMsg);
				} catch (Exception e) {
				}
			}

			cmd.setOriginMessage(null);
			return this.getStep() + 1;
		}
	}

	private WordProcessingSession getWordProcessingSession(
			ProcessableCommand cmd) {
		WordProcessingSession processingSession = (WordProcessingSession) getSession();
		return processingSession;
	}

	@SuppressWarnings("unchecked")
	private WordProcessingSession createWordProcessingSession(Long wordUnitId) {
		WordUnit wordUnit = wordUnitManager.getWordUnit(wordUnitId);

		Iterator<WordUnitEntry> iterator = wordUnit.getWordEntries().iterator();
		List<Long> wordEntries = new ArrayList<Long>();
		while (iterator.hasNext()) {
			wordEntries.add(iterator.next().getPk().getWordEntryId());
		}
		WordProcessingSession ss = new WordProcessingSession(wordUnitId,
				wordEntries);
		setSession(ss);
		return ss;
	}
}
