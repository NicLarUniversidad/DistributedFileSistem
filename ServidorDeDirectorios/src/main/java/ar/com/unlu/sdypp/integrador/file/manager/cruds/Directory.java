package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="Directorio")
@AllArgsConstructor
@NoArgsConstructor
public class Directory {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Integer ID;
    String nombreRuta;
    Boolean activo;
}
