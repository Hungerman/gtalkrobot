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
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

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
		// log.debug("GlobalContext.getUser: " + user);
		String resource = StringUtils.parseResource(jid);
		if (!(resource == null || resource.trim().length() == 0)) {
			// TODO should be deleted in release version
			log.error("System design error: resource is NOT null in getUser.");
		}

		final Roster roster = connection.getRoster();
		RosterEntry rosterEntry = roster.getEntry(jid);

		UserEntry userEntry = null;
		Element element = cacheContext.getUserCache().get(jid);
		if (element == null) {
			userEntry = new UserEntry(jid);
			cacheContext.getUserCache().put(new Element(jid, userEntry));
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

	public void updateUser(String user) {
		UserEntry userEntry = getUser(user);

		final Roster roster = connection.getRoster();
		Presence presence = roster.getPresence(user);
		if (presence == null || !userEntry.isChattable()) {
			activeUserList.remove(user);
		} else {
			activeUserList.add(user);
		}
	}

	public SortedSet getActiveUserList() {
		return activeUserList;
	}

	public Chat getChat(String user) {
		UserEntry userEntry = getUser(user);
		return getChat(userEntry);
	}

	public Chat getChat(UserEntry userEntry) {
		Element element = cacheContext.getChatCache().get(userEntry);
		if (element == null) {
			Chat chat = connection.createChat(userEntry.getUser());
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
