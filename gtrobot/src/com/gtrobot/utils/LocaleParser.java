package com.gtrobot.utils;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class LocaleParser {
	private static final Map supportedLocales = new Hashtable();
	static  
	{
		Locale[] locales = Locale.getAvailableLocales();
		for(int i = 0; i< locales.length; i++)
		{
//			System.out.println(locales[i].getCountry());
			supportedLocales.put(locales[i].getCountry().toLowerCase(), locales[i]);
		}
	}

	public static Locale parseLocale(String lang) {
		lang = lang.trim().toLowerCase();
		return (Locale)supportedLocales.get(lang);
	}

}
