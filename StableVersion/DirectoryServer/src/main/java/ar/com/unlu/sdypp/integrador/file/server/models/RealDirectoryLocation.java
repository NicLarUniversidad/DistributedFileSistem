package ar.com.unlu.sdypp.integrador.file.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RealDirectoryLocation {
    private String nameServer;
    private String path;
}
