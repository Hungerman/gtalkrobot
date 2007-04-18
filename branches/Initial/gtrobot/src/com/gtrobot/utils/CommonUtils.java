package com.gtrobot.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * ar Arabic zh Chinese (Zhongwen) de German (Deutsch) en English fr French el
 * Greek (Ellinika) iw Hebrew (Iwrith) ja Japanese ko Korean nl Dutch
 * (Nederlands) no Norwegian pl Polish ru Russian sv Swedish bo Tibetan
 * (Bodskad) eo Esperanto es Spanish in Indonesian it Italian lt Latin pt
 * Portugese hi Hindi ur Urdu mn Mongolian kl Inuit (formerly Eskimo)
 * 
 * @author sunyuxin
 * 
 */
public class CommonUtils {
	private static final Map<String, Locale> supportedLocales = new Hashtable<String, Locale>();
	static {
		Locale[] locales = Locale.getAvailableLocales();
		for (int i = 0; i < locales.length; i++) {
			// System.out.println(locales[i].getLanguage());
			supportedLocales.put(locales[i].getLanguage().toLowerCase(),
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
	public static List<String> parseCommand(String body,
			boolean isImmediateCommand) {
		List<String> results = new ArrayList<String>();
		StringBuffer tempStr = new StringBuffer();
		int start = 0;
		if (isImmediateCommand) {
			start = 1;
		}
		for (int i = start; i < body.length(); i++) {
			char cc = body.charAt(i);

			if (cc == ' ' || cc == '\t' || cc == '\n' || cc == '\r'
					|| cc == '　') {
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

	public static List<String> parseSimpleCommand(String body) {
		List<String> results = new ArrayList<String>();
		StringBuffer tempStr = new StringBuffer();
		int i = 0;
		if (body == null) {
			results.add("");
			return results;
		}
		// parse the first command
		for (; i < body.length(); i++) {
			char cc = body.charAt(i);

			if (cc == ' ' || cc == '\t' || cc == '\n' || cc == '\r'
					|| cc == '　') {
				if (tempStr.length() == 0) {
					continue;
				} else {
					break;
				}
			} else {
				tempStr.append(cc);
			}
		}
		results.add(tempStr.toString().toLowerCase());

		if (i <= body.length()) {
			results.add(body.substring(i).trim());
		}
		return results;
	}
}
