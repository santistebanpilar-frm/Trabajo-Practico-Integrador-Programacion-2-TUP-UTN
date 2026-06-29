/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exception.PersistenciaException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pilarsg 
 * 
 */
public class PedidoDAO implements IBaseDAO<Pedido> {

    @Override
    public Pedido crear(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedido "+ "(fecha, estado, total, forma_pago, eliminado, created_at, id_usuario) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
 
        String sqlDetalle = "INSERT INTO detalle_pedido "+ "(cantidad, subtotal, eliminado, created_at, id_pedido, id_producto) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection con = null;
        try {
            con = (Connection) ConexionDB.getInstance().getConnection();
            con.setAutoCommit(false);
            
            long pedidoId;
            try (PreparedStatement ps = con.prepareStatement(sqlPedido,Statement.RETURN_GENERATED_KEYS)){
                ps.setDate(1, Date.valueOf(pedido.getFecha()));
                ps.setString(2, pedido.getEstado().name());
                ps.setObject(3, pedido.getTotal());
                ps.setString(4, pedido.getFormaPago().name());
                ps.setObject(5, pedido.isEliminado());
                ps.setObject(6, pedido.getCreatedAt());
                ps.setLong(7, pedido.getUsuario().getId());
                
                ps.executeUpdate();
                
                try(ResultSet claves = ps.getGeneratedKeys()){
                    if(claves.next()){
                        pedidoId = claves.getLong(1);
                        pedido.setId(pedidoId);
                    } else {
                        throw new PersistenciaException("NO se obtuvo ID del PEDIDO ", null);
                    }
                }
            }
            
            for(DetallePedido detalle : pedido.getDetalles()){
                try(PreparedStatement ps = con.prepareStatement(sqlDetalle, Statement.RETURN_GENERATED_KEYS)){
                    ps.setObject(1, detalle.getCantidad());
                    ps.setObject(2, detalle.getSubTotal());
                    ps.setObject(3, detalle.isEliminado());
                    ps.setObject(4, detalle.getCreatedAt());
                    ps.setLong(5, pedidoId);
                    ps.setLong(6, detalle.getProducto().getId());
                    
                    ps.executeUpdate();
                    
                    try(ResultSet claves = ps.getGeneratedKeys()){
                        if (claves.next()){
                            detalle.setId(claves.getLong(1));
                        }
                    }
                }
            }
            
            con.commit();
            return pedido;
            
        } catch (SQLException e){
            if(con != null){
                try{
                    con.rollback();
                } catch (SQLException ea){
                    throw new PersistenciaException("Error al hacer ROLLBACK del pedido", ea);
                }
            }
            throw new PersistenciaException("Error al CREAR PEDIDO",e);
        } finally {
            if (con !=null){
                try{
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException eo){
                    throw new PersistenciaException("Erroa al CERRAR CONEXION del pedido",eo);
                }
            }
        }
    }

    
    public List<Pedido> listarPorUsuario(Long usuarioId) {
        String sql = "SELECT p.id, p.fecha, p.estado, p.total, p.forma_pago, p.eliminado, p.created_at, p.id_usuario, "
                + "u.nombre AS usuario_nombre, u.apellido AS usuario_apellido, u.mail AS usuario_mail "
                + "FROM pedido p "
                + "JOIN usuario u ON p.id_usuario = u.id "
                + "WHERE p.eliminado = false AND p.id_usuario = ? "
                + "ORDER BY p.fecha DESC";
 
        List<Pedido> resultado = new ArrayList<>();
 
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setLong(1, usuarioId);
 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = mapearPedido(rs);
                    pedido.setDetalles(buscarDetallesPorPedido(pedido.getId()));
                    resultado.add(pedido);
                }
            }
            return resultado;
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al LISTAR PEDIDOS por usuario", e);
        }
    }

    @Override
    public boolean actualizar(Pedido pedido) {
        String sql = "UPDATE pedido SET estado = ?, forma_pago = ?, total = ? WHERE id = ?";
 
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, pedido.getEstado().name());
            ps.setString(2, pedido.getFormaPago().name());
            ps.setObject(3, pedido.getTotal());
            ps.setLong(4, pedido.getId());
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al ACTUALIZAR el PEDIDO con ID="+ pedido.getId(), e);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        String sqlDetalles = "UPDATE detalle_pedido SET eliminado = true WHERE id_pedido = ?";
        String sqlPedido   = "UPDATE pedido SET eliminado = true WHERE id = ?";
 
        Connection con = null;
        try {
            con = ConexionDB.getInstance().getConnection();
            con.setAutoCommit(false);
 
            try (PreparedStatement ps1 = con.prepareStatement(sqlDetalles)) {
                ps1.setLong(1, id);
                ps1.executeUpdate();
            }
 
            try (PreparedStatement ps2 = con.prepareStatement(sqlPedido)) {
                ps2.setLong(1, id);
                ps2.executeUpdate();
            }
 
            con.commit();
            return true;
 
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ea) {
                    throw new PersistenciaException("Error al hacer ROLLBACK al eliminar pedido", ea);
                }
            }
            throw new PersistenciaException("Error al ELIMINAR PEDIDO con ID=" + id, e);
 
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException eo) {
                    throw new PersistenciaException("Error al CERRAR CONEXION", eo);
                }
            }
        }
    }
    
    private Pedido mapearPedido(ResultSet rs) throws SQLException{
        Usuario u = new Usuario();
        u.setId(rs.getObject("id_usuario", Long.class));
        u.setNombre(rs.getString("usuario_nombre"));
        u.setApellido(rs.getString("usuario_apellido"));
        u.setMail(rs.getString("usuario_mail"));
 
        Pedido p = new Pedido();
        p.setId(rs.getObject("id", Long.class));
        p.setFecha(rs.getDate("fecha").toLocalDate());
        p.setEstado(Estado.valueOf(rs.getString("estado")));
        p.setTotal(rs.getObject("total", Double.class));
        p.setFormaPago(FormaPago.valueOf(rs.getString("forma_pago")));
        p.setEliminado(rs.getObject("eliminado", Boolean.class));
        p.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
        p.setUsuario(u);
        return p;
    }

    @Override
    public Pedido buscarPorId(Long id) {
        String sql = "SELECT p.id, p.fecha, p.estado, p.total, p.forma_pago, p.eliminado, p.created_at, p.usuario_id, "
                + "u.nombre AS usuario_nombre, u.apellido AS usuario_apellido, u.mail AS usuario_mail "
                + "FROM pedido p "
                + "JOIN usuario u ON p.id_usuario = u.id "
                + "WHERE p.id = ?";
 
        try (Connection con = ConexionDB.getInstance().getConnection() ;PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setLong(1, id);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = mapearPedido(rs);
                    pedido.setDetalles(buscarDetallesPorPedido(pedido.getId()));
                    return pedido;
                }
                return null;
            }
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al BUSCAR el PEDIDO con ID=" + id, e);
        }
    }

    @Override
    public List<Pedido> listar() {
        String sql = "SELECT p.id, p.fecha, p.estado, p.total, p.forma_pago, p.eliminado, p.created_at, p.id_usuario, "
                + "u.nombre AS usuario_nombre, u.apellido AS usuario_apellido, u.mail AS usuario_mail "
                + "FROM pedido p "
                + "JOIN usuario u ON p.id_usuario = u.id "
                + "WHERE p.eliminado = false "
                + "ORDER BY p.fecha DESC";
 
        List<Pedido> resultado = new ArrayList<>();
 
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                Pedido pedido = mapearPedido(rs);
                pedido.setDetalles(buscarDetallesPorPedido(pedido.getId()));
                resultado.add(pedido);
            }
            return resultado;
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al LISTAR PEDIDOS", e);
        }
    }
    
    private List<DetallePedido> buscarDetallesPorPedido(Long pedidoId) throws SQLException {
        String sql = "SELECT dp.id, dp.cantidad, dp.sub_total, dp.eliminado, dp.created_at, dp.id_producto, "
                + "pr.nombre AS producto_nombre, pr.precio AS producto_precio, pr.disponible AS producto_disponible, pr.stock AS producto_stock "
                + "FROM detalle_pedido dp "
                + "JOIN producto pr ON dp.id_producto = pr.id "
                + "WHERE dp.id_pedido = ? AND dp.eliminado = false";
 
        List<DetallePedido> detalles = new ArrayList<>();
 
        try (Connection con = ConexionDB.getInstance().getConnection() ;PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setLong(1, pedidoId);
 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto prod = new Producto();
                    prod.setId(rs.getObject("id_producto", Long.class));
                    prod.setNombre(rs.getString("producto_nombre"));
                    prod.setPrecio(rs.getObject("producto_precio", Double.class));
                    prod.setDisponible(rs.getObject("producto_disponible", Boolean.class));
                    prod.setStock(rs.getObject("producto_stock", Integer.class));
 
                    DetallePedido d = new DetallePedido();
                    d.setId(rs.getObject("id", Long.class));
                    d.setCantidad(rs.getObject("cantidad", Integer.class));
                    d.setSubTotal(rs.getObject("subtotal", Double.class));
                    d.setEliminado(rs.getObject("eliminado", Boolean.class));
                    d.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
                    d.setProducto(prod);
 
                    detalles.add(d);
                }
            }
        }
        return detalles;
    }
    
}
