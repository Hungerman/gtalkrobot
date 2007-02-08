package com.gtrobot.exception;

import java.sql.SQLException;

public class DataAccessException extends Exception {
	private static final long serialVersionUID = -1364359383579346965L;

	private SQLException sqlException;

	public DataAccessException(SQLException sqlException) {
		super();
		this.sqlException = sqlException;
	}

	public SQLException getSqlException() {
		return sqlException;
	}
}
