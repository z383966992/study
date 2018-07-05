package hystrix;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloWorldHystrixCommand extends HystrixCommand<String>{

	private final String name;
	
	public HelloWorldHystrixCommand(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}
	
	@Override
	protected String run() throws Exception {
		return "Hello " + name;
	}

	public static void main(String[] args)throws Exception {
		System.out.println(new HelloWorldHystrixCommand("LX").execute());
	}
	
}





































