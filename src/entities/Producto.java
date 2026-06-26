package entities;


import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author pilarsg
 * < >
 */
public class Producto extends Base{
    private String nombre;
    private Double precio;
    private String descripcion;    
    private Integer stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;
    
    public Producto(){
        super();
    }

    public Producto(String nombre, Double precio, String descripcion,Integer stock,String imagen, Categoria categoria) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.categoria = categoria;
        this.disponible = (stock != null);
    }
    
    public void validarDisponibilidad(){
        this.disponible = (stock != null && stock > 0);
    }
    
    public void reducirStock(Integer i){
        if(i == null || i  <= 0){
            return;
        }        
        if (stock !=null && i<= stock){
            stock-=i;
        }
        if(stock !=null && stock == 0){
            this.disponible = Boolean.FALSE;
            System.out.println("Producto agotado!");
        }
    }
    
    public void aumentarStock(Integer i){
        if (i != null && i>0){
            stock =(stock == null ? 0 : stock) + i;
        }
    }
    
    public boolean validarVenta(Integer cantidad){
        if (cantidad == null || cantidad <= 0) {
            return false;
        }
        if (stock == null) {
            return false;
        }
        return cantidad <= stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no puede quedar vacio");
        }
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        if (precio == null || precio < 0){
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }  
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

    public void setStock(Integer stock) {
        if (stock == null || stock < 0){
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
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

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final Producto other = (Producto) obj;
        return Objects.equals(getId(), other.getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    

    @Override
    public String toString() {
        return "----------------Producto #"+ getId() +"----------------"+
                "Nombre=" + nombre + "\n"
                + "Precio=" + precio + "\n"
                + "Descripcion=" + descripcion + "\n"
                + "Stock=" + stock + "\n"
                + "Disponible=" + disponible + "\n";
    }


}