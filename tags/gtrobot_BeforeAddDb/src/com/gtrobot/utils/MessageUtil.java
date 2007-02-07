package com.gtrobot.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.context.CacheContext;

public class MessageUtil {
	protected static final transient Log log = LogFactory
			.getLog(MessageUtil.class);

	public static final String GTALK_ROBOT_RESOURCE = "gtrobot";

	private static final MessageUtil instance = new MessageUtil();

	private Cache messageCache;

	private MessageUtil() {
		messageCache = CacheContext.getInstance().getMessageCache();
	}

	public static MessageUtil getInstance() {
		return instance;
	}

	public String getMessage(String key) {
		return getMessage(key, null);
	}

	public String getMessage(String key, Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}

		StringBuffer cacheKey = new StringBuffer(key);
		cacheKey.append("::").append(locale);
		Element element = messageCache.get(cacheKey.toString());
		if (element == null) {
			String message = getMessageInternal(key, locale);
			messageCache.put(new Element(cacheKey.toString(), message));
			return message;
		} else {
			return (String) element.getValue();
		}
	}

	private String getMessageInternal(String key, Locale locale) {
		ResourceBundle resourceBundle = null;

		String message = null;
		try {
			if (locale == null) {
				resourceBundle = ResourceBundle.getBundle(GTALK_ROBOT_RESOURCE);
			} else {
				resourceBundle = ResourceBundle.getBundle(GTALK_ROBOT_RESOURCE,
						locale);
			}
			message = resourceBundle.getString(key);
		} catch (Exception e) {
			log.error("Exception in getMessageInternal:", e);
			message = "Not-Defined-Message.";
		}
		return message;
	}
}
