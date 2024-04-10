package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.servers.LoadBalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileRepository {

    //@Autowired
    private LoadBalancerService loadBalancerService;

    public void save(MultipartFile file, String fileName) {
        String url = loadBalancerService.getServerUrl();
    }
}
