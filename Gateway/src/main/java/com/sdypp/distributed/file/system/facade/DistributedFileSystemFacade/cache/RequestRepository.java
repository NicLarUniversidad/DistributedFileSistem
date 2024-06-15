package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, String> {
}
