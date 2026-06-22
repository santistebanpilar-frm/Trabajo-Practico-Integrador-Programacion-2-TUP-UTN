/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.entities.DetallePedido;
import foodstore.entities.Producto;
import foodstore.exception.DAOException;
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
public class DetallePedidoDao implements IBaseDAO<DetallePedido>{
    private Connection conn;

    public DetallePedidoDao(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public DetallePedido crear(DetallePedido detalle) throws DAOException {
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
            return detalle;
        }catch (SQLException e){
            throw new DAOException("Error al crear item de pedido"+ e);
        }
    }

    @Override
    public Optional<DetallePedido> leer(Long id) throws DAOException {
        String sql = "SELECT * FROM detalle_pedido WHERE id = ? AND eliminado = false";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearFila(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al leer detalle de pedido");
        }
        return Optional.empty();
    }

    @Override
    public List<DetallePedido> listar() throws DAOException {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_pedido WHERE eliminado = false";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar detalles de pedido");
        }
        return lista;
    }

    @Override
    public boolean actualizar(DetallePedido detalle) throws DAOException {
        String sql = "UPDATE detalle_pedido SET cantidad = ?, subtotal = ? WHERE id = ? AND eliminado = false";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getSubTotal());
            ps.setLong(3, detalle.getId());
            int filas = ps.executeUpdate();
            return filas == 1;
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar detalle de pedido");
        }
    }

    @Override
    public boolean eliminar(Long id) throws DAOException {
        String sql = "UPDATE detalle_pedido SET eliminado = true WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int filas = ps.executeUpdate();
            return filas == 1;
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar detalle de pedido");
        }
    }
    private DetallePedido mapearFila(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getLong("id_producto"));

        DetallePedido detalle = new DetallePedido(
        producto,
        rs.getInt("cantidad"),
        rs.getDouble("subtotal")
        );

        detalle.setId(rs.getLong("id"));
        detalle.setEliminado(rs.getBoolean("eliminado"));
        return detalle;
    }
}
