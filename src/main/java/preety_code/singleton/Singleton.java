package preety_code.singleton;

import java.util.concurrent.atomic.AtomicInteger;

public class Singleton {
	
	private AtomicInteger num = new AtomicInteger(0);
	
	private static final Singleton instance = new Singleton();
	
	private Singleton() {
		
	}

	public static Singleton getInstance() {
		return instance;
	}
	
	public int add() {
		return num.incrementAndGet();
	}
}
