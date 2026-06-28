/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.entities.Pedido;
import foodstore.exception.BaseDeDatosException;
import java.util.List;

/**
 *
 * @author usuario
 */
public class PedidoDao implements IBaseDAO<Pedido>{

    @Override
    public void crear(Pedido obj) throws BaseDeDatosException {
        
    }

    @Override
    public Pedido leerPorId(Long id) throws BaseDeDatosException {
        return null;
    }

    @Override
    public List<Pedido> leerTodos() throws BaseDeDatosException {
        return null;
    }

    @Override
    public void actualizar(Pedido nuevoObj) throws BaseDeDatosException {
        
    }

    @Override
    public void eliminar(Pedido obj) throws BaseDeDatosException {
        
    }
    
}
