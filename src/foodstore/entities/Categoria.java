/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author usuario
 */
public class Categoria extends Base{
    private String nombre;
    private String descripcion;

    public Categoria( Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    

    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Categoria() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public int hashCode() {
        //Mismo campo que en el equals para matener el contrato
        return Objects.hash(nombre);
    }

    @Override
    public boolean equals(Object obj) {
        // Optimizacion: misma referencia en memoria -> iguales.
        if (this == obj) return true;
        // null check y verificacion de tipo
        if(!(obj instanceof Categoria)) return false;
        
        // Cast seguro y comparacion por campo significativo
        Categoria c = (Categoria) obj;
        return Objects.equals(nombre, c.nombre);
    }

    @Override
    public String toString() {
        return "Categoria{" + 
                "id=" + getId() +
                ", nombre=" + nombre + 
                ", descripcion=" + descripcion + '}';
    }

    
}
