package ar.com.unlu.sdypp.integrador.file.manager.services;

import ar.com.unlu.sdypp.integrador.file.manager.entities.TestEntity;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.TestRepository;
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
