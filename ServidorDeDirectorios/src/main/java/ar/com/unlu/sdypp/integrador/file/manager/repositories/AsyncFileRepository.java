package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.logging.Logger;

@Getter
public class AsyncFileRepository extends Thread {

    private static final Logger logger = Logger.getLogger(AsyncFileRepository.class.getName());

    private final String fileId;
    private final String username;
    private final String host;
    private byte[] fileContent;
    private Date startTime;
    private Date endTime;
    private long processTime;

    public AsyncFileRepository(String fileId, String username, String host) {
        this.fileId = fileId;
        this.username = username;
        this.host = host;
    }

    @Override
    public void run() {
        logger.info("Iniciando descarga... | Hilo: " + Thread.currentThread().getName());
        this.startTime = new Date();
        this.fileContent = this.getFile(fileId, username);
        this.endTime = new Date();
        this.processTime = this.endTime.getTime() - this.startTime.getTime();
        logger.info("Descarga finalizada... Tiempo de descarga: " + processTime + " ms | Hilo: " + Thread.currentThread().getName());
        logger.info("Se descargó el siguiente contenido: " + new String(this.fileContent));
    }

    public byte[] getFile(String fileId, String username) {


        RestTemplate restTemplate = new RestTemplate();

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "/file?username=" +  username + "&id=" + fileId);

        var requestEntity = new HttpEntity<>(map);
        HttpEntity<byte[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                byte[].class);

        return response.getBody();
    }
}
