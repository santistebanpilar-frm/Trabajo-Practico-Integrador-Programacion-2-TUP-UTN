/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import java.sql.Connection;
import foodstore.dao.DetallePedidoDao;
import foodstore.dao.IBaseDAO;
import foodstore.dao.PedidoDao;
import foodstore.entities.DetallePedido;
import foodstore.entities.Pedido;
import foodstore.entities.Usuario;
import foodstore.exception.ValidacionException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class PedidoService extends GenericService<Pedido>{
    private IBaseDAO<Usuario> usuarioDao;
    private DetallePedidoService detallePedidoService;
    private Connection conn;
    private PedidoDao pedidoDao;

    public PedidoService(IBaseDAO<Usuario> usuarioDao, DetallePedidoService detallePedidoService, Connection conn, PedidoDao pedidoDao) {
        super(pedidoDao);
        this.usuarioDao = usuarioDao;
        this.detallePedidoService = detallePedidoService;
        this.conn = conn;
        this.pedidoDao = pedidoDao;
    }

    
    public Pedido crearConDetalles(Pedido pedido, List<DetallePedido> detallesSolicitados)throws SQLException{
        if (pedido == null) {
        throw new SQLException("El pedido no puede ser nulo");
    }
    if (pedido.getUsuario() == null || pedido.getUsuario().getId() == null) {
        throw new SQLException("El Usuario es obligatorio");
    }
    Optional<Usuario> usuario = usuarioDao.leer(pedido.getUsuario().getId());
    if (usuario.isEmpty()) {
        throw new SQLException("El Usuario no existe");
    }
    pedido.setUsuario(usuario.get());

    if (detallesSolicitados == null || detallesSolicitados.isEmpty()) {
        throw new SQLException("El pedido debe tener al menos un producto");
    }

    try {
        conn.setAutoCommit(false);
        
        for (DetallePedido detalle : detallesSolicitados) {
            pedido.addDetallePedido(detalle); 
    }

        super.crear(pedido);

        for (DetallePedido detalle : pedido.getDetalles()) {
            detallePedidoService.guardar(detalle, pedido.getId());
        }

        conn.commit();
        return pedido;

    } catch (SQLException e) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new SQLException("Error al revertir la transacción", ex); // era e, debería ser ex
        }
        throw new SQLException("Error al crear el pedido, se revirtió la operación", e);
    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new SQLException("Error al restaurar autoCommit", e);
        }
    }
    }
    @Override
    public Optional<Pedido> leer(Long id) throws SQLException {
        Optional<Pedido> pedido = super.leer(id);
        if(pedido.isEmpty()){
            throw new SQLException("No se encontro el Pedido con ID: " + id);
        }
        return pedido;
    }
    @Override
    public List<Pedido> listar() throws SQLException {
        
        return super.listar();
    }
    
public List<Pedido> listarPorUsuario(Long usuarioId) throws SQLException {
    Optional<Usuario> usuario  = usuarioDao.leer(usuarioId);
    if (usuario.isEmpty()) {
        throw new SQLException("El Usuario no existe o esta eliminada");
    }
    List<Pedido> lista = pedidoDao.listarPorUsuario(usuarioId);
    if (lista.isEmpty()) {
        throw new SQLException("No hay productos para este Usuario");
    }
    return lista;
}
    @Override
    public boolean actualizar(Pedido pedido) throws SQLException {
        leer(pedido.getId());
        if(pedido.getEstado() == null){
            throw new SQLException("El estado del pedido no puede ser nulo");
        }
        if(pedido.getFormaPago() == null){
            throw new SQLException("La descripcion no puede ser nula");
        }
        return super.actualizar(pedido); 
    }

    @Override
    public boolean eliminar(Long id) throws SQLException {
        leer(id);
        return super.eliminar(id); 
    }
    public Pedido modificarDetalle(Long pedidoId, DetallePedido detalleModificado) throws SQLException{
        Pedido pedido = leer(pedidoId).get();
        
        Long detalleId = detalleModificado.getId();
        Integer nuevaCantidad = detalleModificado.getCantidad();

    if (detalleId == null) {
        throw new SQLException("El detalle a modificar debe tener un ID");
    }
        
        try {
            conn.setAutoCommit(false);

            List<DetallePedido> detalles = detallePedidoService.listarPorPedido(pedidoId);
            DetallePedido detalleActual = null;
            for (DetallePedido d : detalles) {
                if (d.getId().equals(detalleId)) {
                detalleActual = d;
                break;
                }
            }
            if (detalleActual == null) {
                throw new SQLException("El detalle no pertenece a este pedido");
            }

            detallePedidoService.modificarCantidad(detalleId, detalleActual, nuevaCantidad);

            List<DetallePedido> detallesActualizados = detallePedidoService.listarPorPedido(pedidoId);
            double nuevoTotal = detallesActualizados.stream()
                .mapToDouble(DetallePedido::getSubTotal)
                .sum();
            pedido.setTotal(nuevoTotal);
            super.actualizar(pedido);

            conn.commit();
            return pedido;

            } catch ( SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new SQLException("Error al revertir la transaccion: ");
            }
            throw new SQLException("Error al modificar el detalle: ");
            } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autoCommit: " + e.getMessage());
            }
        }
    }
    
}
