package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.models.DirectoryServerModel;
import ar.com.unlu.sdypp.integrador.file.manager.servers.DirectoryServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@RestController
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryServerController.class);


}