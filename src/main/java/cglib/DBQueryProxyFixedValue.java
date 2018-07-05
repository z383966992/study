package cglib;

import net.sf.cglib.proxy.FixedValue;

public class DBQueryProxyFixedValue implements FixedValue{

	@Override
	public Object loadObject() throws Exception {
		System.out.println("DBQueryProxyFixedValue");
		return "Fixed Value";
	}

}
