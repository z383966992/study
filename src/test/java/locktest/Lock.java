package locktest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Lock implements Runnable {

	private int num;

	public Lock(int num) {
            this.num = num;
    }

	@Override
	public void run() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient("10.141.4.236:2182", retryPolicy);
		client.start();

		InterProcessMutex lock = new InterProcessMutex(client, "/mylock");
		try {
			System.out.println("我是第" + num + "号线程，我开始获取锁");
			lock.acquire();
			System.out.println("我是第" + num + "号线程，我已经获取锁");
			Thread.sleep(10000);
			//这个地方我要做一个处理
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				lock.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		client.close();
	}
}
