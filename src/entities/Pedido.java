package entities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import enums.Estado;
import enums.FormaPago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author pilarsg
 */
public class Pedido extends Base implements Calculable{
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles = new ArrayList<>();

    public Pedido() {
        super();
    }

    public Pedido(Usuario usuario,FormaPago formaPago) {
        super();
        if(usuario == null){
            throw new IllegalArgumentException("El pedido debe tener un usuario asociado existente");
        };
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        this.usuario = usuario;
        this.formaPago = formaPago;
    }
    @Override
    public void calcularTotal(){
        double suma=0;
        for (DetallePedido d : detalles){
            if(d.getSubTotal() !=null){
            suma += d.getSubTotal();
            }
        }
        this.total = suma;
    }
    
    public void addDetallePedido(int cantidad,Producto prod){
        DetallePedido d = new DetallePedido(cantidad,prod);
        detalles.add(d);
        this.estado = estado.PENDIENTE;
        calcularTotal();
    }
    
    public DetallePedido findeDetallePedidoByProducto(Producto prod){
        for (DetallePedido d : detalles){
            if(prod.equals(d.getProducto())){
                System.out.println("Su pedido es "+prod);
                return d;
            }
        }
        return null;
    }
    
    public void deleteDetallePedidoByProducto(Producto prod){
        DetallePedido pedidoEncontrado = findeDetallePedidoByProducto(prod);
        if (pedidoEncontrado != null){
            detalles.remove(pedidoEncontrado);
            this.estado = Estado.CANCELADO;
            calcularTotal();
        }
    }
    
    public void validarPedido(){
        this.estado = Estado.CONFIRMADO;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
    @Override
    public String toString() {
        return "PEDIDO #"+ getId() + "\n"
                + "Fecha=" + fecha + "\n"
                + "Estado=" + estado +"\n"
                + "Total=" + total + "\n"
                + "Forma de Pago=" + formaPago + "\n"
                + "Usuario=" + (usuario != null ? usuario.getId() : null) +"\n"
                + "Detalles=" + detalles;
    }


}
