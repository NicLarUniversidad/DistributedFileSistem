@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("archivo");
    }

    @Cacheable("archivo")
    public Optional<File> getFileById(Long id) {
        return repository.findById(id);
    }
}