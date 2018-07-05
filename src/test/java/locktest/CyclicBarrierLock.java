//package locktest;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.CyclicBarrier;
//
//public class CyclicBarrierLock implements Runnable{
//	
//	private int count;
//	private Locks lock = new Locks("10.141.4.223","2181","/mylock");
//	private CyclicBarrier barrier = null;
//	private CountDownLatch latch = null;
//	
//	public CyclicBarrierLock(CyclicBarrier barrier, CountDownLatch latch) {
//		this.barrier = barrier;
//		this.latch = latch;
//	}
//
//	@Override
//	public void run() {
//		try {
//			System.out.println("in run");
//			System.out.println("barrier.getNumberWaiting() : " + barrier.getNumberWaiting());
//			barrier.await();
//			System.out.println("after in run");
//			lock.lock();
//			count = count + 1;
//			System.out.println("count : " + count);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				System.out.println("count down latch!");
////				latch.countDown();
//				lock.releaseLock();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public void exec() throws Exception{
//		for(int i=1; i<=10; i++) {
//			new Thread(new CyclicBarrierLock(barrier, latch)).start();
//		}
//		long start = System.currentTimeMillis();
////		latch.await();
//		long end = System.currentTimeMillis();
//		
//		long time = (end - start)/1000;
//		Thread.sleep(20000);
//		System.out.println("执行时间 : "+ time);
//		System.out.println("count : " + count);
//	}
//}
