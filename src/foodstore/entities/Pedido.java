/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import foodstore.dao.Calculable;
import foodstore.enums.Estado;
import foodstore.enums.FormaPago;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class Pedido extends Base implements Calculable{
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles = new ArrayList();
    private Usuario usuario;

    public Pedido() {
        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
    }

    public Pedido(Usuario usuario, LocalDate fecha, Estado estado, FormaPago formaPago) {
        super();
        this.fecha = fecha;
        this.estado = Estado.PENDIENTE;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.total = 0.0;
    }

    public Pedido( Long id,Usuario usuario, LocalDate fecha, Estado estado, Double total, FormaPago formaPago) {
        super(id);
        this.fecha = fecha;
        this.estado = Estado.PENDIENTE;
        this.total = total;
        this.formaPago = formaPago;
        this.usuario = usuario;
    }
    

    
    @Override
    public void calcularTotal() {
         this.total = detalles.stream()
        .mapToDouble(d -> d.getSubTotal())
        .sum();

    }
    
    public void addDetallePedido(Producto producto, Integer cantidad){
        DetallePedido dp = new DetallePedido(producto, cantidad);
        detalles.add(dp);
        calcularTotal();
    }
    
    public DetallePedido findDetallePedidoByProducto(Producto producto){
        return  detalles.stream()
                .filter(dp -> dp.getProducto().equals(producto))
                .findFirst()
                .orElse(null);
    }
    
    public void deleteDetallePedidoByProducto(Producto producto){
        DetallePedido dp = findDetallePedidoByProducto(producto);
        if(dp != null){
            detalles.remove(dp);
        }
        calcularTotal();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
    
    

    public LocalDate getFecha() {
        return fecha;
    }

    

    public Estado getEstado() {
        return estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    @Override
    public String toString() {
        return "Pedido{" + 
                "id=" + getId() +
                "fecha=" + fecha + 
                ", estado=" + estado + 
                ", total=" + total + 
                ", formaPago=" + formaPago + 
                ", cantidadDetalles=" + detalles.size() + 
                ", usuario=" + usuario.getNombre() + '}';
    }
}
