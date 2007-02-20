package com.gtrobot.dao.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.context.UserEntry;
import com.gtrobot.dao.BaseDao;
import com.gtrobot.exception.DataAccessException;

public class UserEntryDao extends BaseDao {
	protected static final transient Log log = LogFactory
			.getLog(UserEntryDao.class);

	public UserEntry getUserEntry(String jid) {
		String sql = "select ID, JID, NICK_NAME, CHATTABLE, ECHOABLE, LOCALE, CREATE_DATE, LAST_LOGIN_DATE from USER_ENTRY where USER_ENTRY.JID = ?";
		UserEntry userEntry = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setString(1, jid);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				userEntry = new UserEntry(jid);
				userEntry.setId((Long)rs.getObject("ID"));
				userEntry.setNickName(rs.getString("NICK_NAME"));
				userEntry.setChattable(rs.getBoolean("CHATTABLE"));
				userEntry.setEchoable(rs.getBoolean("ECHOABLE"));
				userEntry.setLocale(new Locale(rs.getString("LOCALE")));
			}
			if (log.isDebugEnabled()) {
				log.debug("Loaded UserEntry successfully for: " + jid);
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.getUserEntry error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.getUserEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
		return userEntry;
	}

	public void insertUserEntry(UserEntry userEntry) {
		String sql = "insert into USER_ENTRY(JID, NICK_NAME, CHATTABLE, ECHOABLE, LOCALE, CREATE_DATE, LAST_LOGIN_DATE, ID) values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			userEntry.setId(getNextSequence("SEQ_USER_ENTRY"));
			pstmt = prepareStatement(sql);
			pstmt.setString(1, userEntry.getJid());
			pstmt.setString(2, userEntry.getNickName());
			pstmt.setBoolean(3, userEntry.isChattable());
			pstmt.setBoolean(4, userEntry.isEchoable());
			pstmt.setString(5, userEntry.getLocale().getLanguage());
			pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			pstmt.setObject(8, userEntry.getId());
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
		closeConnection();
	}

	public void updateUserEntry(UserEntry userEntry) {
		String sql = "update USER_ENTRY set JID = ?, NICK_NAME = ?, CHATTABLE = ?, ECHOABLE = ?, LOCALE = ?, CREATE_DATE = ?, LAST_LOGIN_DATE = ? where USER_ENTRY.ID = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = prepareStatement(sql);
			pstmt.setString(1, userEntry.getJid());
			pstmt.setString(2, userEntry.getNickName());
			pstmt.setBoolean(3, userEntry.isChattable());
			pstmt.setBoolean(4, userEntry.isEchoable());
			pstmt.setString(5, userEntry.getLocale().getLanguage());
			pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			pstmt.setObject(8, userEntry.getId());
			int rows = pstmt.executeUpdate();
			if (log.isDebugEnabled()) {
				log.debug("Updated successfully on " + rows + " rows.");
			}
		} catch (DataAccessException e) {
			log.error("UserEntryDao.updateUserEntry error!", e);
		} catch (SQLException e) {
			log.error("UserEntryDao.updateUserEntry error!", e);
		}
		closeStatement(pstmt);
		closeConnection();
	}
}
