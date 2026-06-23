/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.IBaseDAO;
import foodstore.entities.Usuario;
import foodstore.exception.ValidacionException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class UsuarioService extends GenericService<Usuario>{
    public UsuarioService(IBaseDAO<Usuario> dao) {
        super(dao);
    }
    @Override
    public Usuario crear(Usuario usuario) throws SQLException {
        validarDatos(usuario);
        return super.crear(usuario); 
    }
    @Override
    public Optional<Usuario> leer(Long id) throws SQLException {
        Optional<Usuario> usuario = super.leer(id);
        if(usuario.isEmpty()){
            throw new SQLException("No se encontro el Usuario con ID: " + id);
        }
        return usuario;
    }
    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = super.listar();
        if(lista.isEmpty()){
            throw new SQLException("No hay Usuarios cargados");
            
        }
        return lista;
    }
    @Override
    public boolean actualizar(Usuario usuario) throws SQLException {
        leer(usuario.getId());
        validarDatos(usuario);
        return super.actualizar(usuario); 
    }

    @Override
    public boolean eliminar(Long id) throws SQLException {
        leer(id);
        return super.eliminar(id); 
    }
    
    private void validarDatos(Usuario usuario)throws SQLException{
        if(usuario.getNombre() == null || usuario.getNombre().isBlank()){
            throw new SQLException("El nombre no puede estar vacio");
        }
        if(usuario.getApellido()== null || usuario.getApellido().isBlank()){
            throw new SQLException("El Apellido no puede estar vacio");
        }
        if(usuario.getMail()== null || usuario.getMail().isBlank()){
            throw new SQLException("El Mail no puede estar vacio");
        }
        if(!usuario.getMail().contains("@")){
            throw new SQLException("El formato no es el de un correo");
        }
        if(usuario.getCelular() == null || usuario.getCelular().isBlank()){
            throw new SQLException("El celular no puede vacio");
        }
        if(usuario.getContrasenia() == null || usuario.getContrasenia().isBlank()){
            throw new SQLException("La contrasenia no puede estar vacia");
        }
        if(usuario.getContrasenia().length() < 6 ){
            throw new SQLException("La contrasenia debe tener al menos 6 caracteres");
        }
        if(usuario.getRol() == null){
            throw new SQLException("El rol es obligatorio");
        }
    }
}
