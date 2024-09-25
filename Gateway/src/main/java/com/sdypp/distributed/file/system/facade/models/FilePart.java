package com.sdypp.distributed.file.system.facade.models;

import lombok.Data;
import org.springframework.core.io.Resource;

import java.io.Serializable;

@Data
public class FilePart implements Serializable {
    private byte[] resource;
    private Integer number;
    private Boolean hasNext;
    private String resourceString;
}
