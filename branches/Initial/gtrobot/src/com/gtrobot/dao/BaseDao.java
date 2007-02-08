package com.gtrobot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gtrobot.exception.DataAccessException;
import com.gtrobot.utils.GTRDataSource;

public class BaseDao {
	private static ThreadLocal threadLoaclConnection = new ThreadLocal();

	protected Connection getConnection() throws SQLException {
		Connection connection = (Connection) threadLoaclConnection.get();
		if (connection == null) {
			connection = GTRDataSource.getInstance().getConnection();
			threadLoaclConnection.set(connection);
		}
		return connection;
	}

	protected void closeConnection() {
		Connection connection = (Connection) threadLoaclConnection.get();
		if (connection != null) {
			threadLoaclConnection.set(null);
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}

	// protected void parse(ResultSet rs, Object bean) throws SQLException
	// {
	// ResultSetMetaData metaData = rs.getMetaData();
	// String tableName = metaData.getTableName(0);
	// DBMappings.getInstance().getMappings(tableName);
	//            
	// int numcols = metaData.getColumnCount();
	// while(rs.next()) {
	// for(int i=1;i<=numcols;i++) {
	// System.out.print("\t" + rs.getString(i));
	// }
	// System.out.println("");
	// }
	//   
	// }

	protected PreparedStatement prepareStatement(String sql) throws DataAccessException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			return stmt;
		} catch (SQLException e) {
			closeStatement(stmt);
			throw new DataAccessException(e);
		} 
	}

	protected void closeStatement(Statement stmt) {
		try {
			stmt.close();
		} catch (Exception e) {
		}
		try {
			closeConnection();
		} catch (Exception e) {
		}
	}

	protected void executeQuesry(String sql, Object[] argv) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("");
//			int numcols = rs.getMetaData().getco.getColumnCount();
//			while (rs.next()) {
//				for (int i = 1; i <= numcols; i++) {
//					System.out.print("\t" + rs.geto.getString(i));
//				}
//				System.out.println("");
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				stmt.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

}
