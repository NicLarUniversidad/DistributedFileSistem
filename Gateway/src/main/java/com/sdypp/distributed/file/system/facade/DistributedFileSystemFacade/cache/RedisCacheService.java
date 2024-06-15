package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.lang.Nullable;

import java.util.*;

public class RedisCacheService extends AbstractCacheManager {

    private static Logger logger = LoggerFactory.getLogger(RedisCacheService.class);

    private final List<Cache> caches = new ArrayList<>();
    private final HashMap<String, Cache> cacheMap = new HashMap<>();
    private final RequestRepository redisRequestRepository;

    public RedisCacheService(RequestRepository redisRequestRepository) {
        this.redisRequestRepository = redisRequestRepository;
    }

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
        Cache cache = null;
        Optional<Request> requestOptional = this.redisRequestRepository.findById(name);
        if (requestOptional.isPresent()) {
            cache = requestOptional.get().getContent();
        } else {
            logger.info("Cache miss...");
            cache = new ListCache(name);
            this.cacheMap.put(name, cache);
            var request = new Request();
            request.setId(name);
            request.setContent(cache);
            this.redisRequestRepository.save(request);
        }
        return cache;
    }

}
