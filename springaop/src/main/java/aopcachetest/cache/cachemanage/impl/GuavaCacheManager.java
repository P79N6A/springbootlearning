package aopcachetest.cache.cachemanage.impl;

import com.google.common.cache.LoadingCache;
import aopcachetest.cache.cachemanage.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("guavaCache")
public class GuavaCacheManager implements CacheManager {

    @Autowired
    private LoadingCache<String, String> cache;


    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        cache.put(key, value);
    }

    @Override
    public String get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public boolean containsKey(String key) {
        return cache.getIfPresent(key) != null;
    }

}
