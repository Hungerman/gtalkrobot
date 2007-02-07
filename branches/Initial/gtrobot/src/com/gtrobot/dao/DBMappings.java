package com.gtrobot.dao;

import java.util.Hashtable;
import java.util.Map;

public class DBMappings {
	public static final String T_USER_ENTRY = "USER_ENTRY";

	private static DBMappings instance = new DBMappings();

	private Map mappings;

	private DBMappings() {
		initMappings();
	}

	public static DBMappings getInstance() {
		return instance;
	}

	public Map getMappings(String tableName) {
		return (Map) mappings.get(tableName);
	}

	private void initMappings() {
		mappings = new Hashtable();

		Map userEntryMappings = initUserEntryMappings();
		mappings.put(T_USER_ENTRY, userEntryMappings);
	}

	private Map initUserEntryMappings() {
		Map result = new Hashtable();
		result.put("EMAIL", "email");

		return result;
	}

}
