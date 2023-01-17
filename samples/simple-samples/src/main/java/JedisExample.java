/**
 * Created on 2022/07/18.
 */

import com.alibaba.fastjson.parser.ParserConfig;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.redis.RedisCacheBuilder;
import com.alicp.jetcache.support.DecoderMap;
import com.alicp.jetcache.support.Fastjson2KeyConvertor;
import com.alicp.jetcache.support.FastjsonValueDecoder;
import com.alicp.jetcache.support.FastjsonValueEncoder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class JedisExample {

    public static void oriMain(){
        GenericObjectPoolConfig pc = new GenericObjectPoolConfig();
        pc.setMinIdle(2);
        pc.setMaxIdle(10);
        pc.setMaxTotal(10);
        JedisPool pool = new JedisPool(pc, "127.0.0.1", 6379);
        Cache<String, String> cache = RedisCacheBuilder.createRedisCacheBuilder()
                .jedisPool(pool)
                .keyConvertor(Fastjson2KeyConvertor.INSTANCE)
                .keyPrefix("projectA")
                .buildCache();
        cache.put("K1", "V1");
        System.out.println(cache.get("K1"));
    }

    public static void main(String[] args) {
        GenericObjectPoolConfig pc = new GenericObjectPoolConfig();
        pc.setMinIdle(2);
        pc.setMaxIdle(10);
        pc.setMaxTotal(10);
        JedisPool pool = new JedisPool(pc, "127.0.0.1", 6379);
        //DecoderMap.defaultInstance().register(SerialPolicy.IDENTITY_NUMBER_FASTJSON2,Fastjson2ValueDecoder.INSTANCE);
        DecoderMap.defaultInstance().register(FastjsonValueEncoder.IDENTITY_NUMBER, FastjsonValueDecoder.INSTANCE);
        Stream.of("JedisExample","com.alicp.jetcache.").collect(Collectors.toList()).stream().forEach(p-> ParserConfig.getGlobalInstance().addAccept(p));;
        Cache<String, User> cache = RedisCacheBuilder.createRedisCacheBuilder()
                .jedisPool(pool)
                .keyConvertor(Fastjson2KeyConvertor.INSTANCE)
                .valueDecoder(FastjsonValueDecoder.INSTANCE)
                .valueEncoder(FastjsonValueEncoder.INSTANCE)
                .keyPrefix("projectC-2")
                .buildCache();

        //JSONObject obj = JSONObject.of("asd","123");
        User u = new User();
        u.setAge(123);
        u.setName("发的");
        cache.put("K1", u);
        System.out.println(cache.get("K1"));
    }

    public static class User{
        private String name;
        private Integer age;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getAge() {
            return age;
        }
        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
