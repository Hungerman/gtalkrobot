package utils;

import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;

public class MessageUtil {
	public static final String GTALK_ROBOT_RESOURCE = "gtrobot";

	private static final MessageUtil instance = new MessageUtil();

	private Cache messageCache;

	private MessageUtil() {
		messageCache = GlobalContext.getInstance().getMessageCache();
	}

	public static MessageUtil getInstance() {
		return instance;
	}

	public String getMessage(String key, UserEntry userEntry) {
		Locale locale = userEntry.getLocale();

		StringBuffer cacheKey = new StringBuffer(key);
		cacheKey.append("::").append(locale);
		Element element = messageCache.get(cacheKey.toString());
		if (element == null) {
			String message = getMessage(key, locale);
			messageCache.put(new Element(cacheKey.toString(), message));
			return message;
		} else {
			return (String) element.getValue();
		}
	}

	public String getMessage(String key, Locale locale) {
		ResourceBundle resourceBundle = null;
		if (locale == null) {
			resourceBundle = ResourceBundle.getBundle(GTALK_ROBOT_RESOURCE);
		} else {
			resourceBundle = ResourceBundle.getBundle(GTALK_ROBOT_RESOURCE,
					locale);
		}

		return resourceBundle.getString(key);
	}
}
