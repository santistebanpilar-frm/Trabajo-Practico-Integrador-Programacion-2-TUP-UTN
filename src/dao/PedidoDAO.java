/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.DetallePedido;
import entities.Pedido;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pilarsg 
 * 
 */
public class PedidoDAO implements IBaseDAO<Pedido> {

    @Override
    public Pedido crear(Pedido entidad) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Pedido buscarPorId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Pedido> listar() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public List<Pedido> listarPorUsuario() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean actualizar(Pedido entidad) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean eliminar(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    private Pedido mapearPedido(ResultSet rs) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
