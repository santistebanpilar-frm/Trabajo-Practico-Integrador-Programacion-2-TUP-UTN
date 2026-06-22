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
    private Double subTotal;
    private Producto producto;

    public DetallePedido() {
        super();
    }

    public DetallePedido(Producto producto, Integer cantidad, Double subTotal) {
        super();
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.producto = producto;
    }

    public DetallePedido( Long id, Producto producto, Integer cantidad, Double subTotal) {
        super(id);
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.producto = producto;
    }
    
    public Double calcularSubTotal(){
        if(producto != null && producto.getPrecio() != null){
            return cantidad * producto.getPrecio();
        }
        return 0.0;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
    

    public Integer getCantidad() {
        return cantidad;
    }

    public Double getSubTotal() {
        return calcularSubTotal();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    @Override
    public String toString() {
        return "DetallePedido{" + 
                "id=" + getId() +
                "cantidad=" + cantidad + 
                ", producto=" + producto.getNombre() +
                "subtotal=" + subTotal + '}';
    }
}
