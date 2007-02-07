package com.gtrobot.context;

import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

public class GlobalContext {

	private static final GlobalContext instance = new GlobalContext();

	private CacheManager manager;

	private CacheContext cacheContext;

	private XMPPConnection connection;

	private SortedSet userList;

	private GlobalContext() {
		initializeCache();
	}

	private void initializeCache() {
		cacheContext = CacheContext.getInstance();

		userList = new TreeSet();
	}

	protected void finalize() throws Throwable {
		manager.shutdown();
		super.finalize();
	}

	public static GlobalContext getInstance() {
		return instance;
	}

	public UserEntry getUser(String user) {
		final Roster roster = connection.getRoster();
		user = StringUtils.parseBareAddress(user);
		RosterEntry rosterEntry = roster.getEntry(user);

		UserEntry userEntry = null;
		Element element = cacheContext.getUserCache().get(user);
		if (element == null) {
			userEntry = new UserEntry(user);
			cacheContext.getUserCache().put(new Element(user, userEntry));
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
		user = StringUtils.parseBareAddress(user);
		UserEntry userEntry = getUser(user);

		final Roster roster = connection.getRoster();
		Presence presence = roster.getPresence(user);
		if (presence == null || !userEntry.isChattable()) {
			userList.remove(user);
		} else {
			userList.add(user);
		}
	}

	public SortedSet getUserList() {
		return userList;
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
