/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.config.ConexionDB;
import foodstore.entities.Pedido;
import foodstore.enums.Estado;
import foodstore.enums.FormaPago;
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
public class PedidoDao implements IBaseDAO<Pedido>{

    @Override
    public Pedido crear(Pedido pedido) throws BaseDeDatosException {

        String sql = "INSERT INTO pedido (fecha, estado, total, forma_pago, eliminado, created_at, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){

            stat.setTimestamp(1, Timestamp.valueOf(pedido.getFecha().atStartOfDay()));
            stat.setString(2, pedido.getEstado().name());
            stat.setDouble(3, pedido.getTotal());
            stat.setString(4, pedido.getFormaPago().name());
            stat.setBoolean(5, pedido.isEliminado());
            stat.setTimestamp(6, Timestamp.valueOf(pedido.getCreatedAt()));
            stat.setLong(7, pedido.getIdUsuario());

            stat.executeUpdate();

            try(ResultSet rs = stat.getGeneratedKeys()){
                if(rs.next()){
                    pedido.setId(rs.getLong(1));
                }
            }
            return pedido;
        } catch(SQLException e){
            throw new BaseDeDatosException("ERROR: No se pudo crear el pedido");
        }
    }


    @Override
    public Pedido leerPorId(Long id) throws BaseDeDatosException {

        String sql = "SELECT * FROM pedido WHERE id = ? AND eliminado = false";

        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql);
        ){
            stat.setLong(1, id);

            try(ResultSet rs = stat.executeQuery()){
                if(rs.next()){
                    return mapearPedido(rs);
                }
            }
            return null;
        } catch(SQLException e){
            throw new BaseDeDatosException("ERROR: No se pudo buscar el pedido");
        }

    }


    @Override
    public List<Pedido> leerTodos() throws BaseDeDatosException {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedido WHERE eliminado = false";

        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();
        ){

            while(rs.next()){
                pedidos.add(mapearPedido(rs));
            }
            return pedidos;
        } catch(SQLException e){
            throw new BaseDeDatosException("ERROR: No se pudieron listar los pedidos");
        }

    }


    @Override
    public boolean actualizar(Pedido pedido)
    throws BaseDeDatosException {

        String sql =
        "UPDATE pedido SET fecha = ?, estado = ?, total = ?, forma_pago = ?, id_usuario = ? WHERE id = ?";

        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql);
        ){
            stat.setTimestamp(1, Timestamp.valueOf(pedido.getFecha().atStartOfDay()));
            stat.setString(2, pedido.getEstado().name());
            stat.setDouble(3, pedido.getTotal());
            stat.setString(4, pedido.getFormaPago().name());
            stat.setLong(5, pedido.getIdUsuario());
            stat.setLong(6, pedido.getId());

            return stat.executeUpdate() > 0;
            
        } catch(SQLException e){
            throw new BaseDeDatosException("ERROR: No se pudo actualizar el pedido");
        }

    }

    
    @Override
    public boolean eliminar(Pedido pedido) throws BaseDeDatosException {

        String sql = "UPDATE pedido SET eliminado = true WHERE id = ?";

        try(
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement stat = conexion.prepareStatement(sql);
        ){
            stat.setLong(1, pedido.getId());
        
            return stat.executeUpdate() > 0;

        } catch(SQLException e){
            throw new BaseDeDatosException("ERROR: No se pudo eliminar el pedido");
        }

    }

    
    private Pedido mapearPedido(ResultSet rs) throws SQLException {

        Pedido pedido = new Pedido();

        pedido.setId(rs.getLong("id"));
        pedido.setFecha(rs.getTimestamp("fecha").toLocalDateTime().toLocalDate());
        pedido.setEstado(Estado.valueOf(rs.getString("estado")));
        pedido.setTotal(rs.getDouble("total"));
        pedido.setFormaPago(FormaPago.valueOf(rs.getString("forma_pago")));
        pedido.setIdUsuario(rs.getLong("id_usuario"));
        pedido.setEliminado(rs.getBoolean("eliminado"));
        pedido.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        return pedido;
        }
    
    }
