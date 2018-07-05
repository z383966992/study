package cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class TestCGLibProxy {

	public static void main(String[] args) {
		//单回调
//		DBQueryProxy proxy = new DBQueryProxy();
//		Enhancer enhancer = new Enhancer();
//		enhancer.setSuperclass(DBQuery.class);
//		enhancer.setCallback(proxy);
//		
//		DBQuery dbQuery = (DBQuery)enhancer.create();
//		System.out.println(dbQuery.getElement("Hello"));
//		System.out.println();
//		System.out.println(dbQuery.getAllElements());
//		System.out.println();
//		System.out.println(dbQuery.sayHello());
		
		
		//多回调，通过CallbackFilter决定调用那个
//		DBQueryProxy dbQueryProxy = new DBQueryProxy();
//		DBQueryProxy_T dbQueryProxy2 = new DBQueryProxy_T();
//		
//		Enhancer enhancer = new Enhancer();
//		enhancer.setSuperclass(DBQuery.class);
//		enhancer.setCallbacks(new Callback[]{dbQueryProxy, dbQueryProxy2});
//		enhancer.setCallbackFilter(new CallbackFilter() {
//			
//			@Override
//			public int accept(Method method) {
//				if(method.getName().equals("getElement")) {
//					return 0;
//				} else {
//					return 1;
//				}
//				
//			}
//		});
//		
//		DBQuery dbQuery = (DBQuery)enhancer.create();
//		System.out.println(dbQuery.getElement("Hello"));
//		System.out.println(dbQuery.getAllElements());
		
//		不处理		利用枚举常量 Callback noopCb = NoOp.INSTANCE;
		DBQueryProxy dbQueryProxy = new DBQueryProxy();
		DBQueryProxy_T dbQueryProxy_T = new DBQueryProxy_T();
		Callback noopCb = NoOp.INSTANCE;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(DBQuery.class);
		enhancer.setCallbacks(new Callback[]{dbQueryProxy, dbQueryProxy_T, noopCb});
		enhancer.setCallbackFilter(new CallbackFilter() {
			
			@Override
			public int accept(Method method) {
				if(method.getName().equals("getElement")) {
					return 0;
				} else if(method.getName().equals("getAllElements")) {
					return 1;
				} else {
					return 2;
				}
			}
		});
		
		
		DBQuery dbQuery = (DBQuery)enhancer.create();
		System.out.println(dbQuery.getElement("Hello"));
		System.out.println(dbQuery.getAllElements());
		System.out.println(dbQuery.methodForNoop());
		
	}
	
}
