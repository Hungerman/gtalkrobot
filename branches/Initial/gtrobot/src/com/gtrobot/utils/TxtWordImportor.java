package com.gtrobot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;

/**
 * 实现数据的导入处理。
 * 
 * @author Joey
 * 
 */
public class TxtWordImportor extends WordImportor {

	public void importFile(String fileName) {
		long count = 0;
		String line = null;
		String unitName = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), "GBK"));

			unitName = reader.readLine();
			log.info("\n\n\n================================>: " + unitName);
			WordUnit wordUnit = createWordUnit(unitName);

			line = reader.readLine();
			line = reader.readLine();
			while (!line.equals("/End")) {
				log.info("----->: " + line);
				WordEntry wordEntry = processLine(line);

				saveWordEntry(wordUnit, wordEntry);

				line = reader.readLine();
				count++;
			}
			wordUnit.setWordCount(count);
			saveWordUnit(wordUnit);
			flush();
		} catch (Exception e) {
			log.info("*****>" + unitName + "(" + count + "): " + line);
			e.printStackTrace();
		}
	}

	private WordEntry processLine(String line) throws SQLException,
			UnsupportedEncodingException {

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

		WordEntry wordEntry = createWordEntry(word, pronounce, pronounceType,
				wordType, meaning, null, null);
		return wordEntry;
	}

	public static final void main(String[] argv) {
		TxtWordImportor importor = new TxtWordImportor();

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
			importor.importFile(filePath + filenames[i]);
		}
	}
}
