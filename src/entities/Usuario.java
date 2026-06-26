package entities;

import enums.Rol;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author pilarsg
 */
public class Usuario extends Base {
   private String nombre;
   private String apellido;
   private String mail;
   private String celular;
   private String contraseña;
   private Rol rol;
 
    public Usuario() {
        super();
    }
   
    public Usuario(String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (validarStringVacio(nombre)) {
            throw new IllegalArgumentException("El nombre no puede estar vacía");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
       if (validarStringVacio(apellido)) {
            throw new IllegalArgumentException("El apellido no puede estar vacía");
        }
        this.apellido = apellido; 

    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (validarStringVacio(mail)) {
            throw new IllegalArgumentException("El mail no puede estar vacía");
        }
        this.mail = mail;
    
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        if (validarStringVacio(celular)) {
            throw new IllegalArgumentException("El celular no puede estar vacía");
        }
        this.celular = celular; 
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        if (validarStringVacio(contraseña)) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        this.contraseña = contraseña; 

    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    private boolean validarStringVacio (String palabra){
        return palabra ==null  ||  palabra.trim().isEmpty();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof Usuario)) {return false;}
        Usuario that = (Usuario) o;
        return Objects.equals(getId(), that.getId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "----------------Usuario #"+ getId() + "----------------------"
                +"Nombre= "+nombre+"\n"
                +"Apellido= "+apellido +"\n"
                +"Email= "+mail+"\n"
                +"Celular= "+ celular +"\n"
                +"Rol= "+ rol;
    }
   
   
    
    
}

