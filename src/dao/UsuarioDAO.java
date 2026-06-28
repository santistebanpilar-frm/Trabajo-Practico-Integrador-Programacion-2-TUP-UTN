/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.Usuario;
import enums.Rol;
import java.util.List;
import exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author pilarsg
 */
public class UsuarioDAO implements IBaseDAO<Usuario>{

    @Override
    public Usuario crear(Usuario usuario) {
    String sql = "INSERT INTO usuario "+ "(nombre, apellido, mail, celular, contraseña, rol, eliminado, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
 
        try (Connection con = ConexionDB.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContraseña());
            // enum se guarda como string para que coincida con el enum de mysql
            ps.setString(6, usuario.getRol() != null ? usuario.getRol().name() : Rol.USUARIO.name());
            ps.setObject(7, usuario.isEliminado());
            ps.setObject(8, usuario.getCreatedAt());
 
            ps.executeUpdate();
 
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    usuario.setId(claves.getLong(1));
                }
            }
            return usuario;
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al CREAR el USUARIO", e);
        }
    }

    @Override
    public Usuario buscarPorId(Long id) {
        String sql = "SELECT id, nombre, apellido, mail, celular, contraseña, "
                + "rol, eliminado, created_at FROM usuario WHERE id = ?";
 
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setLong(1, id);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
                return null;
            }
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al BUSCAR el USUARIO con ID=" + id, e);
        }
    }
    
    public Usuario buscarPorMail(String mail) {
        String sql = "SELECT id, nombre, apellido, mail, celular, contraseña, "
                + "rol, eliminado, created_at FROM usuario WHERE mail = ? AND eliminado = false";
 
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, mail);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
                return null;
            }
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al BUSCAR USUARIO por MAIL", e);
        }
    }

    @Override
    public List<Usuario> listar() {
        String sql = "SELECT id, nombre, apellido, mail, celular, contraseña, rol, eliminado, created_at"
                +"FROM usuario WHERE eliminado = false "
                + "ORDER BY apellido, nombre";
 
        List<Usuario> resultado = new ArrayList<>();
 
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql);ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                resultado.add(mapearUsuario(rs));
            }
            return resultado;
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al LISTAR USUARIOS", e);
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
    String sql = "UPDATE usuario SET nombre = ?, apellido = ?, mail = ?, celular = ?, contraseña = ?, rol = ?"
            +" WHERE id = ?";
 
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getMail());
            ps.setString(4, usuario.getCelular());
            ps.setString(5, usuario.getContraseña());
            ps.setString(6, usuario.getRol() != null ? usuario.getRol().name() : Rol.USUARIO.name());
            ps.setLong(7, usuario.getId());
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al ACTUALIZAR el USUARIO con ID="+ usuario.getId(), e);
        }
    }
 
    @Override
    public boolean eliminar(Long id) {
        String sql = "UPDATE usuario SET eliminado = true WHERE id = ?";
 
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar el usuario id=" + id, e);
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getObject("id", Long.class));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setMail(rs.getString("mail"));
        u.setCelular(rs.getString("celular"));
        u.setContraseña(rs.getString("contraseña"));
        //Aca el string de enum del myysql se convierte nuevamente en enum de java
        String rolStr = rs.getString("rol");
        u.setRol(rolStr != null ? Rol.valueOf(rolStr) : null);
        u.setEliminado(rs.getObject("eliminado", Boolean.class));
        u.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
        return u;
    }
    
}
