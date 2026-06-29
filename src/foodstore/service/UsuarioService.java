/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.IBaseDAO;
import foodstore.dao.UsuarioDao;
import foodstore.entities.Usuario;
import foodstore.exception.GeneralException;

/**
 *
 * @author usuario
 */
public class UsuarioService extends GenericService<Usuario>{
    
    public UsuarioService(IBaseDAO<Usuario> dao) {
        super(new UsuarioDao());
    }
    
     @Override
    public Usuario crear(Usuario usuario) throws GeneralException {

        if(usuario.getNombre() == null || usuario.getNombre().isBlank()){

            throw new GeneralException("El nombre no puede estar nulo/vacio");
        }

        if(usuario.getMail() == null || usuario.getMail().isBlank()){

            throw new GeneralException("El mail no puede estar nulo/vacio");
        }

        if(usuario.getContrasena() == null || usuario.getContrasena().isBlank()){

            throw new GeneralException("La contrasena no puede estar nula/vacia");
        }

        return super.crear(usuario);
    }


    @Override
    public Usuario leerPorId(Long id) throws GeneralException {

        Usuario usuario = super.leerPorId(id);

        if(usuario == null){

            throw new GeneralException("No se encontro al usuario");
        }
        
        return usuario;
    }


    @Override
    public boolean actualizar(Usuario usuario)
    throws GeneralException {

        leerPorId(usuario.getId());

        return super.actualizar(usuario);
    }


    @Override
    public boolean eliminar(Usuario usuario)
    throws GeneralException {

        leerPorId(usuario.getId());

        return super.eliminar(usuario);
    }
    
    
}
