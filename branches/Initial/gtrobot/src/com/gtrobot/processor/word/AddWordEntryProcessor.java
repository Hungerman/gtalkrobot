package com.gtrobot.processor.word;

import java.util.Locale;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.word.AddWordEntryCommand;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.utils.CommonUtils;

public class AddWordEntryProcessor extends InteractiveProcessor {
	private static final int STEP_TO_MENU = 100;

	private static final int STEP_TO_ADD_LOCALIZATION = 1000;

	private static final int STEP_TO_ADD_MEANING = 2000;

	// The interactiveProcess_0 are skipped

	protected int interactiveProcessPrompt_1(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// //formartMessageHeader(cmd, msgBuf);

		msgBuf.append("Please input the word entry:");

		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_1(AddWordEntryCommand cmd)
			throws XMPPException {
		WordEntry wordEntry = new WordEntry();
		wordEntry.setWord(cmd.getOriginMessage().trim());
		// wordEntry.setLocale(cmd.getOperationLocale());
		setSession(wordEntry);
		return STEP_TO_MENU;
	}

	/**
	 * Main Menu
	 */
	protected int interactiveProcessPrompt_100(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		msgBuf.append("Operation menu:");
		msgBuf.append(endl);
		msgBuf.append(" 1. Input the word localization meaning.");
		msgBuf.append(endl);
		msgBuf.append(" 2. Input the word meaning.");
		msgBuf.append(endl);
		msgBuf.append(" s. Save and exit.");
		msgBuf.append(endl);
		msgBuf.append(" x. Exit.");
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_100(AddWordEntryCommand cmd)
			throws XMPPException {

		// Check whether the command is exit
		String opt = cmd.getOriginMessage().trim().toLowerCase();
		if (opt.equals("1")) {
			return STEP_TO_ADD_LOCALIZATION;
		}
		if (opt.equals("2")) {
			return STEP_TO_ADD_MEANING;
		}
		if (opt.equals("s")) {
			return saveWordEntry(cmd);
		}
		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_ADD_LOCALIZATION
	 */
	protected int interactiveProcessPrompt_1000(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		msgBuf.append("Please input the locale for WordEntry's localization:");
		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_1000(AddWordEntryCommand cmd)
			throws XMPPException {
		Locale locale = CommonUtils.parseLocale(cmd.getOriginMessage());
		if (locale == null) {
			StringBuffer msgBuf = new StringBuffer();
			// formartMessageHeader(cmd, msgBuf);

			msgBuf.append("Your input locale is invalid:");
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_ADD_LOCALIZATION;
		}

		WordEntry we = new WordEntry();
		// we.setLocale(locale);
		setTempSession(we);
		return CONTINUE;
	}

	protected int interactiveProcessPrompt_1001(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		msgBuf.append("Please input the content for WordEntry's localization:");
		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_1001(AddWordEntryCommand cmd)
			throws XMPPException {
		WordEntry we = (WordEntry) getTempSession();
		we.setWord(cmd.getOriginMessage().trim());

		// WordEntry wordEntry = (WordEntry) getSession();
		// List localizations = wordEntry.getLocalizations();
		// if (localizations == null) {
		// localizations = new ArrayList();
		// }
		// localizations.add(we);
		// wordEntry.setLocalizations(localizations);

		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_ADD_MEANING
	 */
	protected int interactiveProcessPrompt_2000(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		msgBuf
				.append("Please input the content for WordMeaning's localization:");
		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_2000(AddWordEntryCommand cmd)
			throws XMPPException {
		WordEntry wordEntry = (WordEntry) getSession();

		// WordMeaning wm = new WordMeaning();
		// // wm.setLocale(wordEntry.getLocale());
		// wm.setMeaning(cmd.getOriginMessage().trim());
		//
		// List meanings = wordEntry.getMeanings();
		// if (meanings == null) {
		// meanings = new ArrayList();
		// }
		// meanings.add(wm);
		// wordEntry.setMeanings(meanings);

		return STEP_TO_MENU;
	}

	protected int interactiveProcessPrompt_3(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		// WordEntry wordEntry = (WordEntry) getSession(cmd.getUserEntry()
		// .getJid());
		//
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// }
		//
		// msgBuf.append(endl);
		// msgBuf.append("wordEntry: ");
		// msgBuf.append(wordEntry.getWord());
		// msgBuf.append(endl);
		// msgBuf.append("wordEntry.locale: ");
		// msgBuf.append(wordEntry.getLocale().getDisplayLanguage(
		// cmd.getUserEntry().getLocale()));
		// msgBuf.append(endl);
		msgBuf.append("Do you want to save this word entry?");
		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_3(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		switch (checkAnswer(cmd)) {
		case YES:
			return saveWordEntry(cmd);
		case NO:
			msgBuf.append("This word entry has been dropped!");
			sendBackMessage(cmd, msgBuf.toString());
			return CONTINUE;
		default:
			// BAD_ANSWER:
			msgBuf.append("Bad answer!");
			sendBackMessage(cmd, msgBuf.toString());
			// return RETRY_CURRENT_STEP;
			return CONTINUE;
		}
	}

	private int saveWordEntry(AddWordEntryCommand cmd) throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);
		WordEntry wordEntry = (WordEntry) getSession();

		try {
			// TODO
			// DaoFactory.getWordEntryDao().saveWordEntry(wordEntry);
		} catch (Exception e) {
			log.error("Exception while saving word entry: ", e);
			// return RETRY_CURRENT_STEP;
			return CONTINUE;
		}
		msgBuf.append("This word entry has been saved!");
		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_EXIT_MAIN_MENU;
	}
}
