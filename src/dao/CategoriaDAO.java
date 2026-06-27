/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.Categoria;
import exception.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pilarsg
 */
public class CategoriaDAO implements IBaseDAO<Categoria> {

    @Override
    public Categoria crear(Categoria categoria) {
        String sql= "INSERT INTO categoria(nombre,descripcion,eliminado,created_at)"+"VALUES(?,?,?,?)";
        
        try (Connection con = ConexionDB.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, Boolean.TRUE.equals(categoria.isEliminado()));
            ps.setObject(4, categoria.getCreatedAt());

            ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        categoria.setId(keys.getLong(1));
                    }
                }
                return categoria;

        } catch (SQLException e) {
            throw new PersistenciaException("Error al crear la categoría", e);
        }
    }

    @Override
    public Categoria buscarPorId(Long id) {
        String sql = "SELECT id, nombre, descripcion, eliminado, created_at " + "FROM categoria where id = ?";
        
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
             ps.setLong(1, id);
             
             try (ResultSet rs = ps.executeQuery()){
                 if (rs.next()) {
                     return mapearCategoria(rs);
                 }
                    return null;
             }
        } catch (SQLException e){
            throw new PersistenciaException("Error al buscar la categoria por su ID;",e);
        }
    }

    @Override
    public List<Categoria> listar() {
        String sql = "SELECT id, nombre, descripcion, eliminado, created_at "+ "FROM categoria WHERE eliminado = false ORDER BY nombre";
        
        List<Categoria> resultado = new ArrayList<>();
        
        try (Connection con = ConexionDB.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()){
                resultado.add(mapearCategoria(rs));
            }
            return resultado;
        } catch (SQLException e){
            throw new PersistenciaException("Error al listar categorias",e);
        }
    }
    
    @Override
    public boolean actualizar(Categoria categoria){
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?";
        
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setLong(3, categoria.getId());
            
            int filasPorActualizar = ps.executeUpdate();
            return filasPorActualizar > 0;
        } catch (SQLException e){
            throw new PersistenciaException("Error al actualizar la categoria "+categoria.getId(), e);
        }
    }  


    @Override
    public boolean eliminar(Long id) {
     String sql = "UPDATE categoria SET eliminado = true WHERE id = ?";
     
     try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)){
         ps.setLong(1, id);
         int filasPorActualizar = ps.executeUpdate();
            return filasPorActualizar > 0;
        } catch (SQLException e){
            throw new PersistenciaException("Error al eliminar la categoria "+id, e);
        }
    }
    
    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        Categoria cat = new Categoria();
        cat.setId(rs.getObject("id", Long.class));
        cat.setNombre(rs.getString("nombre"));
        cat.setDescripcion(rs.getString("descripcion"));
        cat.setEliminado(rs.getObject("eliminado", Boolean.class));
        cat.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
        return cat;
    }
}
