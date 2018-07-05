package preety_code.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * 单元素的枚举，是实现单例模式的最好方式
 * @author liangliangzhou3
 *
 */
public enum DatabaseConnector {
	
	DataSource;
	
	private Connection conn = null;
	
	private DatabaseConnector() {
		System.out.println("constructor");
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/test";
		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "root";
		try {
			Class.forName(driver);
			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConn() {
		try {
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}