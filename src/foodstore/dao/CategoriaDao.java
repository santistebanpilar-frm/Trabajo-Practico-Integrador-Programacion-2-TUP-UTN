/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.config.ConexionDB;
import foodstore.entities.Categoria;
import foodstore.exception.BaseDeDatosException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author usuario
 */
public class CategoriaDao implements IBaseDAO<Categoria>{

    //Create
    @Override
    public Categoria crear(Categoria categoria) throws BaseDeDatosException {
        
        String sql = "INSERT INTO categoria (nombre, descripcion, eliminado, created_at) VALUES (?, ?, ?, ?)";
        
        try(
             Connection conexion = ConexionDB.getConnection();
             PreparedStatement stat = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ){
                stat.setString(1, categoria.getNombre());
                stat.setString(2, categoria.getDescripcion());
                stat.setBoolean(3, categoria.isEliminado());
                stat.setTimestamp(4, Timestamp.valueOf(categoria.getCreatedAt()));
                stat.executeUpdate();
                
                try(
                    ResultSet rs = stat.getGeneratedKeys()
                ){
                    if (rs.next()){
                        categoria.setId(rs.getLong(1));
                    }
                 }
                return categoria;
             } catch (SQLException e){
                 throw new BaseDeDatosException("ERROR: No se pudo crear la categoria" + e);
             }  
    }

    //Read
    @Override
    public Categoria leerPorId(Long id) throws BaseDeDatosException {
        
        String sql = "SELECT * FROM categoria WHERE id = ? AND eliminado = false";
        
        try (
             Connection conexion = ConexionDB.getConnection();
             PreparedStatement stat = conexion.prepareStatement(sql);
            ){
                stat.setLong(1, id);
                
                try(
                    ResultSet rs = stat.executeQuery();
                   ) {
                    if (rs.next()) {
                        return mapearCategoria(rs);
                    }
                     }
                return null;
             } catch (SQLException e){
                 throw new BaseDeDatosException("ERROR: no se pudo buscar la categoria" + e);
             }
    }

    @Override
    public List<Categoria> leerTodos() throws BaseDeDatosException {
        
        List<Categoria> categorias = new ArrayList<>();
        
        String sql = "SELECT * FROM categoria WHERE eliminado = false";
        
        try (
             Connection conexion = ConexionDB.getConnection();
             PreparedStatement stat = conexion.prepareStatement(sql);

                ResultSet rs = stat.executeQuery();
            ) {

            while (rs.next()) {
                categorias.add(mapearCategoria(rs));
                        
            }
            return categorias;

        } catch (SQLException e) {
            throw new BaseDeDatosException("ERROR: No se pudieron listar las categorias" + e);

          }
    }

    //Update
    @Override
    public boolean actualizar(Categoria categoria) throws BaseDeDatosException {
        
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?";
                     
        try (
              Connection conexion = ConexionDB.getConnection();
              PreparedStatement stat = conexion.prepareStatement(sql)
            ) {
                stat.setString(1, categoria.getNombre());
                stat.setString(2, categoria.getDescripcion());
                stat.setLong(3, categoria.getId());

            return stat.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new BaseDeDatosException("ERROR: No se pudo actualizar la categoria" + e);

        }
    }

    //Delete
    @Override
    public boolean eliminar(Categoria categoria) throws BaseDeDatosException {
        
        String sql = "UPDATE categoria SET eliminado = true WHERE id = ?";
        
        try (
                Connection conn = ConexionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, categoria.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new BaseDeDatosException("ERROR: No se pudo eliminar la categoria" + e);

        }
    }

    //Mapear
    private Categoria mapearCategoria(ResultSet rs) throws SQLException {

        Categoria categoria = new Categoria(rs.getLong("id"), rs.getString("nombre"), rs.getString("descripcion"));
        categoria.setEliminado(rs.getBoolean("eliminado"));
        categoria.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        return categoria;
    }
        
}
    
