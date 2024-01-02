package com.ar.sdypp.distributed_file_system.file_manager.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "file_details")
public class FileDetailsEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String name;
    private String size;
    private Date uploadedDate;
    private String username;
}
