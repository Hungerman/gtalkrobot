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
			if (".g".equalsIgnoreCase(cmdMsg)) {
				int maxResults = 20;
				if (getUserEntryHolder().isAdmin()) {
					maxResults = 1000;
				}
				List ls = wordEntryManager.searchWordEntries(content,
						maxResults);
				Iterator it = ls.iterator();
				msgBuf.append(getI18NMessage("baseword.search.result",
						new Object[] { ls.size() }));
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
		msgBuf.append(getI18NMessage("baseword.search.prompt")).append(endl);
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
				msgBuf.append(getI18NMessage("baseword.showword.sentence"))
						.append(endl);
				msgBuf.append(wordEntry.getSentence());
				msgBuf.append(endl);
			}

			if (wordEntry.getComments() != null) {
				msgBuf.append(getI18NMessage("baseword.showword.comments"))
						.append(endl);
				msgBuf.append(wordEntry.getComments());
				msgBuf.append(endl);
			}
		}
	}

	protected void commonOnlineHelper_ChangeWord(StringBuffer msgBuf) {
		if (getUserEntryHolder().isAdmin()) {
			msgBuf.append(getI18NMessage("baseword.changeword.word")).append(
					endl);
			msgBuf.append(getI18NMessage("baseword.changeword.pronounce"))
					.append(endl);
			msgBuf.append(getI18NMessage("baseword.changeword.pronounceType"))
					.append(endl);
			msgBuf.append(getI18NMessage("baseword.changeword.wordType"))
					.append(endl);
			msgBuf.append(getI18NMessage("baseword.changeword.meaning"))
					.append(endl);
			msgBuf.append(getI18NMessage("baseword.changeword.sentence"))
					.append(endl);
			msgBuf.append(getI18NMessage("baseword.changeword.comments"))
					.append(endl);
		}
		msgBuf.append(getI18NMessage("baseword.changeword.appendSentence"))
				.append(endl);
		msgBuf.append(getI18NMessage("baseword.changeword.appendConments"))
				.append(endl);
		msgBuf.append(getI18NMessage("baseword.changeword.reportError"))
				.append(endl);
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
			if (getUserEntryHolder().isAdmin()) {
				if (".cw".equalsIgnoreCase(cmdMsg)) {
					WordEntry wordEntryTemp = wordEntryManager
							.getWordEntry(content);
					if (wordEntryTemp == null) {
						wordEntry.setWord(content);
						msgBuf
								.append(
										getI18NMessage("baseword.changeword.word.result"))
								.append(endl);
					} else {
						msgBuf
								.append(
										getI18NMessage("baseword.changeword.word.fail"))
								.append(endl);
					}
					flag = true;
				}
				if (".cp".equalsIgnoreCase(cmdMsg)) {
					wordEntry.setPronounce(content);
					flag = true;
					msgBuf
							.append(
									getI18NMessage("baseword.changeword.pronounce.result"))
							.append(endl);
				}
				if (".cpt".equalsIgnoreCase(cmdMsg)) {
					wordEntry.setPronounceType(content);
					flag = true;
					msgBuf
							.append(
									getI18NMessage("baseword.changeword.pronounceType.result"))
							.append(endl);
				}
				if (".cwt".equalsIgnoreCase(cmdMsg)) {
					wordEntry.setWordType(content);
					flag = true;
					msgBuf
							.append(
									getI18NMessage("baseword.changeword.wordType.result"))
							.append(endl);
				}
				if (".cm".equalsIgnoreCase(cmdMsg)) {
					wordEntry.setMeaning(content);
					flag = true;
					msgBuf
							.append(
									getI18NMessage("baseword.changeword.meaning.result"))
							.append(endl);
				}
				if (".cs".equalsIgnoreCase(cmdMsg)) {
					wordEntry.setSentence(content);
					flag = true;
					msgBuf
							.append(
									getI18NMessage("baseword.changeword.sentence.result"))
							.append(endl);
				}
				if (".cn".equalsIgnoreCase(cmdMsg)) {
					wordEntry.setComments(content);
					flag = true;
					msgBuf
							.append(
									getI18NMessage("baseword.changeword.comments.result"))
							.append(endl);
				}
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
				msgBuf
						.append(
								getI18NMessage("baseword.changeword.appendSentence.result"))
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
				msgBuf
						.append(
								getI18NMessage("baseword.changeword.appendConments.result"))
						.append(endl);
			}
			if (".rr".equalsIgnoreCase(cmdMsg)) {
				if (wordEntry.getComments() != null) {
					msgBuf.append(wordEntry.getComments());
					msgBuf.append(endl);
				}
				msgBuf.append(endl);
				msgBuf.append("Error reported by: \"");
				msgBuf.append(getUserEntryHolder().getNickName());
				msgBuf.append("\" <");
				msgBuf.append(getUserEntryHolder().getJid());
				msgBuf.append(">: ");
				msgBuf.append(endl);

				msgBuf.append(content.trim());
				wordEntry.setComments(msgBuf.toString());
				wordEntry.setHasError(true);

				msgBuf = new StringBuffer();
				flag = true;
				msgBuf
						.append(
								getI18NMessage("baseword.changeword.reportError.result"))
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
