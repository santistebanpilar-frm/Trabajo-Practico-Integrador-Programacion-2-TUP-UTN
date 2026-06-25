/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.exception.BaseDeDatosException;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IBaseDAO<T> {
    //Create
    public void crear(T obj) throws BaseDeDatosException;
    
    //Read
    public T leerPorId(Long id) throws BaseDeDatosException;
    public List<T> leerTodos() throws BaseDeDatosException;
    
    //Update
    public void actualizar(T nuevoObj) throws BaseDeDatosException;
    
    //Delete
    public void eliminar(T obj) throws BaseDeDatosException;
    
}
