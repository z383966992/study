package locktest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
/**
 * curator 分布式锁
 * @author Administrator
 *
 */
public class ThreadLockTest {
	
	private int threadNum = 100;
	private CountDownLatch latch = null;
	private CountDownLatch startGate = new CountDownLatch(1);
	private int count = 0;
	private Locks lock = new Locks("10.141.4.223","2181","/mylock");
	
	public ThreadLockTest(int threadNum) {
		this.threadNum = threadNum;
		latch = new CountDownLatch(threadNum);
		startGate = new CountDownLatch(1);
	}
	
	public void test() throws Exception {
		for(int i=0; i<threadNum; i++) {
			Thread t = new Thread(){
				@Override
				public void run() {
					try {
						//使线程等待
						startGate.await();
						lock.lock();
//						update();
						count++;
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							latch.countDown();
							lock.releaseLock();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			};
			t.start();
		}
System.out.println("1111");
		//使所有等待的线程开始跑
		startGate.countDown();
System.out.println("2222");
		long start = System.currentTimeMillis();
		latch.await();
System.out.println("3333");
		long end = System.currentTimeMillis();
		
		long time = (end - start)/1000;
		
		System.out.println("执行时间 : "+ time);
		System.out.println("count : " + count);
	}
	
//	public void exec() throws Exception{
//		for(int i=0; i<10; i++) {
//			count = 0;
//			this.test();
//			Thread.sleep(20000);
//		}
//		
//	}
	
//	public static void main(String[] args) throws Exception{
//		ThreadLockTest tlt =  new ThreadLockTest();
//		tlt.exec();
//		
//	}
	
	//读取一个数据，并给数字加一
	public void update() {
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
			// 要执行的SQL语句
			String selectSql = "select digit from locktest where id=?;";
			// statement用来执行SQL语句
			pstmt = conn.prepareStatement(selectSql);
			pstmt.setInt(1, 1);
			
			ResultSet rs = pstmt.executeQuery();
			int result = -1;
			while(rs.next()) {
				result = rs.getInt(1);
			}
			result = result + 1;
			
			String updateSql = "update locktest set digit=?;";
			pstmt = conn.prepareStatement(updateSql);
			pstmt.setInt(1, result);
			pstmt.execute();
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

}
//"10.141.4.236:2182"
//"/mylock"
class Locks{
	
	private RetryPolicy retryPolicy = null;
	private CuratorFramework client = null;
	private InterProcessMutex lock = null;
	
	public Locks(String ip, String port, String path) {
		retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient(ip +":" + port, retryPolicy);
		lock = new InterProcessMutex(client, path);
		client.start();
	}
	
	public void lock() throws Exception {
		lock.acquire();
	}
	
	public void releaseLock() throws Exception {
		lock.release();
	}
	
}
