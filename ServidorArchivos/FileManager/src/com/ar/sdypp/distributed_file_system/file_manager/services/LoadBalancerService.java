package com.ar.sdypp.distributed_file_system.file_manager.services;

import org.springframework.stereotype.Service;

@Service
public class LoadBalancerService {

    public String getServerUrl() {
        //TODO: implementar lógica de balanceador de carga
        return "localhost:9000";
    }
}
