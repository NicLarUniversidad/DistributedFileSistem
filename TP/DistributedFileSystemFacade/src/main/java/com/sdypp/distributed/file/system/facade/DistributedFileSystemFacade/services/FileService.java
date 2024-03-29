package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.services;

import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.entities.UserEntity;
import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models.FileDetailsModel;
import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models.FilesDetailModel;
import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.repositories.externals.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public FileDetailsModel uploadFile(MultipartFile file) throws IOException {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUser;
        if (principal instanceof UserDetails) {
            currentUser = ((UserDetails)principal).getUsername();
        } else {
            currentUser = principal.toString();
        }
        return this.fileRepository.uploadFile(file, currentUser);
    }
}
