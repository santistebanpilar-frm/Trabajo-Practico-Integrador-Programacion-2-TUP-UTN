/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import dao.PedidoDAO;
import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exception.EntityNotFoundException;

/**
 *
 * @author pilarsg
 */
public class PedidoService extends GenericService<Pedido>  {
    
    private final PedidoDAO pedidoDAO;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    public PedidoService(PedidoDAO pedidoDAO, UsuarioService usuarioService, ProductoService productoService) {
        super(pedidoDAO);
        this.pedidoDAO = pedidoDAO;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }
    
    @Override
    public Pedido buscarPorId(Long id){
        Pedido pedido= pedidoDAO.buscarPorId(id);
        if (pedido == null || Boolean.TRUE.equals(pedido.isEliminado())){
            throw new EntityNotFoundException("NO EXISTE UN PEDIDO CON ID"+id);
        }
        return pedido;
    }

    public Pedido iniciarPedido(Long usuarioId, FormaPago formaPago) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        if (formaPago == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar una forma de pago");
        }
        return new Pedido(usuario, formaPago);
    }
 
    public void agregarDetalle(Pedido pedido, Long productoId, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a 0");
        }
 
        Producto producto = productoService.buscarPorId(productoId);
 
        if (!Boolean.TRUE.equals(producto.isDisponible())) {
            throw new IllegalArgumentException(
                    "El producto '" + producto.getNombre().toUpperCase() + "' NO está disponible");
        }
        if (!producto.validarVenta(cantidad)) {
            throw new IllegalArgumentException(
                    "Stock INSUFICIENTE. Stock actual: " + producto.getStock()
                    + ", cantidad pedida: " + cantidad);
        }
        pedido.addDetallePedido(cantidad, producto);
    }

    public Pedido confirmarPedido(Pedido pedido) {
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            throw new IllegalArgumentException(
                    "El pedido debe tener al menos un detalle");
        }
        pedido.calcularTotal();
        pedido.validarPedido();
        return pedidoDAO.crear(pedido);
    }
 
    public java.util.List<Pedido> listarPorUsuario(Long usuarioId) {
        usuarioService.buscarPorId(usuarioId);
        return pedidoDAO.listarPorUsuario(usuarioId);
    }
 
    public boolean actualizarEstado(Long id, Estado nuevoEstado,FormaPago nuevaFormaPago) {
        Pedido pedido = buscarPorId(id);
 
        if (nuevoEstado != null) {
            pedido.setEstado(nuevoEstado);
        }
        if (nuevaFormaPago != null) {
            pedido.setFormaPago(nuevaFormaPago);
        }
 
        boolean actualizado = pedidoDAO.actualizar(pedido);
        if (!actualizado) {
            throw new EntityNotFoundException(
                    "NO se pudo ACTUALIZAR el PEDIDO con ID " + id);
        }
        return actualizado;
    }
 
    public DetallePedido buscarDetallePorProducto(Pedido pedido, Long productoId) {
        Producto producto = productoService.buscarPorId(productoId);
        DetallePedido detalle = pedido.findeDetallePedidoByProducto(producto);
        if (detalle == null) {
            throw new EntityNotFoundException(
                    "NO se encontró el producto con id " + productoId + " en el pedido");
        }
        return detalle;
    }
 
    public void eliminarDetallePorProducto(Pedido pedido, Long productoId) {
        Producto producto = productoService.buscarPorId(productoId);
        pedido.deleteDetallePedidoByProducto(producto);
    }
    
    
}
