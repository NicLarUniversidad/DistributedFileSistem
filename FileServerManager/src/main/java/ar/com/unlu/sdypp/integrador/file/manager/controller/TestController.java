package ar.com.file.system.servidorarchivo.demo.controller;

import ar.com.file.system.servidorarchivo.demo.entities.TestEntity;
import ar.com.file.system.servidorarchivo.demo.models.TestModel;
import ar.com.file.system.servidorarchivo.demo.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public TestEntity hola(@RequestBody TestEntity testModel) {
        return testService.save(testModel);
    }
}
