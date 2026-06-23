/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.dao;

import foodstore.exception.ValidacionException;
import java.util.List;
import java.util.Optional;
import java.sql.*;
/**
 *
 * @author usuario
 */
public interface IBaseDAO <T>{
    // Esta se usa para Crear
    public T crear(T entity) throws SQLException;
    //Estas para Leer
    //Esta para buscar por el ID
    public Optional<T> leer(Long id) throws SQLException;
    //Esta para buscar todas y listarlas
    public List<T> listar()throws SQLException;
    //Esta para actualizar
    public boolean actualizar(T entity)throws SQLException;
    //Esta para eliminar
    public boolean eliminar(Long id)throws SQLException;
}
