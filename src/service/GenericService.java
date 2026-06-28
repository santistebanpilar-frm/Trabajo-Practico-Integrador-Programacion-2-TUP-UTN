/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.IBaseDAO;
import exception.EntityNotFoundException;
import java.util.List;

/**
 *
 * @author pilarsg
 * @param <T>
 */
public abstract class GenericService<T> {
 
    protected final IBaseDAO<T> dao;
 
    public GenericService(IBaseDAO<T> dao) {
        this.dao = dao;
    }
    public T buscarPorId(Long id) {
        T entidad = dao.buscarPorId(id);
        if (entidad == null) {
            throw new EntityNotFoundException("No se encontró la entidad con id " + id);
        }
        return entidad;
    }
 
    public List<T> listar() {
        return dao.listar();
    }
 
    public boolean eliminar(Long id) {
        buscarPorId(id);
        return dao.eliminar(id);
    }
}
