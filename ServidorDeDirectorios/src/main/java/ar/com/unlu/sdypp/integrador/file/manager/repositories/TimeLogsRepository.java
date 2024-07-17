package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.TimeLogs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeLogsRepository extends CrudRepository<TimeLogs, String> {
    List<TimeLogs> findByFile(String file);
    List<TimeLogs> findBySessionIdAndFileNot(String session, String file);
}
