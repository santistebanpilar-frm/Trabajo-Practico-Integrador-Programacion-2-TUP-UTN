/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.Producto;
import config.ConexionDB;
import entities.Categoria;
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
public class ProductoDAO implements IBaseDAO<Producto>  {

    @Override
    public Producto crear(Producto producto) {
        String sql = "INSERT INTO producto "+"(nombre, descripcion, precio, stock, imagen, disponible, eliminado, created_at, id_categoria)" +"VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = ConexionDB.getInstance().getConnection(); PreparedStatement ps= con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setObject(3, producto.getPrecio());
            ps.setObject(4, producto.getStock());
            ps.setString(5, producto.getImagen());
            ps.setObject(6, producto.isDisponible());
            ps.setObject(7, producto.isEliminado());
            ps.setObject(8, producto.getCreatedAt());
            ps.setLong(9, producto.getCategoria().getId());
            
            ps.executeUpdate();
            
            try (ResultSet claves = ps.getGeneratedKeys()){
                if (claves.next()){
                    producto.setId(claves.getLong(1));
                }
            } 
            return producto;
        } catch (SQLException e){
            throw new PersistenciaException("Error al CREAR el PRODUCTO",e);
        }
    }

    @Override
    public Producto buscarPorId(Long id) {
        String sql = "SELECT prod.id, prod.nombre, prod.descripcion, prod.precio, prod.stock, prod.imagen, prod.disponible, prod.eliminado, prod.created_at, prod.id_categoria "+"c.nombre AS categ_nombre, c.descripcion AS categ_descripcion"+"c.eliminado AS categ.eliminado, c.created_at AS categ.created_at"+
        "FROM producto prod"+"JOIN categoria c ON prod.id_categoria = c.id"+"WHERE prod.id = ?";
        
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)){
        ps.setLong(1, id);
        
        try (ResultSet rs = ps.executeQuery()){
            if(rs.next()){
                return mapearProducto(rs);
            }
            return null;
        }
        
        }catch (SQLException e){
            throw new PersistenciaException("Error al BUSCAR el PRODUCTO con id="+id, e);
        }
    }

    @Override
    public List<Producto> listar() {
        String sql = "SELECT prod.id, prod.nombre, prod.descripcion, prod.precio, prod.stock, prod.imagen, prod.disponible, prod.eliminado, prod.created_at, prod.id_categoria "+"c.nombre AS categ_nombre, c.descripcion AS categ_descripcion"+"c.eliminado AS categ.eliminado, c.created_at AS categ.created_at"+
        "FROM producto prod"+"JOIN categoria c ON prod.id_categoria = c.id"+"WHERE prod.eliminado = false"+"ORDER BY prod.nombre";
        
        List<Producto> resultado = new ArrayList<>();
        
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql);ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                resultado.add(mapearProducto(rs));
            }
            return resultado;
        
        }catch (SQLException e){
            throw new PersistenciaException("Error al LISTAR PRODUCTOS", e);
        }
    }
    
    public List<Producto> listarPorCategoria(Long categoriaId){
        String sql = "SELECT prod.id, prod.nombre, prod.descripcion, prod.precio, prod.stock, prod.imagen, prod.disponible, prod.eliminado, prod.created_at, prod.id_categoria "+"c.nombre AS categ_nombre, c.descripcion AS categ_descripcion"+"c.eliminado AS categ.eliminado, c.created_at AS categ.created_at"+
        "FROM producto prod"+"JOIN categoria c ON prod.id_categoria = c.id"+"WHERE prod.eliminado = false AND prod.id_categoria = ?"+"ORDER BY prod.nombre";
        
        List<Producto> resultado = new ArrayList<>();
        
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)){
            ps.setLong(1, categoriaId);
            
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    resultado.add(mapearProducto(rs));
                }
            }
                return resultado;
        } catch (SQLException e){
            throw new PersistenciaException("Error al LISTAR PRODUCTO por CATEGORIA",e);
        } 
    }

    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, stock = ? , imagen = ? , disponible = ? , id_categoria = ? "
                + "WHERE id = ?";
        
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setObject(3, producto.getPrecio());
            ps.setObject(4, producto.getStock());
            ps.setString(5, producto.getImagen());
            ps.setObject(6, producto.isDisponible());
            ps.setLong(7, producto.getCategoria().getId());
            ps.setLong(8, producto.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
           throw new PersistenciaException("Error al ACTUALIZAR el producto ID="+producto.getId(),e);
      }
                
    }

    @Override
    public boolean eliminar(Long id) {
        String sql = "UPDATE prducto SET eliminado = true WHERE id = ?";
        
        try (Connection con = ConexionDB.getInstance().getConnection();PreparedStatement ps = con.prepareStatement(sql)){
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e ){
            throw new PersistenciaException("Error al ELIMINAR PRODUCTO con ID="+id,e);
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        
        Categoria cat = new Categoria();
        cat.setId(rs.getObject("categoria_id", Long.class));
        cat.setNombre(rs.getString("cat_nombre"));
        cat.setDescripcion(rs.getString("cat_descripcion"));
        cat.setEliminado(rs.getObject("cat_eliminado", Boolean.class));
        cat.setCreatedAt(rs.getObject("cat_created_at", java.time.LocalDateTime.class));
 

        Producto p = new Producto();
        p.setId(rs.getObject("id", Long.class));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio(rs.getObject("precio", Double.class));
        p.setStock(rs.getObject("stock", Integer.class));
        p.setImagen(rs.getString("imagen"));
        p.setDisponible(rs.getObject("disponible", Boolean.class));
        p.setEliminado(rs.getObject("eliminado", Boolean.class));
        p.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
        p.setCategoria(cat);
 
        return p;
    }
    
}
