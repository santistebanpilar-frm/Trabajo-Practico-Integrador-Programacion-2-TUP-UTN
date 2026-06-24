/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import foodstore.enums.Rol;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contraseña;
    private Rol rol;
    private List<Pedido> pedidos = new ArrayList<>();
    
    //Sobrecarga
    public Usuario() {
    }

    //crear obj
    public Usuario(String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    
    //el obj existe
    public Usuario(Long id, String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) {
        super(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    
    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getMail() {
        return mail;
    }
    public String getCelular() {
        return celular;
    }
    public String getContraseña() {
        return contraseña;
    }
    public Rol getRol() {
        return rol;
    }
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public String toString() {
        return "--- USUARIO ---" + "\n" +
               "NOMBRE: " + nombre + "\n" +
               "APELLIDO: " + apellido + "\n" +
               "MAIL: " + mail + "\n" +
               "CELULAR: " + celular + "\n" +
               "CONTRASEÑA: " + contraseña + "\n" +
               "ROL: " + rol + "\n";
    }
    
    
}
