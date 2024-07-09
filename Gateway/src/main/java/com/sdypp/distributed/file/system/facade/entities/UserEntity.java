package com.sdypp.distributed.file.system.facade.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Cada clase de estas representa y se mapea con una tabla en la base de datos
@Getter//Crea los getters por reflexión (sin programarlos vos xd)
@Setter//Crea los setters por reflexión
@ToString//Overwrite en el método toString con campos
//@Entity(name = "user")//Acá se settea el nombre de la tabla (se crea automáticamente según la configuración)
public class UserEntity {
    //@Id//Si o si hay que definir un ID que sería una PK en la base de datos
    private String username;
    //Cada campo es una columna en la base de datos
    //@Column(name = "password")//Si se requiere, se puede renombrar los campos de la base con esta anotación
    private String password;
}
