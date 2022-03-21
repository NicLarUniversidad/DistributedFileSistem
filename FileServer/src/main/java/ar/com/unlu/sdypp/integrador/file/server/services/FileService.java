package ar.com.unlu.sdypp.integrador.file.server.services;

import ar.com.unlu.sdypp.integrador.file.server.models.FileModel;
import ar.com.unlu.sdypp.integrador.file.server.models.RealFileLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${file.server.path:}")
    private String rootPath;

    public void save(MultipartFile archivoTalComoSeRecibio, RealFileLocation ubicacionRealDeArchivo) throws IOException {
        String ubicacionRealEnEsteServidor = findRealPath(ubicacionRealDeArchivo);
        File archivoAGuardar = createFileToSave(ubicacionRealEnEsteServidor);
        saveFile(archivoAGuardar, archivoTalComoSeRecibio.getInputStream());
    }

    private FileModel saveFile(File archivoAGuardar, InputStream inputStream) throws IOException {
        FileWriter fileWriter = new FileWriter(archivoAGuardar);
        FileModel fileModel = new FileModel();
        fileModel.setName(archivoAGuardar.getName());
        try {
            String content = toString(inputStream);
            fileWriter.append(content);
            fileModel.setContent(content);
        } catch (IOException e) {
            logger.error("Se produzco un error al intentar escribir el archivo, ruta: [{}]", archivoAGuardar.getAbsolutePath(), e);
        }
        finally {
            fileWriter.close();
        }
        return fileModel;
    }

    private String toString(InputStream inputStream) throws IOException {
        int bufferSize = 1024;
        char[] buffer = new char[bufferSize];
        StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
            out.append(buffer, 0, numRead);
        }
        return out.toString();
    }

    private File createFileToSave(String ubicacionRealEnEsteServidor) throws FileAlreadyExistsException {
        File archivo = loadFile(ubicacionRealEnEsteServidor);
        if (archivo.exists()) {
            throw new FileAlreadyExistsException("Ya existe el archivo, no se puede guardar uno en la misma ruta");
        }
        return archivo;
    }

    public FileModel get(String path) throws IOException {
        File file = loadFile(path);
        if (!file.exists()) {
            throw new FileNotFoundException("No existe el archivo en este servidor");
        }
        return toFileModel(file);
    }

    private FileModel toFileModel(File file) throws IOException {
        FileModel fileModel = new FileModel();
        fileModel.setName(file.getName());
        fileModel.setContent(getFileContent(file));
        return fileModel;
    }

    private String getFileContent(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while (null != (line = bufferedReader.readLine()))
            stringBuilder.append(line);
        bufferedReader.close();
        return stringBuilder.toString();
    }

    private File loadFile(String path) {
        return new File(path);
    }

    public FileModel updateFile(MultipartFile file, RealFileLocation realFileLocation) throws IOException {
        String serverPath = findRealPath(realFileLocation);
        File archivoAGuardar = createFileToUpdate(serverPath);
        return saveFile(archivoAGuardar, file.getInputStream());
    }

    private File createFileToUpdate(String serverPath) throws FileNotFoundException {
        File archivo = loadFile(serverPath);
        if (!archivo.exists()) {
            throw new FileNotFoundException("No existe el archivo, no se puede guardar uno en la misma ruta");
        }
        return archivo;
    }

    private String findRealPath(RealFileLocation realFileLocation) {
        return rootPath + realFileLocation.getPath() + realFileLocation.getName();
    }

    public void delete(RealFileLocation realFileLocation) throws IOException {
        String filePath = findRealPath(realFileLocation);
        File file = createFileToUpdate(filePath);
        Files.delete(file.toPath());
    }
}
