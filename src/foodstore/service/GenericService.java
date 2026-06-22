/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.service;

import foodstore.dao.IBaseDAO;
import foodstore.exception.DAOException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author usuario
 */
public class GenericService<T> {
    private IBaseDAO<T> dao;

    public GenericService(IBaseDAO<T> dao) {
        this.dao = dao;
    }
    public T crear(T entidad) throws DAOException{
        return dao.crear(entidad);
    }
    public Optional<T> leer(Long id)throws DAOException{
        return dao.leer(id);
    }
    public List<T> listar()throws DAOException{
        return dao.listar();
    }
    public boolean actualizar(T entidad)throws DAOException{
        return dao.actualizar(entidad);
    }
    public boolean eliminar(Long id)throws DAOException{
        return dao.eliminar(id);
    }
}
