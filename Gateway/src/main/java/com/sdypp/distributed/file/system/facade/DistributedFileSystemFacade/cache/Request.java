package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.cache;

import lombok.Data;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Request")
@Data
public class Request implements Serializable {
    private String id;
    private Cache content;
    private Integer responseCode;
}
