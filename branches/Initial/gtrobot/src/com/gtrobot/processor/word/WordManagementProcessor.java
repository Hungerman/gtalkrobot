package com.gtrobot.processor.word;

import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.utils.CommonUtils;

public class WordManagementProcessor extends BaseWordProcessor {
	private static final int STEP_TO_WORD_MANAGEMENT = 1000;

	private static final int STEP_TO_SUB_LOOP_WORDS = 1010;

	private static final int STEP_TO_SUB_CHANGE_WORD = 1020;

	/**
	 * Menu
	 */
	protected int interactiveProcessPrompt_10(BaseCommand cmd)
			throws XMPPException {
		return STEP_TO_WORD_MANAGEMENT;
	}

	/**
	 * 选择单元 STEP_TO_WORD_MANAGEMENT = 1000
	 */
	protected StringBuffer interactiveOnlineHelper_1000(ProcessableCommand cmd) {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("> .c <wordUnitName> Create a new word unit.").append(
				endl);
		msgBuf.append("> .l List all word units.").append(endl);

		return msgBuf;
	}

	@SuppressWarnings("unchecked")
	protected int interactiveOnlineProcess_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		List<String> cmds = cmd.getInteractiveCommands();
		if (cmds == null) {
			return CONTINUE;
		}
		String cmdMsg = cmds.get(0);

		if (cmds.size() == 1) {
			if (".l".equalsIgnoreCase(cmdMsg)) {
				List results = wordUnitManager.getWordUnits();

				msgBuf.append("Totaly, there are : " + results.size()
						+ " units.");
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
				return CONTINUE;
			}
		}
		if (cmds.size() == 2) {
			String content = cmds.get(1);
			if (".c".equalsIgnoreCase(cmdMsg)) {
				WordUnit wordUnit = new WordUnit();
				wordUnit.setName(content);
				wordUnitManager.saveWordUnit(wordUnit);

				msgBuf.append("New word unit has been created with id: "
						+ wordUnit.getWordUnitId());
				sendBackMessage(cmd, msgBuf.toString());
				return CONTINUE;
			}
		}
		return CONTINUE;
	}

	protected int interactiveProcessPrompt_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		msgBuf.append("Please choose one to modify...");
		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	@SuppressWarnings("unchecked")
	protected int interactiveProcess_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		Long wordUnitId = null;

		String cmdMsg = cmd.getOriginMessage();
		try {
			wordUnitId = new Long(cmdMsg);
			// check word unit exist
			WordUnit wordUnit = wordUnitManager.getWordUnit(wordUnitId);
			if (wordUnit == null) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			msgBuf.append("You entered one invalid word unit number.");
			return REPEAT_THIS_STEP;
		}
		setSession(wordUnitId);

		// Next Step
		return STEP_TO_SUB_LOOP_WORDS;
	}

	/**
	 * 选择单词 STEP_TO_SUB_LOOP_WORDS = 1010
	 */
	protected StringBuffer interactiveOnlineHelper_1010(ProcessableCommand cmd) {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("> .c <wordName> Create a new word.").append(endl);
		msgBuf.append("> .s <wordId> Add words into unit.").append(endl);

		msgBuf.append("> .d <wordId> Delete a word from unit.").append(endl);
		msgBuf.append("> .l List all words.").append(endl);

		return msgBuf;
	}

	@SuppressWarnings("unchecked")
	protected int interactiveOnlineProcess_1010(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		List<String> cmds = cmd.getInteractiveCommands();
		if (cmds == null) {
			return CONTINUE;
		}
		String cmdMsg = cmds.get(0);

		Long wordUnitId = (Long) getSession();

		if (cmds.size() == 1) {
			if (".l".equalsIgnoreCase(cmdMsg)) {
				WordUnit wordUnit = wordUnitManager.getWordUnit(wordUnitId);

				Iterator<WordUnitEntry> iterator = wordUnit.getWordEntries()
						.iterator();
				msgBuf.append("Totaly, there are : "
						+ wordUnit.getWordEntries().size() + " words in unit: "
						+ wordUnit.getWordUnitId() + "." + wordUnit.getName());
				msgBuf.append(endl);

				while (iterator.hasNext()) {
					WordEntry wordEntry = wordEntryManager
							.getWordEntry(iterator.next().getPk()
									.getWordEntryId());
					showWord(msgBuf, wordEntry, false);
				}
				msgBuf.append(endl);
				sendBackMessage(cmd, msgBuf.toString());
				return CONTINUE;
			}
		}
		if (cmds.size() == 2) {
			String content = cmds.get(1);
			if (".c".equalsIgnoreCase(cmdMsg)) {
				WordEntry wordEntry = new WordEntry();
				wordEntry.setWord(content);
				wordEntry.setPronounce(content);
				wordEntry.setMeaning(content);
				wordEntryManager.saveWordEntry(wordEntry);

				WordUnitEntry wordUnitEntry = new WordUnitEntry();
				wordUnitEntry.getPk()
						.setWordEntryId(wordEntry.getWordEntryId());
				wordUnitEntry.getPk().setWordUnitId(wordUnitId);
				wordUnitEntryManager.saveWordUnitEntry(wordUnitEntry);

				msgBuf.append("New word has been created with id: "
						+ wordEntry.getWordEntryId());
				sendBackMessage(cmd, msgBuf.toString());
				return CONTINUE;
			}
			if (".d".equalsIgnoreCase(cmdMsg)) {
				List<String> params = CommonUtils.parseCommand(content, false);
				Iterator<String> it = params.iterator();
				msgBuf.append("Word Entry: ");
				while (it.hasNext()) {
					try {
						Long weId = new Long(it.next());
						WordUnitEntry wordUnitEntry = wordUnitEntryManager
								.getWordUnitEntry(weId, wordUnitId);
						if (wordUnitEntry != null) {
							wordUnitEntryManager
									.removeWordUnitEntry(wordUnitEntry.getPk());
						}
						msgBuf.append(weId).append(",");
					} catch (Exception e) {
					}
				}
				msgBuf.append(" have been removed from unit: ").append(
						wordUnitId);
				sendBackMessage(cmd, msgBuf.toString());
				return CONTINUE;
			}
			if (".a".equalsIgnoreCase(cmdMsg)) {
				List<String> params = CommonUtils.parseCommand(content, false);
				Iterator<String> it = params.iterator();
				msgBuf.append("Word Entry: ");
				while (it.hasNext()) {
					try {
						Long weId = new Long(it.next());
						WordUnitEntry wordUnitEntry = wordUnitEntryManager
								.getWordUnitEntry(weId, wordUnitId);
						if (wordUnitEntry == null) {
							wordUnitEntry = new WordUnitEntry();
							wordUnitEntry.getPk().setWordEntryId(weId);
							wordUnitEntry.getPk().setWordUnitId(wordUnitId);
							wordUnitEntryManager
									.saveWordUnitEntry(wordUnitEntry);
						}
						msgBuf.append(weId).append(",");
					} catch (Exception e) {
					}
				}
				msgBuf.append(" have been added from unit: ")
						.append(wordUnitId);
				sendBackMessage(cmd, msgBuf.toString());
				return CONTINUE;
			}
		}
		return CONTINUE;
	}

	protected int interactiveProcessPrompt_1010(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("Please choose one word to modify...");
		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	protected int interactiveProcess_1010(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		String cmdMsg = cmd.getOriginMessage();
		Long wordEntryId = null;
		WordEntry wordEntry = null;
		try {
			wordEntryId = new Long(cmdMsg);
			// check word exist
			wordEntry = wordEntryManager.getWordEntry(wordEntryId);
			if (wordEntry == null) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			msgBuf.append("You entered one invalid word number.");
			return REPEAT_THIS_STEP;
		}
		setSession(wordEntry);
		return STEP_TO_SUB_CHANGE_WORD;
	}

	/**
	 * 修改一个单词 STEP_TO_SUB_CHANGE_WORD = 1020
	 */
	protected StringBuffer interactiveOnlineHelper_1020(ProcessableCommand cmd) {
		StringBuffer msgBuf = new StringBuffer();

		commonOnlineHelper_ChangeWord(msgBuf);

		return msgBuf;
	}

	@SuppressWarnings("unchecked")
	protected int interactiveOnlineProcess_1020(ProcessableCommand cmd)
			throws XMPPException {
		List<String> cmds = cmd.getInteractiveCommands();

		return commonOnlineProcess_ChangeWord(cmd, cmds,
				(WordEntry) getTempSession());
	}

	protected int interactiveProcessPrompt_1020(ProcessableCommand cmd) {
		StringBuffer msgBuf = new StringBuffer();
		WordEntry wordEntry = (WordEntry) getTempSession();
		showWord(msgBuf, wordEntry);

		msgBuf.append(endl);
		msgBuf.append("OK?");

		return CONTINUE;
	}

	@SuppressWarnings("unchecked")
	protected int interactiveProcess_1020(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		if (YES == checkAnswer(cmd)) {
			msgBuf.append("OK.");
			msgBuf.append(endl);
			sendBackMessage(cmd, msgBuf.toString());
			return STEP_TO_SUB_LOOP_WORDS;
		}
		return REPEAT_THIS_STEP;
	}
}
