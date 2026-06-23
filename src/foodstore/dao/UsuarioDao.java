/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;
import foodstore.entities.Usuario;
import foodstore.enums.Rol;
import foodstore.exception.ValidacionException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class UsuarioDao implements IBaseDAO<Usuario> {
    private Connection conn;

    public UsuarioDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Usuario crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, apellido, mail, celular, contraseña, rol, eliminado) VALUES (?, ?, ?, ?, ?, ?, false)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContrasenia());
            ps.setString(6, usuario.getRol().name());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }
            return usuario;
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate")) {
                throw new SQLException("El mail ya existe en la base de datos", e);
            }
            throw new SQLException("Error al crear usuario", e);
        }
    }

    @Override
    public Optional<Usuario> leer(Long id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ? AND eliminado = false";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearFila(rs));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al leer usuario", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE eliminado = false ORDER BY nombre ASC";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar usuarios", e);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, mail = ?, celular = ?, contraseña = ?, rol = ? WHERE id = ? AND eliminado = false";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContrasenia());
            ps.setString(6, usuario.getRol().name());
            ps.setLong(7, usuario.getId());
            int filas = ps.executeUpdate();
            return filas == 1;
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate")) {
                throw new SQLException("El mail ya existe en la base de datos", e);
            }
            throw new SQLException("Error al actualizar usuario", e);
        }
    }

    @Override
    public boolean eliminar(Long id) throws SQLException {
        String sql = "UPDATE usuario SET eliminado = true WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int filas = ps.executeUpdate();
            return filas == 1;
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar usuario", e);
        }
    }

    private Usuario mapearFila(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setMail(rs.getString("mail"));
        usuario.setCelular(rs.getString("celular"));
        usuario.setContrasenia(rs.getString("contraseña"));
        usuario.setRol(Rol.valueOf(rs.getString("rol")));
        usuario.setEliminado(rs.getBoolean("eliminado"));
        return usuario;
    }
}
