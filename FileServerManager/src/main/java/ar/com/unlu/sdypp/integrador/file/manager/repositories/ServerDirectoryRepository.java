package ar.com.file.system.servidorarchivo.demo.repositories;

import ar.com.file.system.servidorarchivo.demo.cruds.ServerDirectory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerDirectoryRepository extends CrudRepository<ServerDirectory, Integer> {
}
