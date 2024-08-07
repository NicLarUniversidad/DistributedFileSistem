package com.sdypp.distributed.file.system.facade.services;

import com.sdypp.distributed.file.system.facade.models.settings.ConfigurationSettingsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    @Value("${spring.cache.redis.host}")
    private String redisHost;
    @Value("${spring.cache.redis.port}")
    private String redisPort;
    @Value("${sdypp.file.server.host}")
    private String fileServerHost;

    @Autowired
    private JedisConnectionFactory redisConnectionFactory;

    public ConfigurationSettingsModel getCurrentSettings() {
        ConfigurationSettingsModel settings = new ConfigurationSettingsModel();
        settings.setRedisHost(redisHost);
        settings.setRedisPort(redisPort);
        settings.setFileServerHost(fileServerHost);
        RedisConnection conn = null;
        try {
            conn = redisConnectionFactory.getConnection();
            var policy = conn.getConfig("maxmemory-policy");
            if (policy != null) {
                settings.setRedisPolicy(policy.toString());
            }
            var maxMemory = conn.getConfig("maxmemory");
            if (maxMemory != null) {
                settings.setRedisMaxMemory(maxMemory.toString());
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return settings;
    }
}
