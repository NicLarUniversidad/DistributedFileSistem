package com.sdypp.distributed.file.system.facade.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;
import org.springframework.core.io.Resource;

@Getter
@AllArgsConstructor
public class FileResource implements Serializable{
    private Resource resource;
}
