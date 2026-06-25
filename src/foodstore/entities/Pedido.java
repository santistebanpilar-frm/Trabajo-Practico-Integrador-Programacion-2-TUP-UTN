/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import foodstore.interfaz.Calculable;
import foodstore.enums.Estado;
import foodstore.enums.FormaPago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles = new ArrayList<>(); //Composicion
    
    //Sobrecarga
    public Pedido() {
        super();
    }

    //crear obj
    public Pedido(FormaPago formaPago) {
        super();
        this.fecha = LocalDate.now();
        this.estado = estado.PENDIENTE;
        this.total = total;
        this.formaPago = formaPago;
    }
    
    //el obj existe
    public Pedido(Long id, FormaPago formaPago) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = estado.PENDIENTE;
        this.total = total;
        this.formaPago = formaPago;
    }
    
    //Setters
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
    
    //Getters
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

    @Override
    public String toString() {
        return "--- PEDIDO ---" + "\n" +
               "FECHA: " + fecha + "\n" +
               "ESTADO: " + estado + "\n" +
               "TOTAL: " + total + "\n" +
               "FORMA DE PAGO" + formaPago + "\n" +
               "DETALLE: " + detalles + "\n";
                
    }

    //Interfaz
    @Override
    public void calcularTotal() {
        
    }
    

}
