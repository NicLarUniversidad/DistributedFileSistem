package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.cache;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.LinkedHashMap;
import java.util.concurrent.Callable;

public class ListCache implements Cache {

    public static Logger logger = LoggerFactory.getLogger(ListCache.class);

    @Getter
    private final String name;
    //private final LinkedList<Pair<String, Object>> items = new LinkedList<>();
    private final LinkedHashMap<String, Object> items = new LinkedHashMap<>();

    public ListCache(String name) {
        this.name = name;
    }

    @Override
    public Object getNativeCache() {
        return null;
    }

    @Override
    public ValueWrapper get(Object key) {
        var value = items.get(key.toString());
        return value==null ? null : new SimpleValueWrapper(value);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        var value = items.get(key.toString());
        return value==null ? null : (T) value;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        var value = items.get(key.toString());
        try {
            return value==null ? valueLoader.call() : (T) value;
        } catch (Exception e) {
            String message = String.format("No se pudo recuperar el valor del cache. Se obtuvo el siguiente error: %s.", e.getLocalizedMessage());
            logger.error(message);
            return null;
        }
    }

    @Override
    public void put(Object key, Object value) {
        this.items.put(key.toString(), value);
    }

    @Override
    public void evict(Object key) {
        this.items.remove(key.toString());
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}
