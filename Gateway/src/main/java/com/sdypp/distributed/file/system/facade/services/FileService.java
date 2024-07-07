package com.sdypp.distributed.file.system.facade.services;

import com.sdypp.distributed.file.system.facade.models.FileDetailsModel;
import com.sdypp.distributed.file.system.facade.models.FileModel;
import com.sdypp.distributed.file.system.facade.models.FileResource;
import com.sdypp.distributed.file.system.facade.models.FilesDetailModel;
import com.sdypp.distributed.file.system.facade.repositories.externals.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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

    public FilesDetailModel getAllFiles() {
        return fileRepository.getAllFiles(getUsername());
    }

    private String getUsername() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username;
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails)principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
        return "test-user";
    }

    public FileModel uploadFile(MultipartFile file) throws IOException {
        //var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUser = this.getUsername();
//        if (principal instanceof UserDetails) {
//            currentUser = ((UserDetails)principal).getUsername();
//        } else {
//            currentUser = principal.toString();
//        }
        return this.fileRepository.uploadFile(file, currentUser);
    }

    public Resource getFile(String fileId) {
        //return new FileResource(this.fileRepository.getFile(fileId));
        return this.fileRepository.getFile(fileId);
    }
}
