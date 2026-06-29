/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.IBaseDAO;
import foodstore.exception.BaseDeDatosException;
import foodstore.exception.GeneralException;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class GenericService<T> {
    protected IBaseDAO<T> dao;
    
    public GenericService(IBaseDAO<T> dao) {
        this.dao = dao;
    }
    
    //Create
    public T crear(T obj) throws GeneralException {
        
        try {
            return dao.crear(obj);
            
        } catch (BaseDeDatosException e) {
            throw new GeneralException("Error al crear ");
        }
    }   
    
    //Read
    public T leerPorId(Long id) throws GeneralException {

        try {
            return dao.leerPorId(id);

        } catch (BaseDeDatosException e) {
            throw new GeneralException("Error al buscar");
        }
    }
    
    public List<T> listarTodos() throws GeneralException {

        try {
            return dao.leerTodos();

        } catch (BaseDeDatosException e) {
            throw new GeneralException("Error al listar");
        }
    }
    
    //Update
    public boolean actualizar(T obj) throws GeneralException {

        try {
            return dao.actualizar(obj);

        } catch (BaseDeDatosException e) {
            throw new GeneralException("Error al actualizar");
        }  
    }    

    //Remove
    public boolean eliminar(T obj) throws GeneralException {

        try {
            return dao.eliminar(obj);

        } catch (BaseDeDatosException e) {
            throw new GeneralException("Error al eliminar");
        }
    }

}
