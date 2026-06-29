/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.config.ConexionDB;
import foodstore.entities.Usuario;
import foodstore.enums.Rol;
import foodstore.exception.BaseDeDatosException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class UsuarioDao implements IBaseDAO<Usuario>{

    @Override
    public Usuario crear(Usuario usuario) throws BaseDeDatosException {
        
        String sql = "INSERT INTO usuario (nombre, apellido, mail, celular, contrasena, rol, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
         try(
             Connection conexion = ConexionDB.getConnection();
             PreparedStatement stat = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ){
                stat.setString(1, usuario.getNombre());
                stat.setString(2, usuario.getApellido());
                stat.setString(3, usuario.getMail());
                stat.setString(4, usuario.getCelular());
                stat.setString(5, usuario.getContrasena());
                stat.setString(6, usuario.getRol().name());
                stat.setBoolean(7, usuario.isEliminado());
                stat.setTimestamp(8, Timestamp.valueOf(usuario.getCreatedAt()));
                stat.executeUpdate();
                
                try(
                    ResultSet rs = stat.getGeneratedKeys()
                ){
                    if (rs.next()){
                        usuario.setId(rs.getLong(1));
                    }
                 }
                return usuario;
             } catch (SQLException e){
                 throw new BaseDeDatosException("ERROR: No se pudo crear el usuario" + e);
             }
    }

    @Override
    public Usuario leerPorId(Long id) throws BaseDeDatosException {
        
        String sql = "SELECT * FROM usuario WHERE id = ? AND eliminado = false";
        
        try (
             Connection conexion = ConexionDB.getConnection();
             PreparedStatement stat = conexion.prepareStatement(sql);
            ){
                stat.setLong(1, id);
                
                try(
                    ResultSet rs = stat.executeQuery();
                   ) {
                    if (rs.next()) {
                        return mapearUsuario(rs);
                    }
                     }
                return null;
             } catch (SQLException e){
                 throw new BaseDeDatosException("ERROR: no se pudo buscar el usuario" + e);
             }
        
    }

    @Override
    public List<Usuario> leerTodos() throws BaseDeDatosException {
        
        List<Usuario> usuarios = new ArrayList<>();
        
        String sql = "SELECT * FROM usuario WHERE eliminado = false";
        
        try (
             Connection conexion = ConexionDB.getConnection();
             PreparedStatement stat = conexion.prepareStatement(sql);

                ResultSet rs = stat.executeQuery();
            ) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
                        
            }
            return usuarios;

        } catch (SQLException e) {
            throw new BaseDeDatosException("ERROR: No se pudieron listar los usuarios" + e);
          }
    }

    @Override
    public boolean actualizar(Usuario usuario) throws BaseDeDatosException {
        
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, mail = ?, celular = ?, contrasena = ?, rol = ?, WHERE id = ?";
        
        try (
              Connection conexion = ConexionDB.getConnection();
              PreparedStatement stat = conexion.prepareStatement(sql)
            ) {
                stat.setString(1, usuario.getNombre());
                stat.setString(2, usuario.getApellido());
                stat.setString(3, usuario.getMail());
                stat.setString(4, usuario.getCelular());
                stat.setString(5, usuario.getContrasena());
                stat.setString(6, usuario.getRol().name());

            return stat.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new BaseDeDatosException("ERROR: No se pudo actualizar el usuario" + e);
        }
        
    }

    @Override
    public boolean eliminar(Usuario usuario) throws BaseDeDatosException {
        
        String sql = "UPDATE usuario SET eliminado = true WHERE id = ?";
        
        try (
                Connection conn = ConexionDB.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql)
        ) {
            stat.setLong(1, usuario.getId());

            return stat.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new BaseDeDatosException("ERROR: No se pudo eliminar el usuario" + e);

        }
        
    }
    
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {

        Usuario usuario = new Usuario(rs.getLong("id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("mail"), rs.getString("celular"), rs.getString("contrasena"), Rol.valueOf(rs.getString("rol").toUpperCase()));
        usuario.setEliminado(rs.getBoolean("eliminado"));
        usuario.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        return usuario;
    }
    
}
