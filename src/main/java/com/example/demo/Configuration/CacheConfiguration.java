package com.example.demo.Configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfiguration {

    private static final long CACHE_EVICT_DELAY_MS = 600_000;//10 min

    @Bean
    public CacheManager cacheManager(){
        ConcurrentMapCacheManager manager = new ConcurrentMapCacheManager();
        manager.setCacheNames(EnumSet.allOf(CacheName.class)
                        .stream()
                        .map(CacheName :: getValue)
                        .collect(Collectors.toList())
                    );
        return manager;
    }

    @Component
    public class CacheEvictor{
        private final CacheManager cacheManager;

        public CacheEvictor(CacheManager cacheManager) {
            this.cacheManager = cacheManager;
        }

        @Scheduled(fixedDelay = CACHE_EVICT_DELAY_MS)
        public void evictCache(){
            cacheManager
                    .getCacheNames()
                    .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        }
    }
}
