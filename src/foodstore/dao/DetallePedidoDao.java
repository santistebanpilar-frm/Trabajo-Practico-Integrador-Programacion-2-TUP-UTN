/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.entities.DetallePedido;
import foodstore.entities.Producto;
import foodstore.exception.ValidacionException;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class DetallePedidoDao{
    private Connection conn;

    public DetallePedidoDao(Connection conn) {
        this.conn = conn;
    }
    
    public void crear(DetallePedido detalle, Long pedidoId) throws SQLException {
        String sql = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?);";
        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setLong(1, pedidoId);
            ps.setLong(2, detalle.getProducto().getId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getSubTotal());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    detalle.setId(rs.getLong(1));
                }
            }
        }  
    }
    public List<DetallePedido> listarPorPedido(Long pedidoId) throws SQLException {
        String sql = "SELECT dp.id, dp.cantidad, dp.subtotal, p.id AS id_producto, p.nombre, p.precio, p.stock FROM detalle_pedido dp JOIN producto p ON p.id = dp.id_producto WHERE dp.id_pedido = ?";
        List<DetallePedido> detalles = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, pedidoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getLong("id_producto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setStock(rs.getInt("stock"));

                    DetallePedido detalle = new DetallePedido(producto, rs.getInt("cantidad"));
                    detalle.setId(rs.getLong("id"));
                    detalle.setSubTotal(rs.getDouble("subtotal"));

                    detalles.add(detalle);
                }
            }
        }
        return detalles;
    }
    public void eliminarPorPedido(Long pedidoId)throws SQLException{
        String sql = "DELETE FROM detalle_pedido WHERE id_pedido = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, pedidoId);
            ps.executeUpdate();
        }
    }
    public void actualizar(DetallePedido detalle) throws SQLException {
        String sql = "UPDATE detalle_pedido SET cantidad = ?, subtotal = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getSubTotal());
            ps.setLong(3, detalle.getId());
            ps.executeUpdate();
        }
    }
}
