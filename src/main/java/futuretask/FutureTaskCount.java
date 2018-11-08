package futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
/**
 * 把一个较慢的任务放到FutureTask中执行
 * 和主线程并发，并且可以获得计算的结果
 * @author Administrator
 *
 */
public class FutureTaskCount {
	
	private final FutureTask<Integer> future = new FutureTask<Integer>(new Callable<Integer>() {
		@Override
		public Integer call() throws Exception {
			int sum = 0;
			
			for(int i=1; i<=100; i++) {
//				Thread.sleep(500);
				sum = sum+i;
			}
			int j = 1/0;
			return sum;
		}
	});
	
	public void test() throws Exception{
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(future);
		
		System.out.println("主线程继续运行");
		Thread.sleep(2000);
		System.out.println("获取future的执行结果");
		Integer value = future.get();
		System.out.println("value : " + value);
		executor.shutdown();
	}
	
	
	public static void main(String[] args) throws Exception{
		new FutureTaskCount().test();
	}
}