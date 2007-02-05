package test.context;

import java.util.Iterator;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.util.StringUtils;

public class GlobalContext {

	private static final GlobalContext instance = new GlobalContext();

	private CacheManager manager;

	private Cache userCache;

	private Cache chatCache;

	private XMPPConnection connection;

	private GlobalContext() {
		initializeCache();
	}

	private void initializeCache() {
		CacheManager manager = new CacheManager();
		userCache = manager.getCache("userCache");
		chatCache = manager.getCache("chatCache");
	}

	protected void finalize() throws Throwable {
		manager.shutdown();
		super.finalize();
	}

	public static GlobalContext getInstance() {
		return instance;
	}

	public UserEntry getUser(String user) {
		user = StringUtils.parseBareAddress(user);

		Element element = userCache.get(user);
		if (element == null) {
			UserEntry userEntry = new UserEntry(user);
			userCache.put(new Element(user, userEntry));
			return userEntry;
		} else {
			return (UserEntry) element.getValue();
		}
	}
	
	public Iterator getUserList() {
		return userCache.getKeys().iterator();		
	}

	public Chat getChat(String user) {
		UserEntry userEntry = getUser(user);
		return getChat(userEntry);
	}

	public Chat getChat(UserEntry userEntry) {
		Element element = chatCache.get(userEntry);
		if (element == null) {
			Chat chat = connection.createChat(userEntry.getUser());
			chatCache.put(new Element(userEntry, chat));
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
