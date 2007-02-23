package com.gtrobot.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gtrobot.dao.BaseDao;
import com.gtrobot.exception.DataAccessException;

public class Edict2Importor extends BaseDao {
	private static final String SEQ_WORD_ENTRY = "SEQ_WORD_ENTRY";

	private static final String SEQ_WORD_MEANING = "SEQ_WORD_MEANING";

	private static final String SEQ_WORD_ENTRY_SYNONYM = "SEQ_WORD_ENTRY_SYNONYM";

	private static final String selectWord = "select WE_ID from WORD_ENTRY where WORD = ?";

	private static final String selectWordMeaning = "select WM_ID from WORD_MEANING where MEANING = ?";

	private static final String selectWordMeanings = "select WE_ID from WORD_ENTRY_MEANINGS where WE_ID = ? and WM_ID = ?";

	private static final String insertWord = "insert into WORD_ENTRY(WE_ID, WORD, PRONOUNCE, LOCALE) values(?, ?, ? ,? )";

	private static final String insertWordMeaning = "insert into WORD_MEANING(WM_ID, MEANING, LOCALE) values(?, ?, ? )";

	private static final String insertWordSynonym = "insert into WORD_ENTRY_SYNONYM(WES_ID, WE_ID) values(?, ?)";

	private static final String insertWordMeanings = "insert into WORD_ENTRY_MEANINGS(WE_ID, WM_ID) values(?, ?)";

	private PreparedStatement stSelectWord;

	private PreparedStatement stSelectWordMeaning;

	private PreparedStatement stSelectWordMeanings;

	private PreparedStatement stInsertWord;

	private PreparedStatement stInsertWordMeaning;

	private PreparedStatement stInsertWordSynonym;

	private PreparedStatement stInsertWordMeanings;

	public void importEdict2(String fileName) {
		long count = 0;
		String line = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), "EUC-JP"));

			stSelectWord = prepareStatement(selectWord);
			stSelectWordMeaning = prepareStatement(selectWordMeaning);
			stSelectWordMeanings = prepareStatement(selectWordMeanings);
			stInsertWord = prepareStatement(insertWord);
			stInsertWordSynonym = prepareStatement(insertWordSynonym);
			stInsertWordMeaning = prepareStatement(insertWordMeaning);
			stInsertWordMeanings = prepareStatement(insertWordMeanings);

			line = reader.readLine();
			line = reader.readLine();
			while (line != null) {
				if (count > 51000) {
					processLine(line);
				}
				line = reader.readLine();
				count++;
				if (count % 1000 == 0) {
					getConnection().commit();
					System.out.println("Count: " + count);
				}
			}
		} catch (Exception e) {
			System.out.println("Line(" + count + "): " + line);
			e.printStackTrace();
		}
	}

	private void processLine(String line) throws DataAccessException,
			SQLException, UnsupportedEncodingException {
		// System.out.println("Processing: " + line);

		// shape of "hachi", the kanji for eight/
		String pronounce = null;

		int posPronounce = line.indexOf(" [");
		int posMeaning = line.indexOf(" /");

		String wordsString = null;
		if (posPronounce == -1) {
			wordsString = line.substring(0, posMeaning);
		} else {
			wordsString = line.substring(0, posPronounce);
			pronounce = line.substring(posPronounce + 2, posMeaning - 1);
		}
		String wordMeaningString = line.substring(posMeaning + 2,
				line.length() - 1);
		String[] words = wordsString.split(";");
		String[] meanings = wordMeaningString.split("/");
		Long[] wordIds = new Long[words.length];
		Long[] meaningIds = new Long[meanings.length];

		// insert words information
		for (int i = 0; i < words.length; i++) {
			stSelectWord.setObject(1, words[i]);
			ResultSet rs = stSelectWord.executeQuery();
			if (rs.next()) {
				wordIds[i] = (Long) rs.getObject(1);
			} else {
				wordIds[i] = getNextSequence(SEQ_WORD_ENTRY);
				stInsertWord.setObject(1, wordIds[i]);
				stInsertWord.setObject(2, words[i]);
				stInsertWord.setObject(3, pronounce);
				stInsertWord.setObject(4, "ja");

				stInsertWord.executeUpdate();
			}
		}

		// insert word synonym information
		Long synonymId = getNextSequence(SEQ_WORD_ENTRY_SYNONYM);
		for (int i = 0; i < words.length; i++) {
			stInsertWordSynonym.setObject(1, synonymId);
			stInsertWordSynonym.setObject(2, wordIds[i]);

			stInsertWordSynonym.executeUpdate();
		}

		// insert word meaning information
		for (int i = 0; i < meanings.length; i++) {
			stSelectWordMeaning.setObject(1, meanings[i]);
			ResultSet rs = stSelectWordMeaning.executeQuery();
			if (rs.next()) {
				meaningIds[i] = (Long) rs.getObject(1);
			} else {
				meaningIds[i] = getNextSequence(SEQ_WORD_MEANING);
				stInsertWordMeaning.setObject(1, meaningIds[i]);
				stInsertWordMeaning.setObject(2, meanings[i]);
				stInsertWordMeaning.setObject(3, "en");

				stInsertWordMeaning.executeUpdate();
			}
		}

		// insert word meanings information
		for (int i = 0; i < words.length; i++) {
			for (int j = 0; j < meanings.length; j++) {
				stSelectWordMeanings.setObject(1, wordIds[i]);
				stSelectWordMeanings.setObject(2, meaningIds[j]);
				ResultSet rs = stSelectWordMeanings.executeQuery();
				if (!rs.next()) {
					stInsertWordMeanings.setObject(1, wordIds[i]);
					stInsertWordMeanings.setObject(2, meaningIds[j]);
					stInsertWordMeanings.executeUpdate();
				}
			}
		}
	}

	public static final void main(String[] argv) {
		String filename = "C:\\Working\\gtrobot\\metadata\\dict\\edict2";
		Edict2Importor importor = new Edict2Importor();

		importor.importEdict2(filename);
	}

}
