package com.gtrobot.utils;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GTRobotConfiguration {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotConfiguration.class);

	private static GTRobotConfiguration instance = new GTRobotConfiguration();

	private boolean initialized;

	private String username;

	private String password;

	private String dbSchema;

	private String dbUsername;

	private String dbPassword;

	private String dbUrl;

	private String dbDriver;

	private GTRobotConfiguration() {
		initialized = false;
		init();
	}

	public static GTRobotConfiguration getInstance() {
		return instance;
	}

	private void init() {
		Properties prop = new Properties();
		try {
			prop.load(GTRobotConfiguration.class.getClassLoader()
					.getResourceAsStream("gtrobot.properties"));

			username = getNotNullParamter(prop, "gtrobot.username");
			password = getNotNullParamter(prop, "gtrobot.password");
			dbSchema = getNotNullParamter(prop, "database.schema");
			dbUsername = getNotNullParamter(prop, "database.jdbc.username");
			dbPassword = getNotNullParamter(prop, "database.jdbc.password");
			dbUrl = getNotNullParamter(prop, "database.jdbc.url");
			dbDriver = getNotNullParamter(prop, "database.jdbc.driver.class");

			initialized = true;
		} catch (Exception e) {
			log.error("Error while loading gtrobot.properties!", e);
			initialized = false;
		}
	}

	private String getNotNullParamter(Properties prop, String key)
			throws Exception {
		String result = prop.getProperty(key);
		if (result == null || result.trim().length() == 0)
			throw new Exception("Error: Null value for: " + key);
		return result.trim();
	}

	public boolean isInitialized() {
		return initialized;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public String getDbSchema() {
		return dbSchema;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

}
