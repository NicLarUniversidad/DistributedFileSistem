package com.ar.sdypp.distributed_file_system.file_manager.repositories;

import com.ar.sdypp.distributed_file_system.file_manager.services.LoadBalancerService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Repository
public class FileRepository {

    @Autowired
    private LoadBalancerService loadBalancerService;

    public void save(MultipartFile file, String fileName) throws IOException, URISyntaxException {
        String host = loadBalancerService.getServerUrl();
        URI url = new URI("http", host, "/file", null, null);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        FileBody uploadFilePart = new FileBody(multipartToFile(file, fileName));
        StringBody name = new StringBody(fileName);
        StringBody path = new StringBody("");
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("file", uploadFilePart);
        reqEntity.addPart("name", name);
        reqEntity.addPart("path", path);
        httpPost.setEntity(reqEntity);

        HttpResponse response = httpclient.execute(httpPost);
    }
    private File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}