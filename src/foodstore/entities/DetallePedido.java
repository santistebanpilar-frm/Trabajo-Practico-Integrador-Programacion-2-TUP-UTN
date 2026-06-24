/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

/**
 *
 * @author usuario
 */
public class DetallePedido extends Base{
    private Integer cantidad;
    private Double subtotal;
    private Producto producto;
    
    //Sobrecarga
    public DetallePedido() {
        super();
    }

    //crear obj
    public DetallePedido(Integer cantidad, Double subtotal, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }
   
    //obj ya existe
    public DetallePedido(Long id, Integer cantidad, Double subtotal, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }
    
    //Setters
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    //Getters
    public Integer getCantidad() {
        return cantidad;
    }
    public Double getSubtotal() {
        return subtotal;
    }
    public Producto getProducto() {
        return producto;
    }

    @Override
    public String toString() {
        return "--- DETALLE PEDIDO ---" + "\n" +
               "PRODUCTO: " + producto + "\n" +
               "CANTIDAD: " + cantidad + "\n" +
               "SUBTOTAL: " + subtotal + "\n";
    }
    
    
}
