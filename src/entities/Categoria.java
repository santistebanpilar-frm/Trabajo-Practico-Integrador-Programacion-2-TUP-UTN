package entities;


import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author pilarsg
 */
public class Categoria extends Base {
    private String nombre;
    private String descripcion;

    public Categoria() {
        super();
    }

    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    //getters y setters
     public String getNombre() {
        return nombre;
    }
     
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no puede quedar vacio");
        }
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()){
            throw new IllegalArgumentException("La descripcion no puede quedar vacia");
        }
        this.descripcion = descripcion;
    } 
    
        @Override
    public boolean equals(Object o){
        if (this == o) {return true;}
        if (!(o instanceof Categoria)) {return false;}
        Categoria that = (Categoria) o;
        return Objects.equals(getId(), that.getId());
    }
    
    @Override
    public int hashCode(){
    return Objects.hash(getId());
    }


    @Override
    public String toString() {
        return "CATEGORIA" + nombre +" \n" +
                "Descripcion: " + descripcion;
    }
    
    
}

