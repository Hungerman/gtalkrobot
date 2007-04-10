package com.gtrobot.dao.word;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.dao.BaseDao;
import com.gtrobot.exception.DataAccessException;
import com.gtrobot.model.JPWordEntry;

public class JPWordEntryDao extends BaseDao {
	protected static final transient Log log = LogFactory
			.getLog(JPWordEntryDao.class);

	public JPWordEntry getJPWordEntry(Long id) {
		String sql = "select jwe.JWE_ID as id, jwe.WORD as word, jwe.PRONOUNCE as pronounce, jwe.WORD_TYPE as wordType, jwe.MEANING as meaning, jwe.SENTENCE as sentence, jwe.COMMENTS as comments from JP_WORD_ENTRY jwe where jwe.JWE_ID = ?";

		JPWordEntry jpWordEntry = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setObject(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				jpWordEntry = new JPWordEntry();
				jpWordEntry.setId((Long) rs.getObject("id"));
				jpWordEntry.setWord(rs.getString("word"));
				jpWordEntry.setPronounce(rs.getString("pronounce"));
				jpWordEntry.setWordType(rs.getString("wordType"));
				jpWordEntry.setMeaning(rs.getString("meaning"));
				jpWordEntry.setSentence(rs.getString("sentence"));
				jpWordEntry.setComments(rs.getString("comments"));
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return jpWordEntry;
	}

	public JPWordEntry getJPWordEntry(String word) {
		String sql = "select jwe.JWE_ID as id, jwe.WORD as word, jwe.PRONOUNCE as pronounce, jwe.WORD_TYPE as wordType, jwe.MEANING as meaning, jwe.SENTENCE as sentence, jwe.COMMENTS as comments from JP_WORD_ENTRY jwe where jwe.WORD = ?";

		JPWordEntry jpWordEntry = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setString(1, word);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				jpWordEntry = new JPWordEntry();
				jpWordEntry.setId((Long) rs.getObject("id"));
				jpWordEntry.setWord(rs.getString("word"));
				jpWordEntry.setPronounce(rs.getString("pronounce"));
				jpWordEntry.setWordType(rs.getString("wordType"));
				jpWordEntry.setMeaning(rs.getString("meaning"));
				jpWordEntry.setSentence(rs.getString("sentence"));
				jpWordEntry.setComments(rs.getString("comments"));
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return jpWordEntry;
	}

	public List getJPWordEntrys(Long userId) {
		String sql = "select JWE_ID as id from USER_JP_WORD_ENTRY where UE_ID = ? and rownum < 10";

		List result = new ArrayList();
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setObject(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getObject("id"));
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return result;
	}

	public int addJPWordEntryToUserJPWord(Long userId) {

		long maxWordId = getMaxUserJPWordId(userId);
		if (maxWordId == -1) {
			return 0;
		}

		String sql = "insert into USER_JP_WORD_ENTRY (UE_ID, JWE_ID) select "
				+ userId + ", JWE_ID from JP_WORD_ENTRY where JWE_ID > "
				+ maxWordId;

		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			return pstmt.executeUpdate();
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return 0;
	}

	private long getMaxUserJPWordId(Long userId) {
		String sql = "select max(JWE_ID) as id from USER_JP_WORD_ENTRY where UE_ID = ?";

		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setObject(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getLong("id");
			}
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		return -1;
	}

	public int updateStudyResult(boolean pass, Long userId, Long wordId) {
		String sql = null;
		if (pass)
			sql = "update USER_JP_WORD_ENTRY set PASS_COUNT = (PASS_COUNT +1) where UE_ID = ? and JWE_ID = ?";
		else
			sql = "update USER_JP_WORD_ENTRY set FAIL_COUNT = (FAIL_COUNT +1) where UE_ID = ? and JWE_ID = ?";

		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setObject(1, userId);
			pstmt.setObject(2, wordId);
			return pstmt.executeUpdate();
		} catch (DataAccessException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		} catch (SQLException e) {
			log.error("WordEntryDao.getWordEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return -1;
	}
}
