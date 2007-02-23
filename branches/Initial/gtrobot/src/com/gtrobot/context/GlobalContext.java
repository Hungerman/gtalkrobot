package com.gtrobot.context;

import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.dao.DaoFactory;
import com.gtrobot.dao.common.UserEntryDao;

/**
 * Manager all user information. Mantenant the activeUserList. When user status
 * change event coming, this context will be invoked to update the user's
 * status.
 * 
 * 
 * @author sunyuxin
 * 
 */
public class GlobalContext {
	protected static final transient Log log = LogFactory
			.getLog(GlobalContext.class);

	private static final GlobalContext instance = new GlobalContext();

	private CacheContext cacheContext;

	private XMPPConnection connection;

	private SortedSet activeUserList;

	private GlobalContext() {
		initializeCache();
	}

	private void initializeCache() {
		cacheContext = CacheContext.getInstance();

		activeUserList = new TreeSet();
	}

	public static GlobalContext getInstance() {
		return instance;
	}

	public UserEntry getUser(String jid) {
		// if (log.isDebugEnabled())
		// log.debug("GlobalContext.getUser: " + jid);
		String resource = StringUtils.parseResource(jid);
		if (!(resource == null || resource.trim().length() == 0)) {
			// TODO should be deleted in release version
			log.error("System design error: resource is NOT null in getUser.",
					new Exception());
		}

		final Roster roster = connection.getRoster();
		RosterEntry rosterEntry = roster.getEntry(jid);
		if (rosterEntry == null) {
			return null;
		}

		UserEntry userEntry = null;
		Element element = cacheContext.getUserCache().get(jid);
		if (element == null) {
			userEntry = loadUser(jid);
		} else {
			userEntry = (UserEntry) element.getValue();
		}
		if (rosterEntry != null) {
			String name = rosterEntry.getName();
			if (name != null) {
				userEntry.setNickName(name.trim());
			}
		}
		return userEntry;
	}

	private UserEntry loadUser(String jid) {
		UserEntryDao dao = DaoFactory.getUserEntryDao();
		UserEntry userEntry = dao.getUserEntry(jid);
		if (userEntry == null) {
			userEntry = new UserEntry("" + jid);
			dao.insertUserEntry(userEntry);
		}

		cacheContext.getUserCache().put(new Element(jid, userEntry));
		return userEntry;
	}

	public void updateUser(String jid) {
		UserEntry userEntry = getUser(jid);

		saveUser(userEntry);

		updateUserStatus(userEntry);
	}

	public void saveUser(UserEntry userEntry) {
		UserEntryDao dao = DaoFactory.getUserEntryDao();
		dao.updateUserEntry(userEntry);
	}

	public void updateUserStatus(UserEntry userEntry) {
		boolean userInActiveList = activeUserList.contains(userEntry.getJid());
		if (userEntry.getStatus() == UserEntry.AVAILABLE
				&& userEntry.isChattableInPublicRoom() && !userInActiveList) {
			activeUserList.add(userEntry.getJid());
			if (log.isDebugEnabled()) {
				log.debug(userEntry.getJid()
						+ " has been added into active user list.");
			}

		} else {
			activeUserList.remove(userEntry.getJid());
			if (log.isDebugEnabled()) {
				log.debug(userEntry.getJid()
						+ " has been removed into active user list.");
			}
		}
	}

	public SortedSet getActiveUserList() {
		return activeUserList;
	}

	public Chat getChat(String jid) {
		UserEntry userEntry = getUser(jid);
		return getChat(userEntry);
	}

	public Chat getChat(UserEntry userEntry) {
		Element element = cacheContext.getChatCache().get(userEntry);
		if (element == null) {
			Chat chat = connection.createChat(userEntry.getJid());
			cacheContext.getChatCache().put(new Element(userEntry, chat));
			return chat;
		} else {
			return (Chat) element.getObjectValue();
		}
	}

	public XMPPConnection getConnection() {
		return connection;
	}

	public void setConnection(XMPPConnection connection) {
		this.connection = connection;
	}
}
