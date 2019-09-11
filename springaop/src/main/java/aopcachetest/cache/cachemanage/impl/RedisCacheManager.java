package aopcachetest.cache.cachemanage.impl;

import aopcachetest.cache.cachemanage.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("redisCache")
public class RedisCacheManager implements CacheManager {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        //缓存时间单位秒
        redisTemplate.boundValueOps(key).set(value, timeout, timeUnit);
    }

    @Override
    public String get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public boolean containsKey(String key) {
        return redisTemplate.hasKey(key);
    }

}
