/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

/**
 *
 * @author usuario
 */
public class Producto extends Base{
    private String nombre;
    private Double precio;
    private String descripcion;
    private Integer stock;
    private String imagen;
    private Boolean disponible;
    
    //Sobrecarga
    public Producto() {
    }

    //Crear obj
    public Producto(String nombre, Double precio, String descripcion, Integer stock, String imagen, Boolean disponible) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = disponible;
    }
    
    //El obj existe
    public Producto(Long id, String nombre, Double precio, String descripcion, Integer stock, String imagen, Boolean disponible) {
        super(id);
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = disponible;
    }
   
    
    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
    
    
    //Getters
    public String getNombre() {
        return nombre;
    }
    public Double getPrecio() {
        return precio;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public Integer getStock() {
        return stock;
    }
    public String getImagen() {
        return imagen;
    }
    public Boolean getDisponible() {
        return disponible;
    }

    
    @Override
    public String toString() {
        return "--- PRODUCTO ---" + "\n" +
               "NOMBRE: " + nombre + "\n" +
               "PRECIO: " + precio + "\n" +
               "DESCRIPCION: " + descripcion + "\n" +
               "STOCK: " + stock + "\n" +
               "IMAGEN: " + imagen + "\n" +
               "DISPONIBLE: " + disponible + "\n";
    }
    
    
    
}
