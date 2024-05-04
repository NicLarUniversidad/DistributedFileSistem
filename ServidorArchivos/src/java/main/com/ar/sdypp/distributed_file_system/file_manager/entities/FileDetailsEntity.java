package com.ar.sdypp.distributed_file_system.file_manager.entities;

import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "file_details")
public class FileDetailsEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String name;
    private String url;
    private String size;
    private Date uploadedDate;
    private String username;
    @Lob //permite persistir objetos de gran tamaño
    private byte[] data;

}
