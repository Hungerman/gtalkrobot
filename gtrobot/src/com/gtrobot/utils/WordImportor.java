package com.gtrobot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.gtrobot.engine.GTRobotContext;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.exception.DataAccessException;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.model.word.WordUnitEntryKey;
import com.gtrobot.service.word.WordEntryManager;
import com.gtrobot.service.word.WordUnitEntryManager;
import com.gtrobot.service.word.WordUnitManager;

public class WordImportor {
	private WordEntryManager wordEntryManager = null;

	private WordUnitManager wordUnitManager = null;

	private WordUnitEntryManager wordUnitEntryManager = null;

	private int wordLelvel = 0;

	public WordImportor() {
		GTRobotContext.getContext();

		wordEntryManager = (WordEntryManager) GTRobotContextHelper
				.getBean("wordEntryManager");
		wordUnitManager = (WordUnitManager) GTRobotContextHelper
				.getBean("wordUnitManager");
		wordUnitEntryManager = (WordUnitEntryManager) GTRobotContextHelper
				.getBean("wordUnitEntryManager");
	}

	public void importEdict2(String fileName) {

		long count = 0;
		String line = null;
		String unitName = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), "GBK"));

			unitName = reader.readLine();
			System.out.println("\n\n\n===========================");
			System.out.println("=====>: " + unitName);
			WordUnit wordUnit = getWordUnit(unitName);
			wordUnitManager.saveWordUnit(wordUnit);

			line = reader.readLine();
			line = reader.readLine();
			while (!line.equals("/End")) {
				System.out.println("----->: " + line);
				WordEntry wordEntry = processLine(line);

				wordEntryManager.saveWordEntry(wordEntry);
				WordUnitEntryKey key = new WordUnitEntryKey();
				key.setWordEntryId(wordEntry.getWordEntryId());
				key.setWordUnitId(wordUnit.getWordUnitId());
				WordUnitEntry wordUnitEntry = null;
				try {
					wordUnitEntry = wordUnitEntryManager.getWordUnitEntry(key);
				} catch (ObjectRetrievalFailureException e) {
				}
				if (wordUnitEntry == null) {
					wordUnitEntry = new WordUnitEntry();
					wordUnitEntry.setPk(key);
					wordUnitEntryManager.saveWordUnitEntry(wordUnitEntry);
				}

				line = reader.readLine();
				count++;
			}
			wordUnit.setWordCount(count);
			wordUnitManager.saveWordUnit(wordUnit);
		} catch (Exception e) {
			System.out
					.println("*****>" + unitName + "(" + count + "): " + line);
			e.printStackTrace();
		}
	}

	private WordUnit getWordUnit(String unitName) {
		WordUnit wordUnit = wordUnitManager.getWordUnit(unitName);
		if (wordUnit == null) {
			wordUnit = new WordUnit();
			wordUnit.setName(unitName);
			wordUnit.setLevel(wordLelvel);
		}
		return wordUnit;
	}

	private WordEntry processLine(String line) throws DataAccessException,
			SQLException, UnsupportedEncodingException {

		// できる//②/动2/会，能/0/
		String[] split = line.split("/");
		String pronounce = split[0];
		String word = split[1];
		if (word == null || word.trim().length() == 0) {
			word = pronounce;
		}
		String pronounceType = split[2];
		String wordType = split[3];
		String meaning = split[4];

		WordEntry wordEntry = wordEntryManager.getWordEntry(word);
		if (wordEntry == null) {
			wordEntry = new WordEntry();
			wordEntry.setWord(word);
			wordEntry.setPronounce(pronounce);
			wordEntry.setPronounceType(pronounceType);
			wordEntry.setWordType(wordType);
			wordEntry.setMeaning(meaning);
		} else {
			wordEntry.setWordType(mergeContents(wordType, wordEntry
					.getWordType()));
			wordEntry
					.setMeaning(mergeContents(meaning, wordEntry.getMeaning()));
		}
		return wordEntry;
	}

	private String mergeContents(String newContent, String oldContent) {
		if (oldContent == null) {
			return newContent;
		} else {
			if (newContent != null && newContent.length() != 0
					&& !oldContent.equalsIgnoreCase(newContent)) {
				return oldContent + "/" + newContent;
			} else
				return oldContent;
		}
	}

	public static final void main(String[] argv) {
		WordImportor importor = new WordImportor();

		importFile("metadata\\data\\标日初级分课单词上\\", importor, 4);
		importFile("metadata\\data\\标日初级分课单词下\\", importor, 3);
		importFile("metadata\\data\\标日中级分课单词\\", importor, 2);

		System.exit(0);
	}

	private static void importFile(String filePath, WordImportor importor,
			int wordLevel) {
		importor.wordLelvel = wordLevel;
		File dir = new File(filePath);
		String[] filenames = dir.list();
		for (int i = 0; i < filenames.length; i++) {
			importor.importEdict2(filePath + filenames[i]);
		}
	}

}
