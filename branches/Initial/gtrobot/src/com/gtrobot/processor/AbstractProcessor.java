package com.gtrobot.processor;

import java.util.Iterator;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.context.CacheContext;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;

public abstract class AbstractProcessor implements Processor {
	protected static final transient Log log = LogFactory
			.getLog(AbstractProcessor.class);

	protected static final String endl = "\n";

	protected GlobalContext ctx;

	public AbstractProcessor() {
		ctx = GlobalContext.getInstance();
	}

	public void process(AbstractCommand abCmd) throws CommandMatchedException,
			XMPPException {
		beforeProcess(abCmd);
		if (abCmd.getErrorMessage() == null) {
			internalProcess(abCmd);
		}
	}

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		String error = abCmd.getErrorMessage();
		if (error != null) {
			log
					.error("Should not deal the error in AbstractProcessor.beforeProcess: "
							+ error);
		}
	}

	protected abstract void internalProcess(AbstractCommand abCmd)
			throws XMPPException;

	protected void sendBackMessage(AbstractCommand abCmd, String message)
			throws XMPPException {
		UserEntry userEntry = abCmd.getUserEntry();
		sendMessage(message, userEntry);
	}

	private void sendMessage(String message, UserEntry userEntry)
			throws XMPPException {
		Chat chat = GlobalContext.getInstance().getChat(userEntry);
		chat.sendMessage(message);
	}

	protected void broadcastMessage(AbstractCommand abCmd, String message)
			throws XMPPException {
		UserEntry sender = abCmd.getUserEntry();
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(sender.getNickName());
		msgBuf.append(" #> ");
		msgBuf.append(message);
		message = msgBuf.toString();

		Iterator userList = GlobalContext.getInstance().getUserList()
				.iterator();
		while (userList.hasNext()) {
			String user = (String) userList.next();
			if (user.equals(sender.getUser()) && !sender.isEchoable()) {
				continue;
			}
			UserEntry userEntry = GlobalContext.getInstance().getUser(user);
			if (userEntry.isChattable()) {
				sendMessage(message, userEntry);
			}
		}
	}

	protected Object getSession(AbstractCommand abCmd, String key) {
		Cache sessionCache = CacheContext.getInstance().getSessionCache();
		Element element = sessionCache.get(getSessionKey(abCmd, key));
		if (element == null)
			return null;
		return element.getObjectValue();
	}

	protected void putSession(AbstractCommand abCmd, String key, Object obj) {
		Cache sessionCache = CacheContext.getInstance().getSessionCache();
		String sessionKey = getSessionKey(abCmd, key);
		Element element = sessionCache.get(sessionKey);
		if (element != null)
			sessionCache.remove(sessionKey);

		element = new Element(sessionKey, obj);
		sessionCache.put(element);
	}

	protected void removeSession(AbstractCommand abCmd, String key) {
		Cache sessionCache = CacheContext.getInstance().getSessionCache();
		String sessionKey = getSessionKey(abCmd, key);
		sessionCache.remove(sessionKey);
	}

	private String getSessionKey(AbstractCommand abCmd, String key) {
		return abCmd.getUserEntry().getUser() + key;
	}

}
