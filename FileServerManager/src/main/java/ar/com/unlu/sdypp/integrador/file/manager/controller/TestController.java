package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.file.system.servidorarchivo.demo.models.TestModel;
import ar.com.unlu.sdypp.integrador.file.manager.entities.TestEntity;
import ar.com.unlu.sdypp.integrador.file.manager.services.TestService;
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
