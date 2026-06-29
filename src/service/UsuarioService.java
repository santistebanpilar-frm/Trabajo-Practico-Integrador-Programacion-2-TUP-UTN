/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UsuarioDAO;
import entities.Usuario;
import enums.Rol;
import exception.EntityNotFoundException;

/**
 *
 * @author pilarsg
 */
public class UsuarioService extends GenericService<Usuario>  {
    
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioService(UsuarioDAO usuarioDAO) {
        super(usuarioDAO);
        this.usuarioDAO = usuarioDAO;
    }
    
    @Override
    public Usuario buscarPorId(Long id){
        Usuario usuario = usuarioDAO.buscarPorId(id);
        if (usuario == null || Boolean.TRUE.equals(usuario.isEliminado())){
            throw new EntityNotFoundException("No existe el USUARIO con ID:"+id);
        }
        return usuario;
    }
    
    public Usuario crear(String nombre, String apellido, String mail, String contraseña, String celular, Rol rol){
        validarString(nombre,"nombre");
        validarString(apellido,"apellido");
        validarString(mail,"mail");
        validarString(contraseña,"contraseña");
        
        if (usuarioDAO.buscarPorMail(mail)!= null) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario con el mail: " + mail);
        }
 
        Usuario usuario = new Usuario(nombre, apellido, mail,contraseña, celular, rol != null ? rol : Rol.USUARIO);
        return usuarioDAO.crear(usuario);
    }
    
    public boolean actualizar(Long id, String nuevoNombre, String nuevoApellido, String nuevoMail, String nuevaContraseña, String nuevoCelular, Rol nuevoRol){
        Usuario usuario = buscarPorId(id);
 
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            usuario.setNombre(nuevoNombre);
        }
        
        if (nuevoApellido != null && !nuevoApellido.trim().isEmpty()) {
            usuario.setApellido(nuevoApellido);
        }
       
        if (nuevoMail != null && !nuevoMail.trim().isEmpty()) {
            Usuario usuarioExistente = usuarioDAO.buscarPorMail(nuevoMail.trim());
            if (usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())) {
                throw new IllegalArgumentException("El mail " + nuevoMail + " ya está en uso!");
            }
            usuario.setMail(nuevoMail.trim());
        }
        
        if (nuevoCelular != null) {
            usuario.setCelular(nuevoCelular.trim());
        }
        
        if (nuevaContraseña != null && !nuevaContraseña.trim().isEmpty()) {
            usuario.setContraseña(nuevaContraseña.trim());
        }
        
        if (nuevoRol != null) {
            usuario.setRol(nuevoRol);
        }
 
        boolean usuarioActulizado = usuarioDAO.actualizar(usuario);
        if (!usuarioActulizado) {
            throw new EntityNotFoundException(
                    "No se pudo ACTUALIZAR el USUARIO con ID " + id);
        }
        return usuarioActulizado;
        
    }
    
    private void validarString( String escrito, String campo){
        if (escrito == null || escrito.trim().isEmpty()){
            throw new IllegalArgumentException("El campo "+campo+" no puede quedar vacio!");
        }
    }
    
}
