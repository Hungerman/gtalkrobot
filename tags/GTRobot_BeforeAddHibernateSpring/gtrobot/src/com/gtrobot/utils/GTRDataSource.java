package com.gtrobot.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Initialize the datasource and provide the interface to retrive database
 * connection from the data connection pool.
 * 
 * @author sunyuxin
 * 
 */
public class GTRDataSource {
	protected static final transient Log log = LogFactory
			.getLog(GTRDataSource.class);

	private static GTRDataSource instance = new GTRDataSource();

	private BasicDataSource bds;

	private GTRDataSource() {
		setupDataSource();
	}

	public static GTRDataSource getInstance() {
		return instance;
	}

	private void setupDataSource() {
		GTRobotConfiguration gtrc = GTRobotConfiguration.getInstance();

		bds = new BasicDataSource();
		bds.setDriverClassName(gtrc.getDbDriver());
		bds.setUsername(gtrc.getDbUsername());
		bds.setPassword(gtrc.getDbPassword());
		bds.setUrl(gtrc.getDbUrl());
	}

	public Connection getConnection() throws SQLException {
		return bds.getConnection();
	}

	public void printDataSourceStats() throws SQLException {
		log.info("NumActive: " + bds.getNumActive());
		log.info("NumIdle: " + bds.getNumIdle());
	}

	public void shutdown() throws SQLException {
		bds.close();
	}
}
