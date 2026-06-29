/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.config.ConexionDB;
import foodstore.entities.DetallePedido;
import foodstore.entities.Producto;
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
public class DetallePedidoDao implements IBaseDAO<DetallePedido>{

    @Override
    public DetallePedido crear(DetallePedido detalle) throws BaseDeDatosException {

        String sql = "INSERT INTO detalle_pedido (cantidad, subtotal, eliminado, created_at, id_producto, id_pedido) VALUES (?, ?, ?, ?, ?, ?)";

        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){

            stat.setInt(1, detalle.getCantidad());
            stat.setDouble(2, detalle.getSubtotal());
            stat.setBoolean(3, detalle.isEliminado());
            stat.setTimestamp(4, Timestamp.valueOf(detalle.getCreatedAt()));
            stat.setLong(5, detalle.getProducto().getId());
            stat.setLong(6, detalle.getIdPedido());
        
            stat.executeUpdate();

            try(
                ResultSet rs = stat.getGeneratedKeys()
            ){
                if(rs.next()){
                    detalle.setId(rs.getLong(1));
                }
         }
        
        return detalle;

     } catch(SQLException e){
        throw new BaseDeDatosException("ERROR: No se pudo crear el detalle");
        }
    }


    @Override
    public DetallePedido leerPorId(Long id)
    throws BaseDeDatosException {

        String sql = "SELECT * FROM detalle_pedido WHERE id = ? AND eliminado = false";

        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql);
        ){

        stat.setLong(1, id);

        try(
            ResultSet rs = stat.executeQuery()
        ){
            if(rs.next()){

                return mapearDetalle(rs);
            }
        }

        return null;

        } catch(SQLException e){
            throw new BaseDeDatosException("ERROR: No se pudo buscar el detalle");
            }
    }

    @Override
    public List<DetallePedido> leerTodos() throws BaseDeDatosException {

        List<DetallePedido> detalles = new ArrayList<>();

        String sql = "SELECT * FROM detalle_pedido WHERE eliminado = false";

        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();
        ){
            while(rs.next()){
                detalles.add(mapearDetalle(rs));
            }

            return detalles;

        } catch(SQLException e){
            throw new BaseDeDatosException("ERROR: No se pudieron listar los detalles");
        }

    }


    @Override
    public boolean actualizar(DetallePedido detalle)throws BaseDeDatosException {

        String sql = "UPDATE detalle_pedido SET cantidad = ?, subtotal = ?, id_producto = ?, id_pedido = ? WHERE id = ?";
 
        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql);
        ){
            stat.setInt(1, detalle.getCantidad());
            stat.setDouble(2, detalle.getSubtotal());
            stat.setLong(3, detalle.getProducto().getId());
            stat.setLong(4, detalle.getIdPedido());
            stat.setLong(5, detalle.getId());

            return stat.executeUpdate() > 0;

        } catch(SQLException e){
            throw new BaseDeDatosException("ERROR: No se pudo actualizar el detalle");
        }
    }


    @Override
    public boolean eliminar(DetallePedido detallePedido) throws BaseDeDatosException {

        String sql = "UPDATE detalle_pedido SET eliminado = true WHERE id = ?";

        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql);
        ){

            stat.setLong(1,detallePedido.getId());

            return stat.executeUpdate() > 0;

        } catch(SQLException e){
            throw new BaseDeDatosException("ERROR: No se pudo eliminar el detalle");
        }

    }

    
    private DetallePedido mapearDetalle(ResultSet rs) throws SQLException {

        DetallePedido detalle = new DetallePedido();
    
        detalle.setId(rs.getLong("id"));
        detalle.setCantidad(rs.getInt("cantidad"));
        detalle.setSubtotal(rs.getDouble("subtotal"));

        Producto producto = new Producto();
    
        producto.setId(rs.getLong("id_producto"));

        detalle.setProducto(producto);
        detalle.setIdPedido(rs.getLong("id_pedido"));
        detalle.setEliminado(rs.getBoolean("eliminado"));
        detalle.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        return detalle;

     }
    
}
