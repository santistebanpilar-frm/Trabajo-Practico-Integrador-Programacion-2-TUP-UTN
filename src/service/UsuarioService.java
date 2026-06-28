/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.IBaseDAO;
import entities.Usuario;

/**
 *
 * @author pilarsg
 */
public class UsuarioService extends GenericService<Usuario>  {
    
    public UsuarioService(IBaseDAO<Usuario> dao) {
        super(dao);
    }
    
}
