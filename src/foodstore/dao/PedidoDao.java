/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.entities.Pedido;
import foodstore.entities.Usuario;
import foodstore.enums.Estado;
import foodstore.enums.FormaPago;
import foodstore.exception.DAOException;
import java.util.List;
import java.util.Optional;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class PedidoDao implements IBaseDAO<Pedido>{
    private Connection conn;

    public PedidoDao(Connection conn) {
        this.conn = conn;
    }
    @Override
    public Pedido crear(Pedido pedido) throws DAOException {
        String sql = "INSERT INTO pedido (id_usuario, fecha, estado, forma_pago, total, eliminado) VALUES (?, ?, ?, ?, ?, false)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, pedido.getUsuario().getId());
            ps.setDate(2, java.sql.Date.valueOf(pedido.getFecha()));
            ps.setString(3, pedido.getEstado().name());
            ps.setString(4, pedido.getFormaPago().name());
            ps.setDouble(5, pedido.getTotal());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getLong(1));
                }
            }
            return pedido;
        } catch (SQLException e) {
            throw new DAOException("Error al crear pedido");
        }
    }

    @Override
    public Optional<Pedido> leer(Long id) throws DAOException {
        String sql = "SELECT * FROM pedido WHERE id = ? AND eliminado = false";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearFila(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al leer pedido");
        }
        return Optional.empty();
    }

    @Override
    public List<Pedido> listar() throws DAOException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE eliminado = false ORDER BY fecha DESC";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar pedidos");
        }
        return lista;
    }

    @Override
    public boolean actualizar(Pedido pedido) throws DAOException {
        String sql = "UPDATE pedido SET estado = ?, forma_pago = ?, total = ? WHERE id = ? AND eliminado = false";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pedido.getEstado().name());
            ps.setString(2, pedido.getFormaPago().name());
            ps.setDouble(3, pedido.getTotal());
            ps.setLong(4, pedido.getId());
            int filas = ps.executeUpdate();
            return filas == 1;
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar pedido");
        }
    }

    @Override
    public boolean eliminar(Long id) throws DAOException {
        String sql = "UPDATE pedido SET eliminado = true WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int filas = ps.executeUpdate();
            return filas == 1;
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar pedido");
        }
    }

    private Pedido mapearFila(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getLong("id"));
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));
        pedido.setUsuario(usuario);
        pedido.setFecha(rs.getDate("fecha").toLocalDate());
        pedido.setEstado(Estado.valueOf(rs.getString("estado")));
        pedido.setFormaPago(FormaPago.valueOf(rs.getString("forma_pago")));
        pedido.setTotal(rs.getDouble("total"));
        pedido.setEliminado(rs.getBoolean("eliminado"));
        return pedido;
    }
}
