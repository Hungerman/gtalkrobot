package com.gtrobot.dao.word;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.dao.BaseDao;
import com.gtrobot.exception.DataAccessException;
import com.gtrobot.model.WordEntry;
import com.gtrobot.model.WordMeaning;
import com.gtrobot.model.WordSentence;

public class WordEntryDao extends BaseDao {
	protected static final transient Log log = LogFactory
			.getLog(WordEntryDao.class);

	public WordEntry getWordEntry(long id) {
		String sql = "select we.WE_ID id, we.WORD word, we.PRONOUNCE pronounce, we.LOCALE locale from WORD_ENTRY we where we.WE_ID = ?";

		WordEntry wordEntry = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				wordEntry = new WordEntry();
				wordEntry.setId((Long) rs.getObject("id"));
				wordEntry.setWord(rs.getString("word"));
				wordEntry.setPronounce(rs.getString("pronounce"));
				wordEntry.setLocale(new Locale(rs.getString("locale")));
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return wordEntry;
	}
	
	public WordEntry getWordEntry(String condition) {
		String sql = "select WE_ID, WORD, PRONOUNCE, LOCALE from WORD_ENTRY we where WORD = ?";

		WordEntry wordEntry = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setString(1, condition);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				wordEntry = new WordEntry();
				wordEntry.setId((Long) rs.getObject("WE_ID"));
				wordEntry.setWord(rs.getString("WORD"));
				wordEntry.setPronounce(rs.getString("PRONOUNCE"));
				wordEntry.setLocale(new Locale(rs.getString("LOCALE")));
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return wordEntry;
	}

	public WordMeaning getWordMeaning(long id) {
		String sql = "select wm.WM_ID id, wm.MEANING meaning, wm.LOCALE locale from WORD_MEANING wm where wm.WM_ID = ?";

		WordMeaning wordMeaning = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				wordMeaning = new WordMeaning();
				wordMeaning.setId((Long) rs.getObject("id"));
				wordMeaning.setMeaning(rs.getString("meaning"));
				wordMeaning.setLocale(new Locale(rs.getString("locale")));
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordMeaning error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordMeaning error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return wordMeaning;
	}

	public WordSentence getWordSentence(long id) {
		String sql = "select ws.WS_ID id, ws.SENTENCE sentence, ws.LOCALE locale from WORD_SENTENCE ws where ws.WS_ID = ?";

		WordSentence wordSentence = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				wordSentence = new WordSentence();
				wordSentence.setId((Long) rs.getObject("id"));
				wordSentence.setSentence(rs.getString("sentence"));
				wordSentence.setLocale(new Locale(rs.getString("locale")));
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordSentence error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordSentence error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return wordSentence;
	}

	public List getWordEntryLocalizations(long id) {
		String sql = "select we.WE_ID id, we.WORD word, we.LOCALE locale from WORD_ENTRY_LOCALIZATIONS wel, WORD_ENTRY we where wel.WE_ID = ? and wel.WE_ID_L = we.WE_ID";

		List results = new ArrayList();
		WordEntry wordEntry = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				wordEntry = new WordEntry();
				wordEntry.setId((Long) rs.getObject("id"));
				wordEntry.setWord(rs.getString("word"));
				wordEntry.setLocale(new Locale(rs.getString("locale")));
				results.add(wordEntry);
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return results;
	}

	public List getWordMeaningLocalizations(long id) {
		String sql = "select wm.WM_ID, wm.MEANING, wm.LOCALE from WORD_MEANING_LOCALIZATIONS wml, WORD_MEANING wm where wml.WM_ID = ? and wml.WM_ID_L = wm.WM_ID";

		List results = new ArrayList();
		WordMeaning wordMeaning = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				wordMeaning = new WordMeaning();
				wordMeaning.setId((Long) rs.getObject("WM_ID"));
				wordMeaning.setMeaning(rs.getString("MEANING"));
				wordMeaning.setLocale(new Locale(rs.getString("LOCALE")));
				results.add(wordMeaning);
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return results;
	}

	public List getWordSentenceLocalizations(long id) {
		String sql = "select ws.WS_ID id, ws.SENTENCE sentence, ws.LOCALE locale from WORD_SENTENCE_LOCALIZATIONS wsl, WORD_SENTENCE ws where wsl.WS_ID = ? and wsl.WS_ID_L = ws.WS_ID";

		List results = new ArrayList();
		WordSentence wordSentence = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				wordSentence = new WordSentence();
				wordSentence.setId((Long) rs.getObject("id"));
				wordSentence.setSentence(rs.getString("sentence"));
				wordSentence.setLocale(new Locale(rs.getString("locale")));
				results.add(wordSentence);
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return results;
	}

	public List getWordEntryMeanings(long weId) {
		String sql = "select wm.WM_ID, wm.MEANING, wm.LOCALE from WORD_ENTRY_MEANINGS wem, WORD_MEANING wm where wem.WE_ID = ? and wem.WM_ID = wm.WM_ID";

		List results = new ArrayList();
		WordMeaning wordMeaning = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setLong(1, weId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				wordMeaning = new WordMeaning();
				wordMeaning.setId((Long) rs.getObject("WM_ID"));
				wordMeaning.setMeaning(rs.getString("MEANING"));
				wordMeaning.setLocale(new Locale(rs.getString("LOCALE")));
				results.add(wordMeaning);
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return results;
	}

	public List getWordMeaningSentences(long id) {
		String sql = "select ws.WS_ID id, ws.SENTENCE sentence, ws.LOCALE locale from WORD_MEANING_SENTENCES wms, WORD_SENTENCE ws where wms.WM_ID = ? and wms.WS_ID = ws.WS_ID";

		List results = new ArrayList();
		WordSentence wordSentence = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				wordSentence = new WordSentence();
				wordSentence.setId((Long) rs.getObject("id"));
				wordSentence.setSentence(rs.getString("sentence"));
				wordSentence.setLocale(new Locale(rs.getString("locale")));
				results.add(wordSentence);
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return results;
	}

	public void saveWordEntry(WordEntry wordEntry) {
		boolean newWordEntry = false;
		if (wordEntry.getId() == null) {
			newWordEntry = true;
			insertWordEntry(wordEntry);
		} else {
			updateWordEntry(wordEntry);
		}

		List localizations = wordEntry.getLocalizations();
		if (localizations != null && localizations.size() != 0) {
			Iterator it = localizations.iterator();
			while (it.hasNext()) {
				WordEntry we = (WordEntry) it.next();

				boolean newWordEntryLocalization = false;
				if (we.getId() == null) {
					newWordEntryLocalization = true;
					insertWordEntry(we);
				} else {
					updateWordEntry(we);
				}
				if (newWordEntry || newWordEntryLocalization) {
					insertWordEntryLocalization(wordEntry, we);
				}
			}
		}

		List meanings = wordEntry.getMeanings();
		if (meanings != null && meanings.size() != 0) {
			Iterator it = meanings.iterator();
			while (it.hasNext()) {
				WordMeaning wm = (WordMeaning) it.next();

				boolean newWordEntryMeaning = false;
				if (wm.getId() == null) {
					newWordEntryMeaning = true;
				}
				saveWordMeaning(wm);
				if (newWordEntry || newWordEntryMeaning) {
					insertWordEntryMeaning(wordEntry, wm);
				}
			}
		}

		closeConnection();
	}

	public void insertWordEntry(WordEntry wordEntry) {
		String sql = "insert into WORD_ENTRY(WORD, LOCALE, WE_ID) values(?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			wordEntry.setId(getNextSequence("SEQ_WORD_ENTRY"));
			pstmt = prepareStatement(sql);
			pstmt.setString(1, wordEntry.getWord());
			pstmt.setString(2, wordEntry.getLocale().getLanguage());
			pstmt.setObject(3, wordEntry.getId());
			int rows = pstmt.executeUpdate();
			if (log.isDebugEnabled()) {
				log.debug("Inserted successfully on " + rows + " rows.");
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.insertWordEntry error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.insertWordEntry error!", e);
		}
		closeStatement(pstmt);
		// closeConnection();
	}

	private void updateWordEntry(WordEntry wordEntry) {
		if (!wordEntry.isModified())
			return;
		return;
	}

	private void insertWordEntryLocalization(WordEntry wordEntry, WordEntry we) {
		String sql = "insert into WORD_ENTRY_LOCALIZATIONS(WE_ID, WE_ID_L) values(?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setObject(1, wordEntry.getId());
			pstmt.setObject(2, we.getId());
			int rows = pstmt.executeUpdate();
			if (log.isDebugEnabled()) {
				log.debug("Inserted successfully on " + rows + " rows.");
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.insertWordEntryLocalization error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.insertWordEntryLocalization error!", e);
		}
		closeStatement(pstmt);
		// closeConnection();
	}

	private void insertWordEntryMeaning(WordEntry wordEntry, WordMeaning wm) {
		String sql = "insert into WORD_ENTRY_MEANINGS(WE_ID, WM_ID) values(?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setObject(1, wordEntry.getId());
			pstmt.setObject(2, wm.getId());
			int rows = pstmt.executeUpdate();
			if (log.isDebugEnabled()) {
				log.debug("Inserted successfully on " + rows + " rows.");
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.insertWordEntryLocalization error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.insertWordEntryLocalization error!", e);
		}
		closeStatement(pstmt);
		// closeConnection();
	}

	private void saveWordMeaning(WordMeaning wordMeaning) {
		boolean newWordMeaning = false;
		if (wordMeaning.getId() == null) {
			newWordMeaning = true;
			insertWordMeaning(wordMeaning);
		} else {
			updateWordMeaning(wordMeaning);
		}

		List localizations = wordMeaning.getLocalizations();
		if (localizations != null && localizations.size() != 0) {
			Iterator it = localizations.iterator();
			while (it.hasNext()) {
				WordMeaning wm = (WordMeaning) it.next();

				boolean newWordMeaningLocalization = false;
				if (wm.getId() == null) {
					newWordMeaningLocalization = true;
					insertWordMeaning(wm);
				} else {
					updateWordMeaning(wm);
				}
				if (newWordMeaning || newWordMeaningLocalization) {
					insertWordMeaningLocalization(wordMeaning, wm);
				}
			}
		}

		List sentence = wordMeaning.getSentences();
		if (sentence != null && sentence.size() != 0) {
			Iterator it = sentence.iterator();
			while (it.hasNext()) {
				WordSentence ws = (WordSentence) it.next();

				boolean newWordSentence = false;
				if (ws.getId() == null) {
					newWordSentence = true;
				}
				saveWordSentence(ws);
				if (newWordMeaning || newWordSentence) {
					insertWordMeaningSentence(wordMeaning, ws);
				}
			}
		}
	}

	public void insertWordMeaning(WordMeaning wordMeaning) {
		String sql = "insert into WORD_MEANING(MEANING, LOCALE, WM_ID) values(?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			wordMeaning.setId(getNextSequence("SEQ_WORD_MEANING"));
			pstmt = prepareStatement(sql);
			pstmt.setString(1, wordMeaning.getMeaning());
			pstmt.setString(2, wordMeaning.getLocale().getLanguage());
			pstmt.setObject(3, wordMeaning.getId());
			int rows = pstmt.executeUpdate();
			if (log.isDebugEnabled()) {
				log.debug("Inserted successfully on " + rows + " rows.");
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.insertUserEntry error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.insertUserEntry error!", e);
		}
		closeStatement(pstmt);
		// closeConnection();
	}

	private void updateWordMeaning(WordMeaning wordMeaning) {
		if (!wordMeaning.isModified())
			return;
		// TODO Auto-generated method stub
	}

	private void insertWordMeaningLocalization(WordMeaning wordMeaning,
			WordMeaning wm) {
		String sql = "insert into WORD_MEANING_LOCALIZATIONS(WM_ID, WM_ID_L) values(?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setObject(1, wordMeaning.getId());
			pstmt.setObject(2, wm.getId());
			int rows = pstmt.executeUpdate();
			if (log.isDebugEnabled()) {
				log.debug("Inserted successfully on " + rows + " rows.");
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.insertWordMeaningLocalization error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.insertWordMeaningLocalization error!", e);
		}
		closeStatement(pstmt);
		// closeConnection();
	}

	private void insertWordMeaningSentence(WordMeaning wordMeaning,
			WordSentence ws) {
		String sql = "insert into WORD_MEANING_SENTENCES(WM_ID, WS_ID) values(?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setObject(1, wordMeaning.getId());
			pstmt.setObject(2, ws.getId());
			int rows = pstmt.executeUpdate();
			if (log.isDebugEnabled()) {
				log.debug("Inserted successfully on " + rows + " rows.");
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.insertWordMeaningSentence error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.insertWordMeaningSentence error!", e);
		}
		closeStatement(pstmt);
		// closeConnection();
	}

	private void saveWordSentence(WordSentence wordSentence) {
		boolean newWordSentence = false;
		if (wordSentence.getId() == null) {
			newWordSentence = true;
			insertWordSentence(wordSentence);
		} else {
			updateWordSentence(wordSentence);
		}

		List localizations = wordSentence.getLocalizations();
		if (localizations != null && localizations.size() != 0) {
			Iterator it = localizations.iterator();
			while (it.hasNext()) {
				WordSentence ws = (WordSentence) it.next();

				boolean newWordSentenceLocalization = false;
				if (ws.getId() == null) {
					newWordSentenceLocalization = true;
					insertWordSentence(ws);
				} else {
					updateWordSentence(ws);
				}
				if (newWordSentence || newWordSentenceLocalization) {
					insertWordSentenceLocalization(wordSentence, ws);
				}
			}
		}
	}

	public void insertWordSentence(WordSentence wordSentence) {
		String sql = "insert into WORD_SENTENCE(SENTENCE, LOCALE, WS_ID) values(?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			wordSentence.setId(getNextSequence("SEQ_WORD_SENTENCE"));
			pstmt = prepareStatement(sql);
			pstmt.setString(1, wordSentence.getSentence());
			pstmt.setString(2, wordSentence.getLocale().getLanguage());
			pstmt.setObject(3, wordSentence.getId());
			int rows = pstmt.executeUpdate();
			if (log.isDebugEnabled()) {
				log.debug("Inserted successfully on " + rows + " rows.");
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.insertUserEntry error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.insertUserEntry error!", e);
		}
		closeStatement(pstmt);
		// closeConnection();
	}

	private void updateWordSentence(WordSentence wordSentence) {
		if (!wordSentence.isModified())
			return;

	}

	private void insertWordSentenceLocalization(WordSentence wordSentence,
			WordSentence ws) {
		String sql = "insert into WORD_SENTENCE_LOCALIZATIONS(WS_ID, WS_ID_L) values(?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setObject(1, wordSentence.getId());
			pstmt.setObject(2, ws.getId());
			int rows = pstmt.executeUpdate();
			if (log.isDebugEnabled()) {
				log.debug("Inserted successfully on " + rows + " rows.");
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.insertWordSentenceLocalization error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.insertWordSentenceLocalization error!", e);
		}
		closeStatement(pstmt);
		// closeConnection();
	}
}
