package future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyFetcher implements Fetcher{
	
	private Data data;
	
	public MyFetcher(Data data) {
		this.data = data;
	}

	@Override
	public Future<Data> fetchData() {
//		Feture<data> = new Fe
		data.getX();
		data.getY();
		
		return null;
	}
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				
			}
		});
	}

}
