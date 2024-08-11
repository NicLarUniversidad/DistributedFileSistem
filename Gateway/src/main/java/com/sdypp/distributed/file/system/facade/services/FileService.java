package com.sdypp.distributed.file.system.facade.services;

import com.sdypp.distributed.file.system.facade.models.*;
import com.sdypp.distributed.file.system.facade.repositories.externals.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FilesDetailModel getAllFiles(Pageable pageable) {
        return fileRepository.getAllFiles(getUsername(), pageable);
    }

    private String getUsername() {
        return "test-user";
    }

    public FileModel uploadFile(MultipartFile file, Integer id, Boolean append) throws IOException {
        String currentUser = this.getUsername();
        return this.fileRepository.uploadFile(file, currentUser, id, append);
    }

    public FileModel updateFile(String newText, String fileId) throws IOException {
        String currentUser = this.getUsername();
        return this.fileRepository.updateFile(newText, currentUser, fileId);
    }

    public Resource getFile(String fileId) {
        return this.fileRepository.getFile(fileId);
    }

    public String deleteFile(Integer fileId) {
        return this.fileRepository.deleteFile(fileId);
    }

    public PartModels getFileParts(Integer fileId) {
        return this.fileRepository.getFileParts(fileId);
    }

    public FileLogsModel getFileLogs(Integer fileId) {
        return this.fileRepository.getFileLogs(fileId);
    }

    public FileModel getFileData(Integer fileId) {
        return this.fileRepository.getFileData(fileId);
    }

    public FileModel lockFile(Integer fileId) {
        return this.fileRepository.lockFile(fileId);
    }

    public String deleteLogs(Integer fileId) {
        return this.fileRepository.deleteLogs(fileId);
    }

    public FilePart getFile(Integer fileId, Integer partNumber) {
        return this.fileRepository.getFilePart(fileId, partNumber);
    }
}
