package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class TimeLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String file;
    private Long processTime;
    private Date initTime;
    private Date finishTime;
    private String processType;
    private String sessionId;
}
