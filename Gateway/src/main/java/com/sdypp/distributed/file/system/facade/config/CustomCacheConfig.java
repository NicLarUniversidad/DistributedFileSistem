package com.sdypp.distributed.file.system.facade.config;

import com.sdypp.distributed.file.system.facade.cache.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
@EnableCaching
public class CustomCacheConfig {

    @Value("${spring.cache.redis.host}")
    private String host;

    @Value("${spring.cache.redis.port}")
    private Integer port;

    @Value("${spring.cache.redis.time-to-live}")
    private Integer timeToLive;

    @Value("${spring.cache.redis.password}")
    private String password;

    @Value("${sdypp.cache.redis.minutes:1}")
    private Integer minutes;

    @Autowired
    private RequestRepository requestRepository;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(minutes));
        return RedisCacheManager.builder(jedisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName(this.host);
        jedisConFactory.setPort(this.port);
        //jedisConFactory.setPassword(this.password);
        jedisConFactory.setTimeout(this.timeToLive);
        return jedisConFactory;
    }

}