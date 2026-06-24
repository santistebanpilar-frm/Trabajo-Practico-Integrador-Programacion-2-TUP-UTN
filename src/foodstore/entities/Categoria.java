/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private List<Producto> productos = new ArrayList<>(); //Agregacion
    
    //Sobrecarga
    public Categoria() {
        super();
    }

    //Crear un nuevo obj
    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //El obj ya existe
    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        return "--- CATEGORIA ---" + "\n" +
               "NOMBRE: " + nombre + "\n" +
               "DESCRIPCION: " + descripcion + "\n" +
               "CANTIDAD DE PRODUCTOS: " + productos.size() + "\n";
    }
    

}
