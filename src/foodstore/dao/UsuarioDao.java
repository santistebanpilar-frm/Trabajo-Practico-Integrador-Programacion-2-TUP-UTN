/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.entities.Usuario;
import foodstore.exception.BaseDeDatosException;
import java.util.List;

/**
 *
 * @author usuario
 */
public class UsuarioDao implements IBaseDAO<Usuario>{

    @Override
    public Usuario crear(Usuario obj) throws BaseDeDatosException {
        return null;
    }

    @Override
    public Usuario leerPorId(Long id) throws BaseDeDatosException {
        return null;
    }

    @Override
    public List<Usuario> leerTodos() throws BaseDeDatosException {
        return null;
    }

    @Override
    public boolean actualizar(Usuario nuevoObj) throws BaseDeDatosException {
        return false;
    }

    @Override
    public boolean eliminar(Usuario obj) throws BaseDeDatosException {
        return false;
    }
    
}
