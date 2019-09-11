package aopcachetest.cache.config;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class BloomFilterConfiger {
    @Value("${ele.filter.expectedInsertions}")
    private int expectedInsertions;
    @Value("${ele.filter.fpp}")
    private double fpp;

    @Bean
    public BloomFilter<CharSequence> getBloomilter() {
        return BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),expectedInsertions,fpp);
    }

}
