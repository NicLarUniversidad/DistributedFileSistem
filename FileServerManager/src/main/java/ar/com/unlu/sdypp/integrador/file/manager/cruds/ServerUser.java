package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name="ServerUser")
@AllArgsConstructor
@NoArgsConstructor
public class ServerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ID;
    String nombre;
}
