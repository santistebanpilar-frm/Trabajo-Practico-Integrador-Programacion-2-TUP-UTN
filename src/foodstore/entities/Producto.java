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
    private boolean disponible;
    private Categoria categoria;

    public Producto(String nombre, Double precio, String descripcion, Integer stock, String imagen) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = stock > 0;
    }

    public Producto(Long id, String nombre, Double precio, String descripcion, Integer stock, String imagen) {
        super(id);
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = stock > 0;
    }
    

    public Producto() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return "Producto{" + 
                "id+" + getId() +
                ", nombre=" + nombre + 
                ", precio=" + precio + 
                ", descripcion=" + descripcion + 
                ", stock=" + stock + 
                ", imagen=" + imagen + 
                ", categoria=" + ((categoria != null )? categoria.getNombre() : "N/A") + '}';
    }
}
