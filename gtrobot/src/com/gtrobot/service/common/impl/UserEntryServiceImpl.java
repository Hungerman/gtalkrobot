package com.gtrobot.service.common.impl;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.engine.GoogleTalkConnection;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.service.common.UserEntryManager;
import com.gtrobot.service.common.UserEntryService;

/**
 * Manager all user information. Mantenant the activeUserList. When user status
 * change event coming, this context will be invoked to update the user's
 * status.
 * 
 * 
 * @author sunyuxin
 * 
 */
public class UserEntryServiceImpl implements UserEntryService {
	protected static final transient Log log = LogFactory
			.getLog(UserEntryServiceImpl.class);

	private SortedSet<String> activeUserList = new TreeSet<String>();

	private UserEntryManager userEntryManager;

	public void setUserEntryManager(UserEntryManager userEntryManager) {
		this.userEntryManager = userEntryManager;
	}

	public Collection<String> getAllActiveUsers() {
		return activeUserList;
	}

	public UserEntry getUserEntry(String jid) {
		jid = StringUtils.parseBareAddress(jid);
		UserEntry userEntry = userEntryManager.getUserEntry(jid);
		if (userEntry == null) {
			GoogleTalkConnection googleTalkConnection = GTRobotContextHelper
					.getGoogleTalkConnection();
			Roster roster = googleTalkConnection.getXMPPConnection()
					.getRoster();
			RosterEntry rosterEntry = roster.getEntry(jid);
			if (rosterEntry != null) {
				userEntry = new UserEntry();
				userEntry.setJid(jid);
				userEntry.setNickName(rosterEntry.getName());

				userEntryManager.saveUserEntry(userEntry);
			}
		}
		return userEntry;
	}

	public void updateUserEntryPresence(UserEntry userEntry, Presence presence) {
		if (userEntry.isChattable()
				&& Presence.Type.available.equals(presence.getType())
				&& ((presence.getMode() == null) || (Presence.Mode.available
						.equals(presence.getMode()) || Presence.Mode.chat
						.equals(presence.getMode())))) {
			activeUserList.add(userEntry.getJid());
		} else {
			activeUserList.remove(userEntry.getJid());
		}
	}

	public void saveUserEntry(UserEntry userEntry) {
		userEntryManager.saveUserEntry(userEntry);
	}

	// public UserEntry getUser(String jid) {
	// // if (log.isDebugEnabled())
	// // log.debug("GlobalContext.getUser: " + jid);
	// String resource = StringUtils.parseResource(jid);
	// if (!(resource == null || resource.trim().length() == 0)) {
	// // TODO should be deleted in release version
	// log.error("System design error: resource is NOT null in getUser.",
	// new Exception());
	// }
	//
	// final Roster roster = connection.getRoster();
	// RosterEntry rosterEntry = roster.getEntry(jid);
	// if (rosterEntry == null) {
	// return null;
	// }
	//
	// UserEntry userEntry = null;
	// Element element = cacheContext.getUserCache().get(jid);
	// if (element == null) {
	// userEntry = loadUser(jid);
	// } else {
	// userEntry = (UserEntry) element.getValue();
	// }
	// if (rosterEntry != null) {
	// String name = rosterEntry.getName();
	// if (name != null) {
	// userEntry.setNickName(name.trim());
	// }
	// }
	// return userEntry;
	// }

	// private UserEntry loadUser(String jid) {
	// UserEntryDao dao = DaoFactory.getUserEntryDao();
	// UserEntry userEntry = dao.getUserEntry(jid);
	// if (userEntry == null) {
	// userEntry = new UserEntry("" + jid);
	// dao.insertUserEntry(userEntry);
	// }
	//
	// cacheContext.getUserCache().put(new Element(jid, userEntry));
	// return userEntry;
	// }

	// public void updateUser(String jid) {
	// UserEntry userEntry = getUser(jid);
	//
	// saveUser(userEntry);
	//
	// updateUserStatus(userEntry);
	// }
	//
	// public void saveUser(UserEntry userEntry) {
	// UserEntryDao dao = DaoFactory.getUserEntryDao();
	// dao.updateUserEntry(userEntry);
	// }
	//
	// public void updateUserStatus(UserEntry userEntry) {
	// boolean userInActiveList = activeUserList.contains(userEntry.getJid());
	// if (userEntry.getStatus() == UserEntry.AVAILABLE
	// && userEntry.isChattableInPublicRoom() && !userInActiveList) {
	// activeUserList.add(userEntry.getJid());
	// if (log.isDebugEnabled()) {
	// log.debug(userEntry.getJid()
	// + " has been added into active user list.");
	// }
	//
	// } else {
	// activeUserList.remove(userEntry.getJid());
	// if (log.isDebugEnabled()) {
	// log.debug(userEntry.getJid()
	// + " has been removed into active user list.");
	// }
	// }
	// }
	//
	// public SortedSet getActiveUserList() {
	// return activeUserList;
	// }
	//
	// public Chat getChat(String jid) {
	// UserEntry userEntry = getUser(jid);
	// return getChat(userEntry);
	// }
	//
	// public Chat getChat(UserEntry userEntry) {
	// Element element = cacheContext.getChatCache().get(userEntry);
	// if (element == null) {
	// Chat chat = connection.getChatManager().createChat(userEntry.getJid(),
	// null);
	// cacheContext.getChatCache().put(new Element(userEntry, chat));
	// return chat;
	// } else {
	// return (Chat) element.getObjectValue();
	// }
	// }
	//
	// public XMPPConnection getConnection() {
	// return connection;
	// }
	//
	// public void setConnection(XMPPConnection connection) {
	// this.connection = connection;
	// }

}
