package entities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author pilarsg
 */
public class DetallePedido extends Base{
    private Integer cantidad;
    private Double subTotal;
    private Producto producto;

    public DetallePedido() {
        super();
    }

    public DetallePedido(Integer cantidad, Producto producto) {
        super();
        if(!validar(producto)){
            throw new IllegalArgumentException("Producto invalido/No disponible");
        };
        if(cantidad == null || cantidad  <= 0 ){
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0(cero)");
        };
        this.cantidad = cantidad;
        this.producto = producto;
        calcularSubtotal();
        
    }
  
    public void calcularSubtotal(){
        if (cantidad !=null && producto != null & producto.getPrecio()!=null){
            this.subTotal =cantidad * producto.getPrecio();
        } else {
            this.subTotal = 0.0;
        }
    }
         
    public boolean validar(Producto p){
        return p != null && Boolean.TRUE.equals(p.isDisponible());
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "DetallePedido \n" + 
               "Cantidad=" + cantidad + "\n"
                +"SubTotal=" + subTotal + "\n"
                + "Producto=" + producto;
    }
    
    
        
}


