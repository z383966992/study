package retryer;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;

import utils.HttpClientUtil;

public class RetryerTest {
	
	public static void main(String[] args) {
		//调用接口获取数据
    	Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
    			.retryIfException()
    			.retryIfResult(Predicates.equalTo(false))
    			.withWaitStrategy(WaitStrategies.fixedWait(10, TimeUnit.SECONDS))
    			.withStopStrategy(StopStrategies.stopAfterAttempt(10))
    			.build();
    	
    	try {
    		retryer.call(new Callable<Boolean>() {
				
				@Override
				public Boolean call() throws Exception {
					String url = "https://www.baidu.com/";
					String result = HttpClientUtil.get(url);
					if(result == null) {
						return false;
					}
					//解析结果，设置对象
					return true;
				}
			});
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
	}

}
