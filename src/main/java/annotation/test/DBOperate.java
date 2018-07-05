package annotation.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import annotation.definition.SqlConnector;

public class DBOperate {
	
	@SqlConnector(
			driver="com.mysql.jdbc.Driver", 
			url="jdbc:mysql://127.0.0.1:3306/test",
			user="root",
			passwd="root")
	private Connection conn;

	public void read() {
		try {
			String sql = "select * from store";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				for(int i=1; i<=3; i++) {
					System.out.print(rs.getString(i) + "|");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
