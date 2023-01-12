/**
 * Created on 2022/5/8.
 */
package com.alicp.jetcache.redis;

import com.alicp.jetcache.SimpleCacheManager;
import com.alicp.jetcache.support.BroadcastManager;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPool;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class RedisBroadcastManagerTest extends AbstractBroadcastManagerTest {
    @Test
    public void test() throws Exception {
        GenericObjectPoolConfig pc = new GenericObjectPoolConfig();
        pc.setMinIdle(2);
        pc.setMaxIdle(10);
        pc.setMaxTotal(10);
        JedisPool jedis = new JedisPool(pc, "127.0.0.1", 6379);
        BroadcastManager manager = RedisCacheBuilder.createRedisCacheBuilder().jedisPool(jedis)
                //.jedis(new UnifiedJedis(new HostAndPort("127.0.0.1", 6379)))
                .keyPrefix(RedisBroadcastManagerTest.class.getName())
                .broadcastChannel(RedisBroadcastManagerTest.class.getName())
                .createBroadcastManager(new SimpleCacheManager());
        testBroadcastManager(manager);
    }
}
