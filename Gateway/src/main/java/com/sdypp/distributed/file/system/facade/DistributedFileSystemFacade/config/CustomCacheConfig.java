package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.config;

import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.cache.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableCaching
public class CustomCacheConfig {

//    @Bean
//    public CacheManager cacheManager() {
//        return new ConcurrentMapCacheManager("archivo");
//    }

//    @Cacheable("archivo")
//    public Optional<File> getFileById(Long id) {
//        return repository.findById(id);
//    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig() //
//                .prefixCacheNameWith(this.getClass().getPackageName() + ".") //
//                .entryTtl(Duration.ofHours(1)) //
//                .disableCachingNullValues();
//
//        return RedisCacheManager.builder(connectionFactory) //
//                .cacheDefaults(config) //
//                .build();
        return new RedisCacheService();
    }

}