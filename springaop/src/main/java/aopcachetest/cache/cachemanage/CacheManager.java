package aopcachetest.cache.cachemanage;

import java.util.concurrent.TimeUnit;

public interface CacheManager {
     void set(String key, String value, long timeout, TimeUnit timeUnit);

     String get(String key);

     boolean containsKey(String key);

}
