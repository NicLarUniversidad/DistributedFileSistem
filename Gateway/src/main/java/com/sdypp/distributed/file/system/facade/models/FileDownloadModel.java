package com.sdypp.distributed.file.system.facade.models;

import lombok.Data;
import org.springframework.core.io.Resource;

import java.io.Serializable;

@Data
public class FileDownloadModel implements Serializable {
    private Resource resource;
}
