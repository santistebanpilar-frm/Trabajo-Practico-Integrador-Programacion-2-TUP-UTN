/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.entities.Producto;
import foodstore.exception.BaseDeDatosException;
import java.util.List;

/**
 *
 * @author usuario
 */
public class ProductoDao implements IBaseDAO<Producto>{

    @Override
    public Producto crear(Producto obj) throws BaseDeDatosException {
        return null;
    }

    @Override
    public Producto leerPorId(Long id) throws BaseDeDatosException {
        return null;
    }

    @Override
    public List<Producto> leerTodos() throws BaseDeDatosException {
        return null;
    }

    @Override
    public boolean actualizar(Producto nuevoObj) throws BaseDeDatosException {
        return false;
    }

    @Override
    public boolean eliminar(Producto obj) throws BaseDeDatosException {
        return false;
    }
    
}
