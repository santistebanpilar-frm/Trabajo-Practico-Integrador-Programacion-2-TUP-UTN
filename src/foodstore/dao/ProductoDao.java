/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.config.ConexionDB;
import foodstore.entities.Producto;
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
public class ProductoDao implements IBaseDAO<Producto>{

    @Override
    public Producto crear(Producto producto) throws BaseDeDatosException {
        
        String sql = "INSERT INTO producto (nombre, precio, descripcion, stock, imagen, disponible, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try(
             Connection conexion = ConexionDB.getConnection();
             PreparedStatement stat = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ){
                stat.setString(1, producto.getNombre());
                stat.setDouble(2, producto.getPrecio());
                stat.setString(3, producto.getDescripcion());
                stat.setInt(4, producto.getStock());
                stat.setString(5, producto.getImagen());
                stat.setBoolean(6, producto.isEliminado());
                stat.setTimestamp(7, Timestamp.valueOf(producto.getCreatedAt()));
                stat.executeUpdate();
                
                try(
                    ResultSet rs = stat.getGeneratedKeys()
                ){
                    if (rs.next()){
                        producto.setId(rs.getLong(1));
                    }
                 }
                return producto;
             } catch (SQLException e){
                 throw new BaseDeDatosException("ERROR: No se pudo crear el producto" + e);
             }
        
    }

    @Override
    public Producto leerPorId(Long id) throws BaseDeDatosException {
        
        String sql = "SELECT * FROM producto WHERE id = ? AND eliminado = false";
        
        try (
             Connection conexion = ConexionDB.getConnection();
             PreparedStatement stat = conexion.prepareStatement(sql);
            ){
                stat.setLong(1, id);
                
                try(
                    ResultSet rs = stat.executeQuery();
                   ) {
                    if (rs.next()) {
                        return mapearProducto(rs);
                    }
                     }
                return null;
             } catch (SQLException e){
                 throw new BaseDeDatosException("ERROR: no se pudo buscar el producto" + e);
             }
        
    }

    @Override
    public List<Producto> leerTodos() throws BaseDeDatosException {
        
        List<Producto> productos = new ArrayList<>();
        
        String sql = "SELECT * FROM producto WHERE eliminado = false";
        
        try (
             Connection conexion = ConexionDB.getConnection();
             PreparedStatement stat = conexion.prepareStatement(sql);

                ResultSet rs = stat.executeQuery();
            ) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
                        
            }
            return productos;

        } catch (SQLException e) {
            throw new BaseDeDatosException("ERROR: No se pudieron listar los productos" + e);

          }
        
    }

    @Override
    public boolean actualizar(Producto producto) throws BaseDeDatosException {
        
        String sql = "UPDATE producto SET nombre = ?, precio = ?, descripcion = ?, stock = ?, imagen = ?, disponible = ?, WHERE id = ?";
        
        try (
              Connection conexion = ConexionDB.getConnection();
              PreparedStatement stat = conexion.prepareStatement(sql)
            ) {
                stat.setString(1, producto.getNombre());
                stat.setString(2, producto.getDescripcion());
                stat.setLong(3, producto.getId());

            return stat.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new BaseDeDatosException("ERROR: No se pudo actualizar el producto" + e);

        }
        
    }

    @Override
    public boolean eliminar(Producto producto) throws BaseDeDatosException {
        
        String sql = "UPDATE producto SET eliminado = true WHERE id = ?";
        
        try (
                Connection conn = ConexionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, producto.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new BaseDeDatosException("ERROR: No se pudo eliminar el producto" + e);
        } 
    }
    
    private Producto mapearProducto(ResultSet rs) throws SQLException {

        Producto producto = new Producto(rs.getLong("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getString("descripcion"), rs.getInt("stock"), rs.getString("imagen"), rs.getBoolean("disponible"));
        producto.setEliminado(rs.getBoolean("eliminado"));
        producto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        return producto;
    }
    
}
