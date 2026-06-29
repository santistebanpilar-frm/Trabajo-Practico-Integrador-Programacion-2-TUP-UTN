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
    public Pedido crear(Pedido obj) throws BaseDeDatosException {
        return null;
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
    public boolean actualizar(Pedido nuevoObj) throws BaseDeDatosException {
        return false;
    }

    @Override
    public boolean eliminar(Pedido obj) throws BaseDeDatosException {
        return false;
    }
    
}
