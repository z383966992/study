package future;

import java.util.concurrent.Future;

public interface Fetcher {
	Future<Data> fetchData();
}
