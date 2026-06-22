/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.entities.Categoria;
import foodstore.exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class CategoriaDao implements IBaseDAO<Categoria>{
    private Connection conn;

    public CategoriaDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Categoria crear(Categoria categoria) throws DAOException {
        String sql = "INSERT INTO categorias (nombre, descripcion) VALUES (?, ?);";
        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    categoria.setId(rs.getLong(1));
                }
            }
            return categoria;
        }catch (SQLException e){
            throw new DAOException("Error al crear categoria"+ e);
        }
    }

    @Override
    public Optional<Categoria> leer(Long id) throws DAOException {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapearFila(rs));
                }
            }
            
        }catch (SQLException e){
            throw new DAOException("Error al leer la categoria"+ e);
        }
        return Optional.empty();
    }
    

    @Override
    public List<Categoria> listar() throws DAOException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE eliminado = false ORDER BY nombre ASC";
        try(Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)){
            while (rs.next()){
                lista.add(mapearFila(rs));
            }
        }catch (SQLException e){
            throw new DAOException("Error al listar categorias");
        }
        return lista;
    }

    @Override
    public boolean actualizar(Categoria categoria) throws DAOException {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setLong(3, categoria.getId());
            int filas = ps.executeUpdate();
            return filas == 1;
        }catch (SQLException e){
            throw new DAOException("Error al actualizar categoria"+e);
        }
    }

    @Override
    public boolean eliminar(Long id) throws DAOException {
        String sql = "UPDATE categorias SET eliminado = true WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;
            
        }catch (SQLException e){
            throw new DAOException("Error al eliminar categoria");
        }
    }
    private Categoria mapearFila(ResultSet rs) throws SQLException{
        return new Categoria(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("descripcion")
        );
    }
}
