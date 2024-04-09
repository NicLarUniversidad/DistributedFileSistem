package ar.com.unlu.sdypp.integrador.file.manager.servers;

import org.springframework.stereotype.Service;

@Service
public class LoadBalancerService {

    public String getServerUrl() {
        //TODO: implementar lógica de balanceador de carga
        return "localhost:9000";
    }
}
