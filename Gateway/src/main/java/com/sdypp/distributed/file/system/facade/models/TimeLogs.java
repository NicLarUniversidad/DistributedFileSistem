package com.sdypp.distributed.file.system.facade.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TimeLogs {
    private String id;
    private String file;
    private Long processTime;
    private Date initTime;
    private Date finishTime;
    private String processType;
    private String sessionId;
}

