package com.sdypp.distributed.file.system.facade.models;

import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class FilePart {
    private byte[] resource;
    private Integer number;
    private Boolean hasNext;
    private String resourceString;
}
