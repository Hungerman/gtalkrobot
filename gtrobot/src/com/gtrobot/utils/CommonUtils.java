package com.gtrobot.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CommonUtils {
	private static final Map supportedLocales = new Hashtable();
	static {
		Locale[] locales = Locale.getAvailableLocales();
		for (int i = 0; i < locales.length; i++) {
			// System.out.println(locales[i].getCountry());
			supportedLocales.put(locales[i].getCountry().toLowerCase(),
					locales[i]);
		}
	}

	public static Locale parseLocale(String lang) {
		lang = lang.trim().toLowerCase();
		return (Locale) supportedLocales.get(lang);
	}

	/**
	 * Parse the command message body into String List
	 * 
	 * @param body
	 * @return
	 */
	public static List parseCommand(String body) {
		List results = new ArrayList();
		StringBuffer tempStr = new StringBuffer();
		for (int i = 1; i < body.length(); i++) {
			char cc = body.charAt(i);

			if (cc == ' ' || cc == '\t' || cc == '\n' || cc == '\r') {
				if (tempStr.length() == 0) {
					continue;
				} else {
					results.add(tempStr.toString());
					tempStr = new StringBuffer();
				}
			} else {
				tempStr.append(cc);
			}
		}
		if (tempStr.length() != 0) {
			results.add(tempStr.toString());
		}
		return results;
	}
}
