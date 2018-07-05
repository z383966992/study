package operate_redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class PutData {

	public static void main(String[] args) {
		RedisClient client = new RedisClient();
		Jedis jedis = client.getJedis();
		System.out.println(jedis.set("zzz", "lll", "NX", "EX", 10));
		
		client.returnResource(jedis);
	}
}
