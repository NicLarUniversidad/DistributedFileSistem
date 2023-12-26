package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.controllers;

import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models.FileModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//Acá se definen los recursos o las "URL"
@RestController//Con esto decís que es REST
public class FileController {

    @PostMapping("/upload-file")
    //@RequestBody es para optener datos del cuerpo del request, siempre es un objeto o si no te toma el
    //valor "plano" del cuerpo, por ejemplo un String.
    public void login(@RequestBody FileModel loginModel) {

    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
