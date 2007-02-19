package com.gtrobot.processor.word;

import java.util.Locale;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.word.AddWordEntryCommand;
import com.gtrobot.model.WordEntry;
import com.gtrobot.processor.InteractiveProcessor;

public class AddWordEntryProcessor extends InteractiveProcessor {

	public AddWordEntryProcessor() {
		super(4, "-wordEntry");
	}

	protected void interactiveProcess_0(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		formartMessageHeader(cmd, msgBuf);

		msgBuf.append("Please input the word entry:");
		// msgBuf.append(endl);
		//
		// // setSession(jid, new Date());
		// //
		sendBackMessage(cmd, msgBuf.toString());
	}

	protected void interactiveBeforeProcess_1(AddWordEntryCommand cmd)
			throws XMPPException {
		WordEntry wordEntry = new WordEntry();
		wordEntry.setWord(cmd.getOriginMessage().trim());
		setSession(cmd.getUserEntry().getJid(), wordEntry);
	}

	protected void interactiveProcess_1(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		formartMessageHeader(cmd, msgBuf);

		WordEntry wordEntry = new WordEntry();
		wordEntry.setWord(cmd.getOriginMessage());
		setSession(cmd.getUserEntry().getJid(), wordEntry);

		msgBuf
				.append("Good, please enter the word locale[en|zh|jp], default is[jp]:");
		sendBackMessage(cmd, msgBuf.toString());
	}

	protected void interactiveBeforeProcess_2(AddWordEntryCommand cmd)
			throws XMPPException {
		WordEntry wordEntry = (WordEntry) getSession(cmd.getUserEntry()
				.getJid());
		Locale locale = new Locale(cmd.getOriginMessage().trim());
		//TODO
		wordEntry.setLocale(locale);
	}

	protected void interactiveProcess_2(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		formartMessageHeader(cmd, msgBuf);

		WordEntry wordEntry = (WordEntry) getSession(cmd.getUserEntry()
				.getJid());

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}

		msgBuf.append(endl);
		msgBuf.append("wordEntry: ");
		msgBuf.append(wordEntry.getWord());
		msgBuf.append(endl);
		msgBuf.append("wordEntry.locale: ");
		msgBuf.append(wordEntry.getLocale().getDisplayLanguage(
				cmd.getUserEntry().getLocale()));
		msgBuf.append(endl);
		msgBuf.append("Do you want to save this word entry?");
		sendBackMessage(cmd, msgBuf.toString());
	}

	protected void interactiveProcess_3(AddWordEntryCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		formartMessageHeader(cmd, msgBuf);

		WordEntry wordEntry = (WordEntry) getSession(cmd.getUserEntry()
				.getJid());

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}

		msgBuf.append(endl);
		msgBuf.append("wordEntry: ");
		msgBuf.append(wordEntry.getWord());
		msgBuf.append(endl);
		msgBuf.append("wordEntry.locale: ");
		msgBuf.append(wordEntry.getLocale().getDisplayLanguage(
				cmd.getUserEntry().getLocale()));
		msgBuf.append(endl);
		msgBuf.append("This word entry has been saved!");
		sendBackMessage(cmd, msgBuf.toString());
	}

}
