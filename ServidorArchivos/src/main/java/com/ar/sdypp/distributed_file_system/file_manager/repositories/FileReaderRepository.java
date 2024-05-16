package com.ar.sdypp.distributed_file_system.file_manager.repositories;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class FileReaderRepository {

    public String getFileContentById(String fileId) throws IOException {
        var content = StreamUtils.copyToString( new ClassPathResource(fileId).getInputStream(), Charset.defaultCharset()  );
        //String plainText = textEncryptor.decrypt(content);
        return content;
    }
}
