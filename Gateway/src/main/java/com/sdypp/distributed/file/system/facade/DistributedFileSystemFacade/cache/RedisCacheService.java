package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class RedisCacheService extends AbstractCacheManager {

    private static Logger logger = LoggerFactory.getLogger(RedisCacheService.class);

    private final List<Cache> caches = new ArrayList<>();
    private final HashMap<String, Cache> cacheMap = new HashMap<>();

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return this.caches;
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        // Quick check for existing cache...
        //TODO: Agregar conexión con Redis y LRU
        logger.info("Searching for cache {}", name);
        Cache cache = this.cacheMap.getOrDefault(name, null);
        if (cache == null) {
            logger.info("Cache miss...");
            cache = new ListCache(name);
            this.cacheMap.put(name, cache);
        }
        return cache;
    }

}
