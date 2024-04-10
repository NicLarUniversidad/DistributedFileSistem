package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("archivo");
    }

//    @Cacheable("archivo")
//    public Optional<File> getFileById(Long id) {
//        return repository.findById(id);
//    }
}