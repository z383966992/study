package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

public class ConnectToMysql {

	private Random random = new Random();

	public String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public int getRandomNumber(int max) {
		return random.nextInt(max);
	}

	public void insert(int num) {
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/test";
		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "root";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);

			// 要执行的SQL语句
			String sql = "INSERT INTO employee(`name`,salary, deptid) VALUES (?,?,?);";

			// statement用来执行SQL语句
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < num; i++) {
				pstmt.setString(1, getRandomString(20));
				pstmt.setInt(2, getRandomNumber(100000));
				pstmt.setInt(3, 1);
				pstmt.execute();
			}
			for (int i = 0; i < num; i++) {
				pstmt.setString(1, getRandomString(20));
				pstmt.setInt(2, getRandomNumber(100000));
				pstmt.setInt(3, 2);
				pstmt.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
				if(!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (Exception e) {
				
			}
			
		}
	}

	public static void main(String[] args) {
		ConnectToMysql ctm = new ConnectToMysql();
		ctm.insert(500000);
	}
}
