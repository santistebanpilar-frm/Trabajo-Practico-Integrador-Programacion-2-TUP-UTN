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
    public void crear(Usuario obj) throws BaseDeDatosException {
        
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
    public void actualizar(Usuario nuevoObj) throws BaseDeDatosException {
        
    }

    @Override
    public void eliminar(Usuario obj) throws BaseDeDatosException {
        
    }
    
}
