package proxy;

public class RealSubject implements Subject{

	@Override
	public void methodA() {
		System.out.println("method A!");
		
	}

	@Override
	public void methodB(String str) {
		System.out.println("method B : " + str );
	}

}
