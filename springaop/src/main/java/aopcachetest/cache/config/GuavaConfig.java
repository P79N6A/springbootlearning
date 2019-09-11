package aopcachetest.cache.config;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class GuavaConfig {

    @Value("${ele.mapper.expiretime:60}")
    private int expiretime;
    @Value("${ele.guava.concurrencyLevel:5}")
    private int concurrencyLevel;
    @Value("${ele.guava.initialCapacity:10}")
    private int initialCapacity;
    @Value("${ele.guava.type:expireAfterWrite}")
    private String type;
    @Value("${ele.mapper.unit:SECONDS}")
    private String unit;



    @Bean
    public LoadingCache<String, String> getGuavaCache() {
        CacheBuilder<Object, Object> cacheBuilder=CacheBuilder.newBuilder();
         cacheBuilder.initialCapacity(initialCapacity).
                 concurrencyLevel(concurrencyLevel);
        switch (type) {
            case "expireAfterWrite":
                cacheBuilder.expireAfterWrite(expiretime, Enum.valueOf(TimeUnit.class, unit));
                break;
            case "expireAfterAccess":
                cacheBuilder.expireAfterAccess(expiretime, Enum.valueOf(TimeUnit.class, unit));
                break;
            case "refreshAfterWrite":
                cacheBuilder.refreshAfterWrite(expiretime, Enum.valueOf(TimeUnit.class, unit));
                break;
        }

        return cacheBuilder.build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return "";
            }
        });
    }
}
