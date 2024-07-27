package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Getter
@Setter
@ToString
@Entity(name="Archivo")
public class FileCrud {

    public static final char UNLOCKED = 'u';
    public static final char LOCKED = 'l';

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String nombreArchivo;
    private String nombreRutaDirectorio;
    private String tamaño;
    private Integer tamaño2;
    private String formato;
    private String tipo; //si es de lectura, escritura o ambas
    private Boolean activo;
    private Date lastTimeOpen;
    private char state;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    @ToString.Exclude
    private List<FilePartCrud> parts = new LinkedList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserCrud user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        FileCrud fileCrud = (FileCrud) o;
        return getID() != null && Objects.equals(getID(), fileCrud.getID());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
