package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.servers.LoadBalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileRepository {

    @Autowired
    private LoadBalancerService loadBalancerService;

    public void save(MultipartFile file, String fileName) {
        String url = loadBalancerService.getServerUrl();
    }
}
