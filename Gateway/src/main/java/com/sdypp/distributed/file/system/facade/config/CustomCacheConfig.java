package com.sdypp.distributed.file.system.facade.config;

import com.sdypp.distributed.file.system.facade.cache.RedisCacheService;
import com.sdypp.distributed.file.system.facade.cache.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

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

    @Value("${spring.cache.redis.host}")
    private String host;

    @Value("${spring.cache.redis.port}")
    private Integer port;

    @Value("${spring.cache.redis.time-to-live}")
    private Integer timeToLive;

    @Value("${spring.cache.redis.password}")
    private String password;

    @Autowired
    private RequestRepository requestRepository;

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
        return new RedisCacheService(requestRepository);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName(this.host);
        jedisConFactory.setPort(this.port);
        jedisConFactory.setPassword(this.password);
        jedisConFactory.setTimeout(this.timeToLive);
        return jedisConFactory;
    }

}