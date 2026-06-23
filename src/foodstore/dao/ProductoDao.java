/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.entities.Categoria;
import foodstore.entities.Producto;
import foodstore.exception.ValidacionException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class ProductoDao implements IBaseDAO<Producto> {
    private Connection conn;

    public ProductoDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Producto crear(Producto producto) throws SQLException {
        String sql = "INSERT INTO producto (nombre, descripcion, precio, stock, imagen, disponible, id_categoria, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, false)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setString(5, producto.getImagen());
            ps.setBoolean(6, producto.isDisponible());
            ps.setLong(7, producto.getCategoria().getId());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    producto.setId(rs.getLong(1));
                }
            }
            return producto;
        } catch (SQLException e) {
            throw new SQLException("Error al crear producto", e);
        }
    }

    @Override
    public Optional<Producto> leer(Long id) throws SQLException {
        String sql = "SELECT * FROM producto WHERE id = ? AND eliminado = false";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearFila(rs));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al leer producto", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE eliminado = false ORDER BY nombre ASC";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar productos", e);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Producto producto) throws SQLException{
        String sql = "UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, stock = ?, imagen = ?, disponible = ?, id_categoria = ? WHERE id = ? AND eliminado = false";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setString(5, producto.getImagen());
            ps.setBoolean(6, producto.isDisponible());
            ps.setLong(7, producto.getCategoria().getId());
            ps.setLong(8, producto.getId());
            int filas = ps.executeUpdate();
            return filas == 1;
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar producto", e);
        }
    }

    @Override
    public boolean eliminar(Long id) throws SQLException {
        String sql = "UPDATE producto SET eliminado = true WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int filas = ps.executeUpdate();
            return filas == 1;
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar producto", e);
        }
    }

    private Producto mapearFila(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setStock(rs.getInt("stock"));
        producto.setImagen(rs.getString("imagen"));
        producto.setDisponible(rs.getBoolean("disponible"));
        producto.setEliminado(rs.getBoolean("eliminado"));

        // Relación con categoría (solo id, luego se puede enriquecer con CategoriaDao)
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id_categoria"));
        producto.setCategoria(categoria);

        return producto;
    }
}

