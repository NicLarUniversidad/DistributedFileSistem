package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UserCrud {
    @Id
    private String username;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FileCrud> files = new ArrayList<>();
}
