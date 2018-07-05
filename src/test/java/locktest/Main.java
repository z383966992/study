package locktest;

public class Main {

	public static void main(String[] args) throws Exception{
//		CyclicBarrier barrier = new  CyclicBarrier(10);
//		CountDownLatch latch = new CountDownLatch(10);
//		new CyclicBarrierLock(barrier, latch).exec();
		
		ThreadLockTest tlt =  new ThreadLockTest(100);
		tlt.test();
	}
}
