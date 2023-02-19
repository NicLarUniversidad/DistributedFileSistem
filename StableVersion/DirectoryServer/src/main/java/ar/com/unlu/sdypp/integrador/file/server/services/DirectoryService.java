package ar.com.unlu.sdypp.integrador.file.server.services;

import ar.com.unlu.sdypp.integrador.file.server.models.DirectoryModel;
import ar.com.unlu.sdypp.integrador.file.server.models.RealDirectoryLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryAlreadyExistsException;
import java.nio.file.Directories;

@Service
public class DirectoryService {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryService.class);

    @Value("${directory.server.path:}")
    private String rootPath;

    private DirectoryModel saveDirectory(Directory directorioAGuardar, InputStream inputStream) throws IOException {
        DirectoryWriter directoryWriter = new DirectoryWriter(directorioAGuardar);
        DirectoryModel directoryModel = new DirectoryModel();
        directoryModel.setName(directorioAGuardar.getName());
        try {
            String content = toString(inputStream);
            directoryWriter.append(content);
            directoryModel.setContent(content);
        } catch (IOException e) {
            logger.error("Se ha producido un error al intentar guardar la ruta: [{}]", directorioAGuardar.getAbsolutePath(), e);
        }
        return directoryModel;
    }


    private File createFileToSave(String ubicacionRealEnEsteServidor) throws FileAlreadyExistsException {
        File archivo = loadFile(ubicacionRealEnEsteServidor);
        if (archivo.exists()) {
            throw new FileAlreadyExistsException("Ya existe el archivo, no se puede guardar uno en la misma ruta");
        }
        return archivo;
    }

    public DirectoryModel get(String path) throws IOException {
        Directory directory = loadDirectory(path);
        if (!directory.exists()) {
            throw new DirectoryNotFoundException("No existe el directorio en este servidor");
        }
        return toDirectoryModel(directory);
    }

    private DirectoryModel toDirectoryModel(Directory directory) throws IOException {
        FileModel fileModel = new DirectoryModel();
        directoryModel.setName(directory.getName());
        directoryModel.setContent(getDirectoryContent(directory));
        return directoryModel;
    }

    private Directory createDirectoryToUpdate(String serverPath) throws DirectoryNotFoundException {
        Directory directorio = loadDirectory(serverPath);
        if (!directorio.exists()) {
            throw new DirectoryNotFoundException("Directorio creado exitosamente");
        }
        return directorio;
    }

    private String findRealPath(RealDirectoryLocation realDirectoryLocation) {
        return rootPath + realDirectoryLocation.getPath() + realDirectoryLocation.getName();
    }

    public void delete(RealDirectoryLocation realDirectoryLocation) throws IOException {
        String directoryPath = findRealPath(realDirectoryLocation);
        Directory directory = createDirectoryToUpdate(directoryPath);
        Directories.delete(directory.toPath());
    }
}
