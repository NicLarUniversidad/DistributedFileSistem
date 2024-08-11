package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class FilePart {
    private byte[] resource;
    private Integer number;
    private Boolean hasNext;
}
