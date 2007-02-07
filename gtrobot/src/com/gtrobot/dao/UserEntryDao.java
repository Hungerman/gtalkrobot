package com.gtrobot.dao;

import com.gtrobot.context.UserEntry;

public class UserEntryDao {
	public UserEntry getUseEntry(String email)
	{
		return null;
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

	// protected void parse(ResultSet rs, Object bean)
	// {
	// Connection conn = null;
	// Statement stmt = null;
	// ResultSet rs = null;
	//
	// try {
	// conn = getConnection();
	// stmt = conn.createStatement();
	// rs = stmt.executeQuery("");
	// int numcols = rs.getMetaData().getco.getColumnCount();
	// while(rs.next()) {
	// for(int i=1;i<=numcols;i++) {
	// System.out.print("\t" + rs.geto.getString(i));
	// }
	// System.out.println("");
	// }
	// } catch(SQLException e) {
	// e.printStackTrace();
	// } finally {
	// try { rs.close(); } catch(Exception e) { }
	// try { stmt.close(); } catch(Exception e) { }
	// try { conn.close(); } catch(Exception e) { }
	// }
	//
	// }

}
