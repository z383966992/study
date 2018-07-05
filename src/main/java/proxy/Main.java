package proxy;

import java.lang.reflect.Proxy;

public class Main {

	public static void main(String[] args) {
		RealSubject subject = new RealSubject();
		SubjectProxy proxy = new SubjectProxy(subject);
		Subject sub = (Subject)Proxy.newProxyInstance(proxy.getClass().getClassLoader(), subject.getClass().getInterfaces(), proxy);
		sub.methodA();
		sub.methodB("ssss");
	}
}
