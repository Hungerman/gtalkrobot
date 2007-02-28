package com.gtrobot.processor.word;

import java.util.List;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.dao.DaoFactory;
import com.gtrobot.model.JPWordEntry;
import com.gtrobot.processor.InteractiveProcessor;

public class StudyJPWordProcessor extends InteractiveProcessor {
	private static final int STEP_TO_NORMAL_STUDY = 1000;

	private static final int STEP_TO_TEST = 2000;

	private static final int STEP_TO_ADD_WORDS_TO_MY_LIST = 901000;

	public StudyJPWordProcessor() {
		super("-jpWordList");
	}

	protected int interactiveProcess_100(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		
		msgBuf.append(seperator);
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("studyjpword.menu.welcome"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("studyjpword.menu.1"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("studyjpword.menu.2"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("studyjpword.menu.901"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("studyjpword.menu.back"));
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("studyjpword.menu.exit"));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_101(ProcessableCommand cmd)
			throws XMPPException {

		// Check whether the command is exit
		String opt = cmd.getOriginMessage().trim().toLowerCase();
		if (opt.equals("1")) {
			return STEP_TO_NORMAL_STUDY;
		}
		if (opt.equals("2")) {
			return STEP_TO_TEST;
		}
		if (opt.equals("901")) {
			return STEP_TO_ADD_WORDS_TO_MY_LIST;
		}
		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_NORMAL_STUDY
	 */
	protected int interactiveProcess_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		WordList wl = getWordList(cmd);
		int total = wl.getWordEntrys().size();
		if (total == 0) {
			// msgBuf.append("No words to study!");
			// msgBuf.append(endl);
			// msgBuf.append("Please choose 901 to add words to your word
			// list!");
			// sendBackMessage(cmd, msgBuf.toString());
			// return STEP_TO_MENU;
			interactiveProcess_901000(cmd);
			wl = getWordList(cmd);
		}

		JPWordEntry wordEntry = wl.getJPWordEntry();
		msgBuf.append("Word(");
		msgBuf.append(wl.getCurCount() + 1);
		msgBuf.append("/");
		msgBuf.append(total);
		msgBuf.append("):  ");
		msgBuf.append(wordEntry.getWord());
		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_1001(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		// formartMessageHeader(cmd, msgBuf);

		WordList wl = getWordList(cmd);
		JPWordEntry wordEntry = wl.getJPWordEntry();

		if (wordEntry.getWord().equals(cmd.getOriginMessage().trim())) {
			DaoFactory.getJPWordEntryDao().updateStudyResult(true,
					cmd.getUserEntry().getId(), wordEntry.getId());

			msgBuf.append("Word:  ");
			msgBuf.append(wordEntry.getWord());
			if (wordEntry.getPronounce() != null) {
				msgBuf.append("  [");
				msgBuf.append(wordEntry.getPronounce());
				msgBuf.append("]  ");
			}
			msgBuf.append(endl);
			if (wordEntry.getWordType() != null) {
				msgBuf.append("  (");
				msgBuf.append(wordEntry.getWordType());
				msgBuf.append(")  ");
			}
			msgBuf.append(wordEntry.getMeaning());
			msgBuf.append(endl);

			if (wordEntry.getSentence() != null) {
				msgBuf.append(wordEntry.getSentence());
				msgBuf.append(endl);
			}

			if (wordEntry.getComments() != null) {
				msgBuf.append("/* ");
				msgBuf.append(endl);
				msgBuf.append(wordEntry.getComments());
				msgBuf.append(endl);
				msgBuf.append(" */");
				msgBuf.append(endl);
			}

			if (wl.next()) {
				msgBuf.append("=======================");
				msgBuf.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				return STEP_TO_NORMAL_STUDY;
			} else {
				msgBuf
						.append(cmd
								.getI18NMessage("studyjpword.menu.1.finished"));
				msgBuf.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				setSession(null);
				return STEP_TO_MENU;
			}
		} else {
			DaoFactory.getJPWordEntryDao().updateStudyResult(false,
					cmd.getUserEntry().getId(), wordEntry.getId());

			msgBuf.append(cmd
					.getI18NMessage("studyjpword.menu.1.enterword.prompt"));
			msgBuf.append(endl);
			msgBuf.append(wordEntry.getWord());
			if (wordEntry.getPronounce() != null) {
				msgBuf.append("  [");
				msgBuf.append(wordEntry.getPronounce());
				msgBuf.append("]");
			}
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_NORMAL_STUDY;
		}
	}

	/**
	 * STEP_TO_ADD_WORDS_TO_MY_LIST = 901000;
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
		int count = DaoFactory.getJPWordEntryDao().addJPWordEntryToUserJPWord(
				cmd.getUserEntry().getId());
		msgBuf.append(cmd.getI18NMessage("studyjpword.menu.901.finished"));
		msgBuf.append(count);
		msgBuf.append(cmd
				.getI18NMessage("studyjpword.menu.901.finished.prompt"));
		
		createWordList(cmd);

		sendBackMessage(cmd, msgBuf.toString());
		// }
		return STEP_TO_MENU;
	}

	/**
	 * STEP_TO_TEST
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

	private WordList getWordList(ProcessableCommand cmd) {
		WordList wl = (WordList) getSession();
		if (wl == null || wl.getWordEntrys().size() == 0) {
			wl = createWordList(cmd);
		}
		return wl;
	}

	private WordList createWordList(ProcessableCommand cmd) {
		WordList wl;
		wl = new WordList(cmd.getUserEntry().getId());
		setSession(wl);
		return wl;
	}
}

class WordList {
	List wordEntrys;

	int curCount;

	public WordList(Long userId) {
		super();

		this.wordEntrys = DaoFactory.getJPWordEntryDao()
				.getJPWordEntrys(userId);
		reset();
	}

	public int getCurCount() {
		return curCount;
	}

	public List getWordEntrys() {
		return wordEntrys;
	}

	public JPWordEntry getJPWordEntry() {
		Long id = (Long) wordEntrys.get(curCount);
		return DaoFactory.getJPWordEntryDao().getJPWordEntry(id);
	}

	public void reset() {
		curCount = 0;
	}

	public boolean next() {
		curCount++;
		if (curCount == wordEntrys.size())
			return false;
		return true;
	}

	public void setCurCount(int curCount) {
		this.curCount = curCount;
	}

	public void setWordEntrys(List wordEntrys) {
		this.wordEntrys = wordEntrys;
	}
}
