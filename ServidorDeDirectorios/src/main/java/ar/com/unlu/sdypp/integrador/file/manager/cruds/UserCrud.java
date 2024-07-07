package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private List<FileCrud> files = new ArrayList<>();
}
