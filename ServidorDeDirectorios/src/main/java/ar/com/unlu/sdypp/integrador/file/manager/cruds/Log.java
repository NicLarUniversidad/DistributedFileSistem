package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="Logger")
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Integer log_id;
    Date logged;
    String level;
    String message;
}