package com.sdypp.distributed.file.system.facade.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@AllArgsConstructor
public class FileResource {
    private Resource resource;
}
