package singleton;

public class Singleton {
	
	private static Singleton singletion = null;
	
	private Singleton() {}
	
	public static Singleton getSingleton() {
		if(singletion == null) {
			singletion = new Singleton();
		}
		return singletion;
	}
}
