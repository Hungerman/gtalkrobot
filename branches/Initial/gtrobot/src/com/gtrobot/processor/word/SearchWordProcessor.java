package com.gtrobot.processor.word;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.word.SearchWordCommand;
import com.gtrobot.processor.AbstractProcessor;

public class SearchWordProcessor extends AbstractProcessor {
	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		SearchWordCommand cmd = (SearchWordCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();
		// TODO
		// WordEntryDao wordEntryDao = DaoFactory.getWordEntryDao();
		// WordEntry wordEntry = wordEntryDao.getWordEntry(cmd.getCondition());
		// if (wordEntry == null) {
		// msgBuf.append("No word: " + cmd.getCondition() + " was found!");
		// } else {
		// List wms = wordEntryDao.getWordEntryMeanings(wordEntry.getId()
		// .longValue());
		// wordEntry.setMeanings(wms);
		//
		// msgBuf.append(wordEntry.getWord());
		// msgBuf.append(" [");
		// msgBuf.append(wordEntry.getPronounce());
		// msgBuf.append("]");
		//
		// for (int i = 0; i < wms.size(); i++) {
		// msgBuf.append(endl);
		// WordMeaning wm = (WordMeaning) wms.get(i);
		// msgBuf.append(i);
		// msgBuf.append(". ");
		// msgBuf.append(wm.getMeaning());
		// }
		// }
		sendBackMessage(cmd, msgBuf.toString());
	}
}
