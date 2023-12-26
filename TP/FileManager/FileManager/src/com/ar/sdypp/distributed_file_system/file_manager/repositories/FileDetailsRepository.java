package com.ar.sdypp.distributed_file_system.file_manager.repositories;

import com.ar.sdypp.distributed_file_system.file_manager.entities.FileDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDetailsRepository extends CrudRepository<FileDetailsEntity, String> {
    List<FileDetailsEntity> findAllByUsername(String username);
}
