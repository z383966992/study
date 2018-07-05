package cglib;



import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DBQueryProxy_T implements MethodInterceptor{

	@Override
	public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("in interceptor_T！");
		return methodProxy.invokeSuper(obj, objects);
	}

}
