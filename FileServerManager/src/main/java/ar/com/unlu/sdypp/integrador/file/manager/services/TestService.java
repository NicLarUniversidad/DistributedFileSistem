package ar.com.file.system.servidorarchivo.demo.services;

import ar.com.file.system.servidorarchivo.demo.entities.TestEntity;
import ar.com.file.system.servidorarchivo.demo.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public List<TestEntity> findAll() {
        return testRepository.findAll();
    }

    public TestEntity save(TestEntity testEntity) {
        return testRepository.save(testEntity);
    }
}
