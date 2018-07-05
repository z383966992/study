package futuretask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * FutureTask可以用来异步计算，计算只执行一次, 当主线程中有一个比较长的计算任务的时候，可以使用FutureTask
 * 这个例子用五个线程计算整数的加法，在主线程中计算总和
 * @author Administrator
 */
public class FutureTaskForMultiCompute {

	public static void main(String[] args) throws Exception{
		FutureTaskForMultiCompute inst = new FutureTaskForMultiCompute();
		List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();

		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (int i = 1; i <= 10; i++) {
			FutureTask<Integer> ft = new FutureTask<Integer>(inst.new ComputeTask(i, i+""));
			taskList.add(ft);
			executor.execute(ft);
		}
		
		int sum = 0;
		
		for(FutureTask<Integer> ft : taskList) {
			Integer value = ft.get();
			sum = sum + value;
			System.out.println(value);
		}
		System.out.println("总和 ：" + sum);
		executor.shutdown();
	}

	private class ComputeTask implements Callable<Integer> {

		private int value;
		private String taskName;

		public ComputeTask(int value, String taskName) {
			this.value = value;
			this.taskName = taskName;
		}

		public String getTaskName() {
			return taskName;
		}
		
		@Override
		public Integer call() throws Exception {
			System.out.println("子线程 ：" + taskName + "在执行！");
			for (int i = 1; i <= 100; i++) {
				value = value + i;
			}

			return value;
		}

	}
}
