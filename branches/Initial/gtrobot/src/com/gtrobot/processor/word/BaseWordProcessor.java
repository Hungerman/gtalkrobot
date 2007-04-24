package com.gtrobot.processor.word;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.service.word.UserFailedWordInfoManager;
import com.gtrobot.service.word.UserStudyingWordInfoManager;
import com.gtrobot.service.word.UserUnitInfoManager;
import com.gtrobot.service.word.WordEntryManager;
import com.gtrobot.service.word.WordService;
import com.gtrobot.service.word.WordUnitEntryManager;
import com.gtrobot.service.word.WordUnitManager;

public class BaseWordProcessor extends InteractiveProcessor {
	protected static final transient Log log = LogFactory
			.getLog(BaseWordProcessor.class);

	protected UserUnitInfoManager userUnitInfoManager;

	protected WordUnitManager wordUnitManager;

	protected UserStudyingWordInfoManager userStudyingWordInfoManager;

	protected WordEntryManager wordEntryManager;

	protected UserFailedWordInfoManager userFailedWordInfoManager;

	protected WordUnitEntryManager wordUnitEntryManager;

	protected WordService wordService;

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

	public void setWordService(WordService wordService) {
		this.wordService = wordService;
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

	protected void showWord(StringBuffer msgBuf, WordEntry wordEntry) {
		showWord(msgBuf, wordEntry, true);
	}

	protected void showWord(StringBuffer msgBuf, WordEntry wordEntry,
			boolean showAll) {
		if (!showAll) {
			msgBuf.append(wordEntry.getWordEntryId()).append(". ");
		}

		msgBuf.append(wordEntry.getWord());
		if (wordEntry.getPronounce() != null) {
			msgBuf.append("　/");
			msgBuf.append(wordEntry.getPronounce());

			if (wordEntry.getPronounceType() != null) {
				msgBuf.append("/");
				msgBuf.append(wordEntry.getPronounceType());
			}
			msgBuf.append("/　");
		}

		if (wordEntry.getWordType() != null) {
			msgBuf.append("　(");
			msgBuf.append(wordEntry.getWordType());
			msgBuf.append(")　");
		}
		msgBuf.append(wordEntry.getMeaning());
		msgBuf.append(endl);
		if (showAll) {
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
	}

	protected void commonOnlineHelper_ChangeWord(StringBuffer msgBuf) {
		msgBuf.append("> .cw <word> Change the word.").append(endl);
		msgBuf.append("> .cp <pronounce> Change the pronounce.").append(endl);
		msgBuf.append("> .cpt <pronounceType> Change the pronounce type.")
				.append(endl);
		msgBuf.append("> .cwt <wordType> Change the word type.").append(endl);
		msgBuf.append("> .cm <meaning> Change the meaning.").append(endl);

		msgBuf.append("> .as <content> Append sentence as sample.")
				.append(endl);
		msgBuf.append("> .an <comments> Append note as conments.").append(endl);
	}

	protected int commonOnlineProcess_ChangeWord(BaseCommand cmd,
			List<String> cmds, WordEntry wordEntry) throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		if (cmds == null) {
			return CONTINUE;
		}
		String cmdMsg = cmds.get(0);
		boolean flag = false;
		if (cmds.size() == 2) {
			// Process the command with parameter
			String content = cmds.get(1);
			if (".cw".equalsIgnoreCase(cmdMsg)) {
				WordEntry wordEntryTemp = wordEntryManager
						.getWordEntry(content);
				if (wordEntryTemp == null) {
					wordEntry.setWord(content);
					msgBuf.append("The word has been changed.").append(endl);
				} else {
					msgBuf.append("Error: The same word has been there.")
							.append(endl);
				}
				flag = true;
			}
			if (".cp".equalsIgnoreCase(cmdMsg)) {
				wordEntry.setPronounce(content);
				flag = true;
				msgBuf.append("The word's pronounce has been changed.").append(
						endl);
			}
			if (".cpt".equalsIgnoreCase(cmdMsg)) {
				wordEntry.setPronounceType(content);
				flag = true;
				msgBuf.append("The word's pronounce type has been changed.")
						.append(endl);
			}
			if (".cwt".equalsIgnoreCase(cmdMsg)) {
				wordEntry.setWordType(content);
				flag = true;
				msgBuf.append("The word's word type has been changed.").append(
						endl);
			}
			if (".cm".equalsIgnoreCase(cmdMsg)) {
				wordEntry.setMeaning(content);
				flag = true;
				msgBuf.append("The word's meaning has been changed.").append(
						endl);
			}

			if (".as".equalsIgnoreCase(cmdMsg)) {
				if (wordEntry.getSentence() != null) {
					msgBuf.append(wordEntry.getSentence());
					msgBuf.append(endl);
				}
				msgBuf.append(content.trim());
				wordEntry.setSentence(msgBuf.toString());

				msgBuf = new StringBuffer();
				flag = true;
				msgBuf.append("Your entered sentence has been changed.")
						.append(endl);
			}
			if (".an".equalsIgnoreCase(cmdMsg)) {
				if (wordEntry.getComments() != null) {
					msgBuf.append(wordEntry.getComments());
					msgBuf.append(endl);
				}
				msgBuf.append(content.trim());
				wordEntry.setComments(msgBuf.toString());

				msgBuf = new StringBuffer();
				flag = true;
				msgBuf.append("Your entered comments has been changed.")
						.append(endl);
			}
		}
		if (flag) {
			wordEntryManager.saveWordEntry(wordEntry);
			showWord(msgBuf, wordEntry);
			sendBackMessage(cmd, msgBuf.toString());
			cmd.setProcessed(true);
		}
		return CONTINUE;

	}
}
