package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDataRepository extends CrudRepository <FileCrud,Integer>{
    Page<FileCrud> findAllByUserUsernameAndActivo(String username, Boolean activo, Pageable pageable);
}
